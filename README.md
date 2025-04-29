## Complaint REST API

### HOW TO RUN:
##### Using docker-compose

- **Locally add a file named .env where docker-compose.yaml is**
  Variables to set:

  - DB_HOST=mysql
  - DB_NAME=complaint_rest_api
  - DB_PASSWORD=password
  - DB_PORT=3306
  - DB_USERNAME=root

- Open Docker Desktop

- Open terminal in the root path of the project containing docker-compose.yaml
- Run '''mvn package'''

- After jar i ready run ```docker compose -up -d``` command

### TESTING THE APLLICATION
- Run ```mvn integration-test```   for integration tests

### DOCS

- See: http://localhost:8080/swagger-ui/index.html#
