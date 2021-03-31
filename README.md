# salesforce_springboot_api
This API allows to check standard salesforce objects Contact and Account from your org, save it in your own database and send emails to specific Contact without contravene salesforce governor limits

## Run

* Download or clone repository and run it in IntelliJ IDEA
* Go to ```salesforce-springboot-v2\src\main\resources``` and under ```postgres configuration``` tab in ```spring.datasource.url``` connect with your Postgres database,
in ```spring.datasource.username``` and ```spring.datasource.password```
enter your username and password to database, in ```spring.datasource.username``` and ```spring.datasource.password```.
Rest leave at it is.

![postgres config](https://user-images.githubusercontent.com/57005090/113119620-f0ae0300-9210-11eb-981d-d9fbbe89395c.png)

* Next in ```login.username``` pass username to your salesforce org, in ```login.password``` pass password to org, in ```login.client_secret```
and ```login.client_id``` pass client secret which you can find in connected app. Rest leave at it is.

![salesforce credentials](https://user-images.githubusercontent.com/57005090/113120524-f5bf8200-9211-11eb-8e5a-e9a1d3def1e8.png)
* Next in ```spring.mail.username and spring.mail.password``` enter valid
gmail email and password if you want api be able to send emails.

![gmail credentials](https://user-images.githubusercontent.com/57005090/113121710-34097100-9213-11eb-84c3-7882134b0e09.png)

##Api map

* You can get list of contacts (GET request):
```
http://localhost:{your_default_port}/api/v1/salesforce/contacts
```
* Get one contact by contact id (GET request):
```
http://localhost:{your_default_port}/api/v1/salesforce/contacts/{contactId}
 ```
 * Save contact by contact id (POST request):
```
http://localhost:{your_default_port}/api/v1/salesforce/contacts/{contactId}
```
 * Delete contact by contact id (DELETE request):
```
http://localhost:{your_default_port}/api/v1/salesforce/contacts/{contactId}
```
 * Send hello message to contact email (if present) using contact id (POST request):
```
http://localhost:{your_default_port}/api/v1/salesforce/contacts/{contactId}/send
```
* You can get list of accounts (GET request):
```
http://localhost:{your_default_port}/api/v1/salesforce/accounts
```
* Get one account by account id (GET request):
```
http://localhost:{your_default_port}/api/v1/salesforce/accounts/{accountId}
```
* Save account by account id (POST request):
```
http://localhost:{your_default_port}/api/v1/salesforce/accounts/{accountId}
```
* Delete account by account id (DELETE request):
```
http://localhost:{your_default_port}/api/v1/salesforce/accounts/{accountId}
```

