package com.example.salesforcespringbootv2.controller;

import com.example.salesforcespringbootv2.auth.AuthenticationResponse;
import com.example.salesforcespringbootv2.auth.AuthenticationService;
import com.example.salesforcespringbootv2.model.account.AccountResponse;
import com.example.salesforcespringbootv2.model.contact.ContactResponse;
import com.example.salesforcespringbootv2.service.AccountService;
import com.example.salesforcespringbootv2.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/salesforce")
public class AppController {

    private final AuthenticationService authenticationService;
    private final ContactService contactService;
    private final AccountService accountService;

    @Autowired
    public AppController(ContactService contactService,
                         AuthenticationService authenticationService,
                         AccountService accountService) {
        this.contactService = contactService;
        this.authenticationService = authenticationService;
        this.accountService = accountService;
    }

    private AuthenticationResponse getAuthenticationResponse() {
        return authenticationService.login();
    }

    @GetMapping("contacts")
    public ContactResponse getAllContacts(@QueryParam("limit") Integer limit) {
        AuthenticationResponse authenticationResponse = getAuthenticationResponse();
        return contactService.selectAllContacts(
                authenticationResponse.getAccess_token(),
                authenticationResponse.getInstance_url(),
                Optional.ofNullable(limit)
        );
    }

    @GetMapping("contacts/{contactId}")
    public ContactResponse getContactById(@PathVariable("contactId") String contactId) {
        AuthenticationResponse authenticationResponse = getAuthenticationResponse();
        return contactService.selectContactById(
                authenticationResponse.getAccess_token(),
                authenticationResponse.getInstance_url(),
                contactId
        );
    }

    @PostMapping("contacts/{contactId}")
    public void saveContactById(@PathVariable("contactId") String contactId) {
        AuthenticationResponse authenticationResponse = getAuthenticationResponse();
        contactService.saveContactById(
                authenticationResponse.getAccess_token(),
                authenticationResponse.getInstance_url(),
                contactId
        );
    }

    @DeleteMapping("contacts/{contactId}")
    public void deleteContactById(@PathVariable("contactId") String contactId) {
        AuthenticationResponse authenticationResponse = getAuthenticationResponse();
        contactService.deleteContactById(contactId);
    }

    @PostMapping("contacts/{contactId}/send")
    public String sendHelloEmailToContact(@PathVariable("contactId") String contactId) {
        AuthenticationResponse authenticationResponse = getAuthenticationResponse();
        return contactService.sendHelloEmailToContact(
                authenticationResponse.getAccess_token(),
                authenticationResponse.getInstance_url(),
                contactId
        );
    }

    @GetMapping("accounts")
    public AccountResponse getAllAccounts(@QueryParam("limit") Integer limit) {
        AuthenticationResponse authenticationResponse = getAuthenticationResponse();
        return accountService.selectAllAccounts(
                authenticationResponse.getAccess_token(),
                authenticationResponse.getInstance_url(),
                Optional.ofNullable(limit)
        );
    }

    @GetMapping("accounts/{accountId}")
    public AccountResponse getAccountById(@PathVariable("accountId") String accountId) {
        AuthenticationResponse authenticationResponse = getAuthenticationResponse();
        return accountService.selectAccountById(
                authenticationResponse.getAccess_token(),
                authenticationResponse.getInstance_url(),
                accountId
        );
    }

    @PostMapping("accounts/{accountId}")
    public void saveAccountById(@PathVariable("accountId") String accountId) {
        AuthenticationResponse authenticationResponse = getAuthenticationResponse();
        accountService.saveAccountById(
                authenticationResponse.getAccess_token(),
                authenticationResponse.getInstance_url(),
                accountId
        );
    }

    @DeleteMapping("accounts/{accountId}")
    public void deleteAccountById(@PathVariable("accountId") String accountId) {
        accountService.deleteAccountById(accountId);
    }


}
