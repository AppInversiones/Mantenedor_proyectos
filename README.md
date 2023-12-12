# inversiones-mantenedor-de-proyectos

Este proyecto de Spring Boot proporciona un servicio de mantencion de proyectos a invertir. :

- Listar usuarios registrados.
- Buscar usuario registrado por id.
- Crear proyectos (sólo administradores).
- editar proyectos (sólo administradores).
- Eliminar usuario registrado (sólo administradores).



## Authors

- [@EugenioMuñoz](https://github.com/kenomun/inversiones-acceso)

## Enlaces Adicionales

- Jira (Proyecto APP INVERSIONES BACKEND):** [https://kenomun.atlassian.net/jira/software/projects/AIB/boards/1](https://kenomun.atlassian.net/jira/software/projects/AIB/boards/1)

## Requisitos

- Java 8
- Maven 3.x

## Dependencias

- Spring Boot 2.7.16
- Springfox Swagger 3.0.0
- Spring Boot Validation
- Spring Boot Data JPA
- Argon2-jvm 2.5
- jjwt 0.9.1
- Lombok 1.18.24
- JUnit 5.9.1
- Mockito 4.9.0
- h2database
- Feinght 

## Base de Datos

- La aplicacion trabaja con una base de datos en memoria la cual se genera al momento de levantar el servicio.
- Al momento de generar la base de datos de forma automatica esta llenara las tablas con los siguientes datos:

  proyects:
    * name: Proyecto prueba 1example.com, daily_profit: 3.5
    * name: Proyecto prueba 2, daily_profit: 5.0


para examinar la base de datos entrar al link siguiente una vez levantado el servicio:

* User Name: sa
* Password: password

- http://localhost:8081/h2-console

## Para las pruebas en Postman se podr aimportar la coleecion de las siguiente dirección:

- https://api.postman.com/collections/20444141-702393e7-8f71-4ff3-94c4-169f9e86c15a?access_key=PMAT-01HGXJKR0Z5SQ6C6WTZ496QJ7E

## Documentación

La documentación de la API está disponible a través de Swagger UI. Después de iniciar la aplicación, visita:

- http://localhost:8081/swagger-ui/index.html
