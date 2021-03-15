# Gender guess

Simple Java application that can detect gender by given name.

Gender is determined by the tokens - names in gender-specific files - as per instructions in e-mail.


## Running locally

Execute with use of the Spring Boot Maven plugin:

```
mvn spring-boot:run
```

## Usage

### Get all tokens

#### GET /genders

Example: http://localhost:8080/genders

Response body:

```
[
    "Jacek",
    "Jakub",
    "Aaron"...
]
```

### Guess gender

#### GET /genders/{name}
RequestParam fullName - boolean (optional)

Example: http://localhost:8080/genders/Jan%20Rokita

Response body

```
MALE
```

Example: http://localhost:8080/genders/Jan%20Maria%20Rokita?fullName=true

Response body

```
INCONCLUSIVE
```
