# Task manager simulator

A simple task manager simulator created as REST API service with scheduler.<br />
The service is written to work with Postgres and other compatible sql dialects.<br />
The database connection configuration is in **application.properties**<br />
The current implementation works with Postgres 14.4.<br />
The Url of the service is: *http(s)://{host}:{port}/tasks<br />*
<br />
### Prerequisites
Create a table using the following sql script<br />
```
CREATE TABLE IF NOT EXISTS "Tasks"."Tasks"
(
    id uuid NOT NULL,
    "createdAt" timestamp without time zone NOT NULL,
    "updatedAt" timestamp without time zone,
    "dueDate" date NOT NULL,
    "resolvedAt" timestamp without time zone,
    title character(50) COLLATE pg_catalog."default" NOT NULL,
    description character(500) COLLATE pg_catalog."default",
    priority integer,
    status boolean DEFAULT false,
    CONSTRAINT "Tasks_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS "Tasks"."Tasks"
    OWNER to postgres;
```
<br /><br />
### API calls
CREATE: <br />
```
curl --location --request POST 'http://localhost:8080/tasks/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dueDate" : "yyyy-MM-dd",
    "title" : "title",
    "description" : "description",
    "priority" : "1"
}'
```
READ: <br />
```
curl --location --request GET 'http://localhost:8080/tasks/fetch?id=b1195456-8794-4c00-9fad-00f797bcd1c8'
```
UPDATE: <br />
```
curl --location --request PUT 'http://localhost:8080/tasks/update?id=b1195456-8794-4c00-9fad-00f797bcd1c8' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dueDate" : "yyyy-MM-dd",
    "title" : "title",
    "description" : "description",
    "priority" : 3
}'
```
DELETE: <br />
```
curl --location --request DELETE 'http://localhost:8080/tasks/delete?id=968537aa-2703-4753-8e71-01ab02d496c5'
```
READ ALL:  <br />
```
curl --location --request GET 'http://localhost:8080/tasks/fetchAll'
```
