# Histórico de atualizações e registro de tecnologias

## 📋 ToDo List
### 🕗 Pendentes...

*  #### 😫📢 Modificações importantes
    - [ ] Adicionar a **META** como entidade genérica para as metas especializadas;
        - [ ] Formular as **Metas** no diagrama relacional e na base de dados;
    - [ ] Formular os exercícios catalogados;
    - [ ] Melhorar a segurança da autenticação;
    - [ ] Implementar um método para manter o usuário logado através de JWT.

* #### 🥱🫸 Modificações secundárias

    
    - [ ] Mudar o endpoint de adicionar amigos para `api/usuarios/{username}/amigos/adicionar`;
    - [ ] Verificar redundância nos *Exceptions* personalizados;

### ✅ Concluídos!!!
    Nada foi concluido...

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

## 12/02/2025 - Reformulação e início da base de dados

Depois de todo o processo feito na última atualização, eu decidi refazer algumas coisas. Primeiro, eu apaguei todos os DTOs, os GlobalExceptionHandler e os Controllers, Services e Repositories. Mantive algumas coisas, mas quase tudo foi reescrito.

### Tratamento de exceções

Para cada requisição HTTP que falha, é preciso retornar o status do erro, como 409 para *conflict* ou 404 para *not found*. Para evitar *hardcode* sobre os status, de modo que o programador não tenha que definir o status pelo seu tipo, um bom uso de *Exceptions* é recomendado.

Os exceptions personalizados ajudam a retornar os status específicos. Para que sua funcionalidade seja efetivada, é preciso configurar (novamente) o `GlobalExceptionHandler`, de modo que os exceptions sejam detectados automaticamente e não sejam de responsabilidade do programador. O `GlobalExceptionHandler` foi configurado como:

```
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex);
    .
    .
    .
}

```

Os *Exceptions* tratados, personalizados ou não, são (até o momento):

* ***DataIntegrityViolationException*** que ocorre quando a integridade da base de dados é violada na inserção, lançando o status `HttpStatus.BAD_REQUEST`;
* ***UniqueKeyException*** que ocorre quando a chave primária é violada na inserção. Lança o status `HttpStatus.CONFLICT`.
* ***UserNotFoundException*** que ocorre quando o usuário não é encontrado na base de dados. Lança o status `HttpStatus.NOT_FOUND`;
* ***ConflictException*** ocorre num conflito genérico de dados. Também lança o status `HttpStatus.CONFLICT`;
* ***RuntimeException*** ocorre em qualquer exception em tempo de execução. Lança `HttpStatus.BAD_REQUEST`.

> 📝 **NOTA:** alguns dos *Exceptions* são repetidos e precisam ser reformulados.

### Mudanças no diagrama ER e ínicio do diagrama relacional

O diagrama ER, até então, continha funcionalidades básicas previstas na base dados, junto a informações de cada entidade. O diagrama ER não passou por uma reformulação, mas novas funcionalidades foram adicionadas:

- ***Criar/Participar de grupos***: uma mudança de planos foi feita e agora as competições vão ser eventos maiores. As competições particulares previstas anteriormente agora serão metas de grupo. Uma correção foi feita em Competição, pois permita apenas participar e não criar, que foi adicionado por último.

- ***Metas de grupo e metas pessoais***: as metas foram divididas em *Meta Conjunta* e *Meta Pessoal*. No diagrama ER, são duas entidades fracas diferentes sem nenhuma relação entre si, mas pretendo colocá-las como especialização de um tipo genérico *Meta*.

- ***Adicionar amigos***: um autorelacionamento N-N foi criado para que seja possível adicionar amigos.

> ⏰ **LEMBRAR**: preciso adicionar a **META** como entidade genérica para as metas especializadas.

No diagrama relacional, apenas os entidades/relacionamentos **USUARIO**, **GRUPO**, **NIVEL** e **TEM_AMIZADE** foram implementados na base de dados. O único deles que foi completamente instanciado, até agora, é o Nivel, que possui as entradas:

```
INSERT INTO Nivel (classificacao, minimo_pontos, descricao) VALUES
('Iniciante', 0, 'Nadador iniciante, aprendendo os fundamentos básicos da natação.'),
('Intermediário', 500, 'Nadador com habilidades básicas, capaz de nadar diferentes estilos com técnica razoável.'),
('Avançado', 1500, 'Nadador experiente, com boa resistência e técnica refinada.'),
('Competitivo', 3000, 'Nadador treinado para competições, com alto desempenho e estratégia de prova.'),
('Elite', 6000, 'Nadador de alto nível, com excelente condicionamento físico e técnica apurada.'),
('Master', 10000, 'Nadador veterano com vasta experiência e participação em competições de alto nível.');
```

### Endpoints configurados

Os endpoints configurados, sabendo das funcionalidades implementadas até agora, são:

* `api/usuario/`: aplicando uma requisição GET é possível obter todos os usuários cadastrados;

* `api/usuarios/cadastrar`: ao enviar um JSON do usuário, ele é salvo na base de dados;

* `api/usuarios/login`: ao enviar um JSON do usuário, verifica-se se o usuário está cadastrado e se a senha corresponde;

* `api/usuarios/{username}/amigos`: enviando uma requisição POST e um JSON de um usuário, é possível adicionar o amigo. Enviando uma requisição GET, é possível obter todos os amigos do usuário `username`;

* `grupos/{username}/criados`: uma requisição POST e um JSON de Grupo, é possível criar um grupo. Uma requisição GET obtém todos os grupos criados por esse usuário;

* `criados/{username}/{id}/apagar`: uma requisição POST, enviando o username e o id do grupo apaga o grupo, se ele existir.

> ⏰ ***LEMBRAR***: o endpoint de adicionar amigos deve ser `api/usuarios/{username}/amigos/adicionar`.

