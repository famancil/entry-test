# entry-test
RESTful API Server - Entry Test

## Requirements

* JDK version 9 (Before it was version 14 but it wouldn't let me upload the service with that version, so I downloaded it to 9).
* Have installed Google Cloud Platform SDK.
* MySQL version 8.0

## URL


There are two routes that access resources: 

/courses : route belonging to resource Course and worked under the standard set for the test for the actions GET, POST, PUT y DELETE.
For the JSON body for POST, the following structure must be followed:

{
	"name":"Course 1",
	"code":"AAAA"
}

In the entity Course, the validation was worked so that the attribute "code" is a string with a maximum of 4 characters.
For the action PUT, it can add either and / or both attributes.

/students: route belonging to resource Course and worked under the standard set for the test for the actions GET, POST, PUT y DELETE.
For the JSON body for POST, the following structure must be followed:

{
	"rut":"181246235",
	"age": 26,
	"name":"Felipe",
	"lastName":"Mancilla Sepúlveda",
	"course_id": 1
}

In the entity Student, the validation was worked so that the "rut" and "course_id" attributes are not null and the "age" attribute
have a value greater than 18.
In the Controller StudentController, the validation of the rut is worked (whether it is delivered with points,
hyphen and / or without any of them).
For the action PUT, it can add either and / or all attributes.

##Configuration MySQL

In the file application.properties, there is a route to access a MySQL instance create in GCP, as well as for localhost.

## Present Problem

The most challenged

The biggest challenge has been uploading the API REST server to the GCP, although an instance was created to communicate 
with MySQL, it has not been possible upload correctly (the best achieved is uploading the service but not being able 
to connect to the MySQL instance, getting a 502 Bad Gateway).
What it is possible to test is to test everything from localhost.

