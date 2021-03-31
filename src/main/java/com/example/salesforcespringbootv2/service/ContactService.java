package com.example.salesforcespringbootv2.service;

import com.example.salesforcespringbootv2.auth.AuthorizationHeaders;
import com.example.salesforcespringbootv2.email.EmailService;
import com.example.salesforcespringbootv2.model.account.Account;
import com.example.salesforcespringbootv2.model.contact.Contact;
import com.example.salesforcespringbootv2.model.contact.ContactResponse;
import com.example.salesforcespringbootv2.query.QueryProvider;
import com.example.salesforcespringbootv2.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ContactService {

    private final QueryProvider queryProvider;
    private final AuthorizationHeaders authorizationHeaders;
    private final ContactRepository contactRepository;
    private final AccountService accountService;
    private final EmailService emailService;

    @Autowired
    public ContactService(QueryProvider queryProvider,
                          AuthorizationHeaders authorizationHeaders,
                          ContactRepository contactRepository,
                          AccountService accountService,
                          EmailService emailService) {
        this.queryProvider = queryProvider;
        this.authorizationHeaders = authorizationHeaders;
        this.contactRepository = contactRepository;
        this.accountService = accountService;
        this.emailService = emailService;
    }

    public ContactResponse selectAllContacts(String accessToken, String instanceUrl, Optional<Integer> limit) {
        HttpHeaders httpHeaders = authorizationHeaders.getAuthorizationHeaders(accessToken);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        String queryAllContacts = queryProvider.getQueryAllContacts();
        if (limit.isPresent()) {
            queryAllContacts = queryAllContacts + " LIMIT " + limit.get();
        }

        ResponseEntity<ContactResponse> contactResponse =
                getContactResponseEntity(instanceUrl, queryAllContacts, httpHeaders, params);

        return contactResponse.getBody();
    }

    public ContactResponse selectContactById(String accessToken, String instanceUrl, String contactId) {
        HttpHeaders httpHeaders = authorizationHeaders.getAuthorizationHeaders(accessToken);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        String queryContactById = queryProvider.getQueryContactById() + "'" + contactId + "'";
        ResponseEntity<ContactResponse> contactResponse =
                getContactResponseEntity(instanceUrl, queryContactById, httpHeaders, params);


        return contactResponse.getBody();
    }

    @Transactional
    public void saveContactById(String accessToken, String instanceUrl, String contactId) {
        Contact contact = selectContactById(accessToken, instanceUrl, contactId).getRecords().get(0);
        Account account = accountService.selectAccountById(accessToken, instanceUrl, contact.getAccountId()).getRecords().get(0);
        contact.setAccountName(account.getName());
        contactRepository.save(contact);
    }

    public void deleteContactById(String contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Account %s not exists in database", contactId)));
        contactRepository.delete(contact);
    }

    public String sendHelloEmailToContact(String accessToken, String instanceUrl, String contactId) {
        Contact contact = selectContactById(accessToken, instanceUrl, contactId).getRecords().get(0);

        if (contact.getEmail() == null) {
            throw new IllegalArgumentException(String.format(
                    "Contact with id %s and name %s does not provided email",
                    contact.getId(),
                    contact.getName())
            );
        }

        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        String helloMsg = String.format("Hello %s, how are you in this beautiful %s?", contact.getName(), dayOfWeek);
        emailService.send(contact.getEmail(), helloMsg);
        return String.format("Send email to %s", contact.getName());
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    private ResponseEntity<ContactResponse> getContactResponseEntity(String instanceUrl,
                                                                     String query,
                                                                     HttpHeaders httpHeaders,
                                                                     MultiValueMap<String, String> params
    ) {
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ContactResponse> contactSalesforceResponse = restTemplate.exchange(
                instanceUrl + query,
                HttpMethod.GET,
                request,
                ContactResponse.class
        );
        return contactSalesforceResponse;
    }
}
