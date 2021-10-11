# spring-boot-example-crud-In-Memory-DB

Simple crud example project. Data can be loaded with a data.sqll and schema.sqll or with bootstrap.

put post delete and update rest api.
runs on localhost: 8089

#Endpoint documentation
* Json: http://localhost:8089/api-json-documentation
* ui: http://localhost:8089/api-ui-documentation.html

#H2 Database
* http://localhost:8080/h2-console
* use version 1.4.199
* jdbc:h2:mem:studentdb

#example POST request in IntelliJ
````
POST http://localhost:8089/
Content-Type: application/json

{
  "firstName": "Edmund",
  "lastName": "Elephant",
  "email": "edmund@yahoo.com"
}

<> 2021-06-15T125431.500.json
<> 2021-06-15T125318.500.json
<> 2021-06-15T125225.500.json
````

#Example DELETE request in intelliJ
````
DELETE http://localhost:8089/1

<> 2021-06-15T131110.500.json
<> 2021-06-15T130955.404.json
<> 2021-06-15T130752.404.json
````
#Example PUT request in intelliJ
````
PUT http://localhost:8089/1?name=Peter&email=peter@yahoo.com
Content-Type: application/json
````