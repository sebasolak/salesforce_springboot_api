package com.example.salesforcespringbootv2.service;

import com.example.salesforcespringbootv2.auth.AuthorizationHeaders;
import com.example.salesforcespringbootv2.model.account.Account;
import com.example.salesforcespringbootv2.model.account.AccountResponse;
import com.example.salesforcespringbootv2.query.QueryProvider;
import com.example.salesforcespringbootv2.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountService {

    private final QueryProvider queryProvider;
    private final AuthorizationHeaders authorizationHeaders;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(QueryProvider queryProvider,
                          AuthorizationHeaders authorizationHeaders, AccountRepository accountRepository) {
        this.queryProvider = queryProvider;
        this.authorizationHeaders = authorizationHeaders;
        this.accountRepository = accountRepository;
    }

    public AccountResponse selectAllAccounts(String accessToken, String instanceUrl, Optional<Integer> limit) {
        HttpHeaders httpHeaders = this.authorizationHeaders.getAuthorizationHeaders(accessToken);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        String queryAllAccounts = queryProvider.getQueryAllAccounts();
        if (limit.isPresent()) {
            queryAllAccounts = queryAllAccounts + " LIMIT " + limit.get();
        }

        ResponseEntity<AccountResponse> accountResponse =
                getAccountResponseEntity(instanceUrl, queryAllAccounts, httpHeaders, params);
        return accountResponse.getBody();
    }

    public AccountResponse selectAccountById(String accessToken, String instanceUrl, String accountId) {
        HttpHeaders httpHeaders = this.authorizationHeaders.getAuthorizationHeaders(accessToken);
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        String queryAccountById = String.format("%s '%s'", queryProvider.getQueryAccountById(), accountId);
        ResponseEntity<AccountResponse> accountResponse =
                getAccountResponseEntity(instanceUrl, queryAccountById, httpHeaders, params);

        return accountResponse.getBody();
    }

    public void saveAccountById(String accessToken, String instanceUrl, String accountId) {
        Account account = selectAccountById(accessToken, instanceUrl, accountId).getRecords().get(0);
        accountRepository.save(account);
    }
    public void deleteAccountById(String accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Account %s not exists in database", accountId)));
        accountRepository.delete(account);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    private ResponseEntity<AccountResponse> getAccountResponseEntity(String instanceUrl,
                                                                     String query,
                                                                     HttpHeaders httpHeaders,
                                                                     MultiValueMap<String, String> params
    ) {
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccountResponse> accountSalesforceResponse = restTemplate.exchange(
                instanceUrl + query,
                HttpMethod.GET,
                request,
                AccountResponse.class
        );
        return accountSalesforceResponse;
    }
}
