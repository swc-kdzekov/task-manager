# task-manager
Task manager simulator

A simple task manager simulator created as REST API service with scheduler.
The service can work with any relational database if properly configured in application.properties.
The current implementation works with Postgres 14.4.

The url of the service is: http(s)//{hostname}:{port}/tasks


Prerequisites:

Create table "Tasks" with follwing script:

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


CRUD api calls for the service

CREATE:
curl --location --request POST 'http://localhost:8080/tasks/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dueDate" : "yyyy-MM-dd",
    "title" : "title",
    "description" : "decription",
    "priority" : 1
}'

READ:
curl --location --request GET 'http://localhost:8080/tasks/fetch?id=b1195456-8794-4c00-9fad-00f797bcd1c8'

UPDATE:
curl --location --request PUT 'http://localhost:8080/tasks/update?id=b1195456-8794-4c00-9fad-00f797bcd1c8' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dueDate" : "yyyy-MM-dd",
    "title" : "title",
    "description" : "decription",
    "priority" : 3
}'

DELETE:
curl --location --request DELETE 'http://localhost:8080/tasks/delete?id=968537aa-2703-4753-8e71-01ab02d496c5'

READ ALL:
curl --location --request GET 'http://localhost:8080/tasks/fetchAll'
