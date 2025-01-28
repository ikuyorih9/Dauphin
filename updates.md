# Histórico de atualizações e registro de tecnologias

## 27/01/2025 - Básico da REST API e front-end

### Back-end no springboot

O projeto Dauphin no springboot se estabeleceu com a estrutura de arquivos abaixo.

```
java.com.dauphin.dauphin
├── config/
|   └── CorsConfig.java
├── controller/
|   └── UsuarioController.java
├── model/
|   └── Usuario.java
├── repository/
|   └── UsuarioRepository.java
├── service/
|   └── UsuarioService.java
└── DauphinApplication.java
```

Os diretórios separam os arquivos pela sua função:
- O diretório `config/` apresenta arquivos de configuração do sistema. No caso, o único arquivo `CorsConfig.java` permite que as requisições possam ser feitas por domínios diferentes (`files://` e `localhost`);
- O diretório `controller/` contem os arquivos que mapeiam as requisições HTTP para funções em backend, que no Dauphin são GET e POST, por hora;
- O diretório `model/` contem as entidades básicas do sistema;
- O diretório `repository/` contem os arquivos da camada *repository* , que interagem diretamente com o banco de dados;
- O diretório `service/` apresenta os arquivos da camada de serviço, que está entre as camadas de *repository* e *controller*. Os arquivos *service* montam a lógica da regra de negócio da aplicação;

A aplicação é iniciada através do arquivo `DauphinApplication.java`.

### Front-end da aplicação

O front-end da aplicação é feito em HTML puro, apenas para representar uma interface que posteriormente será feita em AngularJS. Por enquanto, basta um front-end que possa fazer requisições GET e POST para o back-end.

O arquivo `index.html`, que está na pasta *root* do projeto Dauphin, apresenta as marcações HTML e scripts em JavaScript que permitem a listagem dos usuários e o cadastro.

### Base de dados

A base de dados foi feita em PostgreSQL e nomeada simplesmente de *dauphin*. Ela está sendo executada na maquina local (*localhost*) e precisa que o usuário crie sua base dados. É necessário que as configurações da aplicação sejam modificadas para a conexão com a base de dados postgres. O arquivo está em `dauphin/src/main/resources/application.properties`:

```
# Configuração do banco de dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/nome_base_de_dados
spring.datasource.username= seu_username
spring.datasource.password= sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuração do JPA (Hibernate)
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```