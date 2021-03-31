# salesforce_springboot_api
This API allows to check standard salesforce objects Contact and Account from your org, save it in your own database and send emails to specific Contact without contravene salesforce governor limits

## Run

* Download or clone repository and run it in IntelliJ IDEA
* Go to ```salesforce-springboot-v2\src\main\resources``` and under ```postgres configuration``` tab in ```spring.datasource.url``` connect with your Postgres database,
in ```spring.datasource.username``` and ```spring.datasource.password```
enter your username and password to database, in ```spring.datasource.username``` and ```spring.datasource.password```.
Rest leave at it is.

![postgres config](https://user-images.githubusercontent.com/57005090/113119620-f0ae0300-9210-11eb-981d-d9fbbe89395c.png)


```login.password```
```login.client_secret```
```login.client_id```
