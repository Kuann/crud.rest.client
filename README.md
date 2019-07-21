# CRUD Rest client example
How to consume rest apis in Java:

| Method   |      URL           |  Description                 |  Status Code  |
|----------|:------------------:|-----------------------------:|--------------:|
| GET      |  /demo/employees   | Get all employees            | 200 OK        |
| GET      |  /demo/employees/1 | Get a specific employee      | 200 OK        |
| GET      |  /demo/employees/5 | Get a non-existing employee  | 204 NO CONTENT|
| POST     |  /demo/employees   | Create new employee          | 200 OK        |
| GET      |  /demo/employeess  | Not found URL                | 404 NOT FOUND |

Libraries:
https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.5.9
https://mvnrepository.com/artifact/com.google.code.gson/gson
