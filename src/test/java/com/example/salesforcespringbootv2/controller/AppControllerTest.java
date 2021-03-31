package com.example.salesforcespringbootv2.controller;

import com.example.salesforcespringbootv2.auth.AuthenticationResponse;
import com.example.salesforcespringbootv2.auth.AuthenticationService;
import com.example.salesforcespringbootv2.model.account.Account;
import com.example.salesforcespringbootv2.model.account.AccountResponse;
import com.example.salesforcespringbootv2.model.contact.Contact;
import com.example.salesforcespringbootv2.model.contact.ContactResponse;
import com.example.salesforcespringbootv2.service.AccountService;
import com.example.salesforcespringbootv2.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AppControllerTest {

    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private ContactService contactService;
    @Mock
    private AccountService accountService;
    private AppController underTest;

    private List<Contact> contactsMock = Arrays.asList(
            new Contact(
                    "00309000006xiELAAY", "Stella Pavlova", "0010900000AoeruAAB",
                    "SVP, Production", "(212) 842-5500", "spavlova@uog.com"
            ),
            new Contact(
                    "00309000006xiENAAY", "Babara Levy", "0010900000AoervAAB",
                    "SVP, Operations", "(503) 421-7800", "b.levy@expressl&t.net"
            ),
            new Contact(
                    "00309000006xiEVAAY", "Avi Green", "0010900000AoeruAAB",
                    "CFO", "(212) 842-5500", "agreen@uog.com"
            )
    );

    private List<Account> accountsMock = Arrays.asList(
            new Account(
                    "0010900000AoerpAAB", "Edge Communications", 139000000L,
                    "Electronics", "(512) 757-6000"
            ),
            new Account(
                    "0010900000AoerqAAB", "Burlington Textiles Corp of America", 350000000L,
                    "Apparel", "(336) 222-7000"
            ),
            new Account(
                    "0010900000AoerrAAB", "Pyramid Construction Inc.", 950000000L,
                    "Construction", "(014) 427-4427"
            )
    );
    private AuthenticationResponse authenticationResponse;
    String exampleToken = "token1", exampleUrl = "www.example.com";

    @BeforeEach
    void setUp() {
        underTest = new AppController(contactService, authenticationService, accountService);
        authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccess_token(exampleToken);
        authenticationResponse.setInstance_url(exampleUrl);
    }

    @Test
    void canGetAllContacts() {
        //given
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setRecords(contactsMock);

        given(authenticationService.login()).willReturn(authenticationResponse);
        given(contactService.selectAllContacts(exampleToken, exampleUrl, Optional.empty()))
                .willReturn(contactResponse);

        //when
        ContactResponse allContacts = underTest.getAllContacts(null);

        //then
        assertThat(allContacts).isEqualTo(contactResponse);
        verify(contactService).selectAllContacts(exampleToken, exampleUrl, Optional.empty());
    }

    @Test
    void canGetContactById() {
        //given
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setRecords(contactsMock);

        String exampleId = "00309000006xiELAAY";
        given(authenticationService.login()).willReturn(authenticationResponse);
        given(contactService.selectContactById(exampleToken, exampleUrl, exampleId))
                .willReturn(contactResponse);

        //when
        ContactResponse contactById = underTest.getContactById(exampleId);

        //then
        assertThat(contactById).isEqualTo(contactResponse);
        verify(contactService).selectContactById(exampleToken, exampleUrl, exampleId);

    }

    @Test
    void canSaveContactById() {
        //given
//        Contact contact = contactsMock.get(0);

//        doNothing().when(contactService).saveContactById(exampleToken, exampleUrl, contact.getId());

        //when
//        underTest.saveContactById(contact.getId());

        //then
//        verify(accountService,times(1)).saveAccountById(exampleToken, exampleUrl, contact.getId());
    }

    @Test
    void canDeleteContactById() {
        //given
        Contact contact = contactsMock.get(0);
        doNothing().when(contactService).deleteContactById(contact.getId());

        //when
        underTest.deleteContactById(contact.getId());

        //then
        verify(contactService).deleteContactById(contact.getId());
    }

    @Test
    void canSendHelloEmailToContact() {
        //given
        given(authenticationService.login()).willReturn(authenticationResponse);
        String exampleId = "00309000006xiELAAY";
        String message = "Hello Joe, how are you in this beautiful SATURDAY?";
        given(contactService.sendHelloEmailToContact(exampleToken, exampleUrl, exampleId))
                .willReturn(message);

        //when
        String resultMsg = underTest.sendHelloEmailToContact(exampleId);

        //then
        assertThat(resultMsg).isEqualTo(message);
        verify(contactService).sendHelloEmailToContact(exampleToken, exampleUrl, exampleId);
    }

    @Test
    void throwAnExceptionWhenSendHelloEmailToContactWithNullEmail() {
        //given
        given(authenticationService.login()).willReturn(authenticationResponse);
        String exampleId = "00309000006xiELAAY";
        String message = "Hello Joe, how are you in this beautiful SATURDAY?";
        String errorMsg = String.format(
                "Contact with id %s and name Joe does not provided email",
                exampleId
        );
        given(contactService.sendHelloEmailToContact(exampleToken, exampleUrl, exampleId))
                .willThrow(new IllegalArgumentException(errorMsg));


        //when
        //then
        assertThatThrownBy(() -> underTest.sendHelloEmailToContact(exampleId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(errorMsg);
        verify(contactService).sendHelloEmailToContact(exampleToken, exampleUrl, exampleId);

    }

    @Test
    void canGetAllAccounts() {
        //given
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setRecords(accountsMock);

        given(authenticationService.login()).willReturn(authenticationResponse);
        given(accountService.selectAllAccounts(exampleToken, exampleUrl, Optional.empty()))
                .willReturn(accountResponse);

        //when
        AccountResponse allAccounts = underTest.getAllAccounts(null);

        //then
        assertThat(allAccounts).isEqualTo(accountResponse);
        verify(accountService).selectAllAccounts(exampleToken, exampleUrl, Optional.empty());

    }

    @Test
    void getAccountById() {
        //given
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setRecords(accountsMock);

        String exampleId = "0010900000AoerpAAB";
        given(authenticationService.login()).willReturn(authenticationResponse);
        given(accountService.selectAccountById(exampleToken, exampleUrl, exampleId))
                .willReturn(accountResponse);

        //when
        AccountResponse accountById = underTest.getAccountById(exampleId);

        //then
        assertThat(accountById).isEqualTo(accountResponse);
        verify(accountService).selectAccountById(exampleToken, exampleUrl, exampleId);
    }

    @Test
    void canDeleteAccountById() {
        //given
        Account account = accountsMock.get(0);
        doNothing().when(accountService).deleteAccountById(account.getId());

        //when
        underTest.deleteAccountById(account.getId());

        //then
        verify(accountService).deleteAccountById(account.getId());
    }
}