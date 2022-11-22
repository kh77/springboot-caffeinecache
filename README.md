# SpringBoot- Caffeine Cache

- Java 11
- MySQL

There are two ways to configure :
- application.properties file 
- code (using this way)
- Create schema: CREATE SCHEMA employee_management ;

### Payload :
 
- Create Employee


 ` curl --location --request POST 'localhost:8080/employees' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "name" : "hello-world"
  }'`


- Get Employee by id, first time it will hit database and second time it will retrieve by cache


`  curl --location --request GET 'localhost:8080/employees/1' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "name" : "hello-world"
  }'`


- List of Cache names


`  curl --location --request GET 'localhost:8080/cache/name' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "name" : "hello-world"
  }'`


- How many elements are present in the specific cache


  `curl --location --request GET 'localhost:8080/cache/data/employees' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "name" : "hello-world"
  }'`


  Response:

  
  `{
    "4": {
    "headers": {},
    "body": {
    "value": {
    "id": 4,
    "name": "hello-world 4"
    },
    "empty": false,
    "present": true
    },
    "status": "OK",
    "statusCode": "OK",
    "statusCodeValue": 200
    }
  }
`

