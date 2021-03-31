package com.example.salesforcespringbootv2.query;

import org.springframework.stereotype.Component;

@Component
public class QueryProvider {

    private String mainUrl = "/services/data/v51.0/query?q=SELECT ";
    private String queryAllContacts = mainUrl + "Id, Name, Account.Name, Title, Phone, Email FROM Contact";
    private String queryContactById = mainUrl + "Id, Name, AccountId, Title, Phone, Email FROM Contact WHERE Id = ";
    private String queryAllAccounts = mainUrl + "Id, Name, AnnualRevenue, Industry, Phone FROM Account";
    private String queryAccountById = mainUrl + "Id, Name, AnnualRevenue, Industry, Phone FROM Account WHERE Id = ";

    public String getMainUrl() {
        return mainUrl;
    }

    public String getQueryAllContacts() {
        return queryAllContacts;
    }

    public String getQueryContactById() {
        return queryContactById;
    }

    public String getQueryAllAccounts() {
        return queryAllAccounts;
    }

    public String getQueryAccountById() {
        return queryAccountById;
    }
}
