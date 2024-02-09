
# Projeto desenvolvido com Rocketseat no evento NLW Expert

Na trilha de Java, o projeto oferecido pela Rocketseat foi uma aplicação onde você consegue tirar certificados, realizando uma prova de cada linguagem.

## Tecnologias utilizadas 

- Spring WEB
- Spring Data
- Docker
- PostgreSQL
- Lombok
- Spring DevTools

## Endpoints
### StudentController:

- POST: http://localhost:8080/students/verifyIfHasCertification (para verificar se o estudante possui um certificado com determinada tecnologia)
- POST: http://localhost:8080/students/certification/answer (para responder às questões)

### QuestionController

- GET: http://localhost:8080/technology/{tecnologia} (busca as questões por linguagem)

### RankingController

- GET: http://localhost:8080/ranking/top10 (busca os Top 10 alunos com mais acertos)

## Configurando o Postgres e Docker no projeto

Vá até o arquivo `application.properties` e insira as informações correspondentes ao seu banco de dados PostgreSQL.

```
spring.datasource.url=jdbc:postgresql://localhost:5432/{NomeDoDB} (Certifique-se de que sua porta do PostgreSQL é a mesma)
spring.datasource.username={usuário do PostgreSQL}
spring.datasource.password={senha do PostgreSQL}
```

Depois de configurar o `application.properties`, vá até o arquivo `docker-compose.yml` e altere os itens correspondentes ao ambiente.

```yaml
environment:
  - POSTGRES_USER={usuário do PostgreSQL}
  - POSTGRES_PASSWORD={senha do PostgreSQL}
  - POSTGRES_DB={NomeDoDB}
```

---

Espero que isso ajude! Se precisar de mais alguma coisa, estou à disposição.
