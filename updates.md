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

## 29/01/2025 - Telas de login e cadastro

### Login e cadastro no frontend

Para comunicação com a base de dados, é preciso utilizar das requisições HTTP em conjunto com o backend. Dessa forma, foi feita duas telas: a `index.html` e `cadastro.html`, com seus respectivos scripts `.js`. 

A primeira possui um formulário pedindo por nome de usuário e senha, que faz uma requisição POST no endpoint `/api/auth/login`; se a resposta do servidor for bem sucedida, então um alerta de "login bem sucedido" é lançado. A segunda também possui um formulário com outras informações, e faz uma requisição POST no endpoint `api/usuarios/cadastrar`; é esperado uma resposta em JSON de sucesso ou falha.

### ErrorResponse e ApiResponse

A resposta do backend para o frontend é traduzida de uma instância de uma das classes `ErrorResponse` e `ApiResponse`. Mas confesso que ambas deveriam ser somente uma única classe, pois ambas tem uma estrutura parecida:
```
public class ErroResponse {
    private int status;
    private String message;
    private String timestamp;
}

public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
}
```

### Login e cadastro no backend

Com a requisição `@PostMapping("/login")` de login, é preciso criar um Data Transfer Object (DTO) *LoginRequest*, que representa uma entidade mínima para o usuário, com apenas username e senha. Primeiro, o *AuthController* procura por um usuário de mesmo nome na base de dados e se a senha do usuário do request é a mesma da base. Eventualmente, se o usuário existir, o login será bem sucedido.

```
Optional<Usuario> user = userRepository.findByUsername(loginRequest.getUsername());

if (user.isPresent() && user.get().getSenha().equals(loginRequest.getSenha())){
    return ResponseEntity.ok("Login bem-sucedido!");
} 
else {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos.");
}
```

Para o cadastro a anotação `@PostMapping("/cadastrar")` indica a função `public ResponseEntity<ApiResponse<Usuario>> cadastrarUsuario(@RequestBody Usuario usuario)`, que chama a `usuarioService.cadastrarUsuario` do serviço. No serviço, há duas verificações: uma de nome de usuário existente e outra para o email.

```
Optional<Usuario> usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername());
if (usuarioExistente.isPresent()) {
    throw new RuntimeException("O nome de usuário já está em uso.");
}

// Verifica se o email já está em uso
Optional<Usuario> emailExistente = usuarioRepository.findByEmail(usuario.getEmail());
if (emailExistente.isPresent()) {
    throw new RuntimeException("O email já está em uso.");
}
```

 O RuntimeException é capturado pela GlobalExceptionHandler, que é executada sempre que uma runtime exception ocorre. Mas eu sinceramente prefiro fazer o tratamento de erro localmente e devo corrigir isso.
