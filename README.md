Dauphin é uma aplicação para quem gosta de nadar e pratica a natação. Nele você pode criar treinos compartilhados, planos de treinamento, registrar seus treinos, visualizar sua evolução, definir metas para o futuro e criar competições (privadas ou públicas).

> Acesse [`updated.md`](https://github.com/ikuyorih9/Dauphin/blob/main/updates.md) para visualizar o ***histórico de atualizações e registro de tecnologias*** da aplicação.

> Acesse [`database.md`](https://github.com/ikuyorih9/Dauphin/blob/main/database.md) para visualizar a ***estrutura da base de dados***.

# 🎲 Estrutura da base de dados

O diagrama entidade-relacionamento apresentado na Figura 1 apresenta a formação básica da base de dados. Para informações mais detalhadas, incluindo regras, restrições e outros diagramas, veja o <a href="database.md">database.md</a>.

![database](images/Dauphin.jpg)
###### *<center>Figura 1: diagrama ER da base de dados.</center>* 

# 🌐 Endpoints da API

## 🔗 De autenticação `auth/`


🔴 `auth/signup`: 

* ➡️ **Descrição**: realiza o cadastro de um usuário;
* ➡️ **Method**: POST; 
* ➡️ **Return status**: *CREATED* (201), *BAD REQUEST* (400), *CONFLICT* (409);
* ➡️ **Body**: 
```
{
  "username": "string",
  "email": "string",
  "nome": "string",
  "senha": "string",
  "sexo": "string",
  "dataNascimento": "date",
  "foto": "string (URL ou base64)"
}
```

## 👤 De usuários `users/`

🔴 `users/me`:
* ➡️ **Descrição**: realiza a exclusão de uma conta;
* ➡️ **Method**: DELETE; 
* ➡️ **Return status**: 
* ➡️ **Body**: 

🔴 `users/friends`:
* ➡️ **Descrição**: cria uma amizade entre dois usuários;
* ➡️ **Method**: POST; 
* ➡️ **Return status**: *CREATED* (201), *BAD REQUEST* (400), *CONFLICT* (409);
* ➡️ **Body**: 
```
{
    "username1": string,
    "username2": string
}
```

🔴 `users/friends`:
* ➡️ **Descrição**: apaga uma amizade entre dois usuários;
* ➡️ **Method**: DELETE; 
* ➡️ **Return status**: *OK* (200), *NOT FOUND* (404);
* ➡️ **Body**: 
```
{
    "username1": string,
    "username2": string
}
```

🔴 `users/group`:
* ➡️ **Descrição**: cria um grupo de amigos;
* ➡️ **Method**: POST; 
* ➡️ **Return status**: *CREATED* (201), *NOT FOUND* (404);
* ➡️ **Body**: 
```
{
    "username": string,
    "nome": string,
    "qtdMaxima": number,
    "visibilidade": string
}
```

🔴 `users/group`:
* ➡️ **Descrição**: apaga um grupo de amigos;
* ➡️ **Method**: DELETE; 
* ➡️ **Return status**: *OK* (200), *NOT FOUND* (404);
* ➡️ **Body**: 
```
{
    "id": number
}
```

🔴 `users/group/{id}/invite`:
* ➡️ **Descrição**: cria e disponibiliza um token para entrar no grupo;
* ➡️ **Method**: GET; 
* ➡️ **Return**: (String) token criado;
* ➡️ **Path variable**: `id : number`, o número ID do grupo;
* ➡️ **Body**: 
```
{
    "id": number
}
```

🔴 `users/group/{id}/join`:
* ➡️ **Descrição**: acessa e entra no grupo pelo convite.
* ➡️ **Method**: POST; 
* ➡️ **Return status**: *OK* (200), *NOT FOUND* (404), *NOT ACCEPTABLE* (406);
* ➡️ **Path variable**: `id : number`, o número ID do grupo;
* ➡️ **Body**: 
```
{
    "token": string
}
```

