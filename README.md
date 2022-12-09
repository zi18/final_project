# Final Project

## How to run the project

`mvn spring-boot:run`

## Postman

Added a postman collections to test the endpoints under the file `Final Project.postman_collection.json`

## Mysql script

Added a mysql script to create the tables on mysql and also create some users to test under `create_schema.sql`

## Use case diagram

Added a diagram of use case under `use_case.png`

## Basic auth

The basic auth is working with the database, after running the script with the users the endpoints can be tested by using this credentials

`username/password`

`zineb/pass`
`maria/pass`
`luisa/pass`

## Endpoints

#### Create Account

Creates an account for a user, needs to be ADMIN to create accounts.

```
POST /accounts/

Request 
{
    "balance": 10000,
    "secretKey": "zbi",
    "status": "ACTIVE",
    "creditLimit": 50,
    "interestRate": 0.12,
    "accountType": "SAVINGS",
    "primaryOwner": "emma"
}

Response
{
    "id": 3,
    "balance": 1000,
    "secretKey": "zbi",
    "penaltyFee": 40,
    "creationDate": "2022-12-09T13:54:59.8712796",
    "status": "ACTIVE",
    "monthlyMaintenanceFee": null,
    "minBalance": 1000,
    "creditLimit": 50,
    "interestRate": 0.12,
    "accountType": "SAVINGS",
    "primaryOwner": "emma",
    "secondaryOwner": null
}
```

#### Get Accounts

Gets a list of accounts

```
GET /accounts/

Response
[
    {
        "id": 1,
        "balance": 1000.000,
        "secretKey": "zbi",
        "penaltyFee": 40.000,
        "creationDate": "2022-12-09T13:54:50",
        "status": "ACTIVE",
        "monthlyMaintenanceFee": null,
        "minBalance": 1000.000,
        "creditLimit": 50.000,
        "interestRate": 0.120,
        "accountType": "SAVINGS",
        "primaryOwner": "zineb",
        "secondaryOwner": null
    }
]
```

#### Get Account by id

Gets an account by id

```
GET /accounts/{accountId}

Response
{
    "id": 1,
    "balance": 1000.000,
    "secretKey": "zbi",
    "penaltyFee": 40.000,
    "creationDate": "2022-12-09T13:54:50",
    "status": "ACTIVE",
    "monthlyMaintenanceFee": null,
    "minBalance": 1000.000,
    "creditLimit": 50.000,
    "interestRate": 0.120,
    "accountType": "SAVINGS",
    "primaryOwner": "zineb",
    "secondaryOwner": null
}
```

#### Update Account balance by id

Update an account balance by id

```
PATCH /accounts/{accountId}

Reuest
{
    "balance": 3000
}

Response
{
    "id": 1,
    "balance": 1000.000,
    "secretKey": "zbi",
    "penaltyFee": 40.000,
    "creationDate": "2022-12-09T13:54:50",
    "status": "ACTIVE",
    "monthlyMaintenanceFee": null,
    "minBalance": 1000.000,
    "creditLimit": 50.000,
    "interestRate": 0.120,
    "accountType": "SAVINGS",
    "primaryOwner": "zineb",
    "secondaryOwner": null
}
```

#### Transfer from an account to other

Transfer money from an account to another

```
POST /accounts/{accountId}

Reuest
{
    "accountId": 2,
    "amount": 100
}

Response
{
    "id": 1,
    "balance": 1000.000,
    "secretKey": "zbi",
    "penaltyFee": 40.000,
    "creationDate": "2022-12-09T13:54:50",
    "status": "ACTIVE",
    "monthlyMaintenanceFee": null,
    "minBalance": 1000.000,
    "creditLimit": 50.000,
    "interestRate": 0.120,
    "accountType": "SAVINGS",
    "primaryOwner": "zineb",
    "secondaryOwner": null
}
```

#### Delete account

Deletes an account if the user is an ADMIN

```
DELETE /accounts/{accountId}

Response

200

```