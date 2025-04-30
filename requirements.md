# 📑 Documento de requisitos

## 🎯 Objetivo

O projeto **Dauphin** tem como objetivo fornecer uma plataforma online para montagem de treinos personalizados, acompanhamento da evolução física dos usuários e gerenciamento de métricas relacionados à prática de exercícios físicos. Para auxiliar no desenvolvimento físico do usuário, ele pode se juntar à outros usuários em grupos e, juntos, compartilhar treinos, metas, conquistas etc., além de poder participar de competições criadas por outros usuários e protagonizar em um pódio de melhor desempenho.

## 👤 Perfis de Usuário

O sistema Dauphin será utilizado por usuários que desejam planejar, acompanhar e compartilhar sua rotina de exercícios físicos, progresso e participação em grupos e competições.

### 1. Usuário Padrão
Usuário comum que utilizará o sistema para:
- Cadastrar exercícios, treinos e planos.
- Criar metas pessoais e registrar métricas de evolução.
- Conectar-se com amigos para acompanhar o progresso.
- Participar de grupos privados e competições.

### 2. Usuário com Papéis Específicos em Grupos
Dentro dos grupos privados, o usuário poderá ter papéis com diferentes permissões:
- **Host**: cria o grupo e tem controle total (editar, remover membros, criar metas conjuntas).
- **Administrador**: auxiliar na gestão do grupo (remover/invitar usuários).
- **Membro**: participa de interações e metas conjuntas.

Obs.: Um mesmo usuário pode participar de vários grupos com diferentes papéis.

> Não há, neste primeiro momento, um tipo de “administrador do sistema” (como papel técnico ou de suporte).

## ⚙️ Requisitos funcionais (RF)

| CÓDIGO | REQUISITO |
|:--------:|:----------:|
| RF01 | O sistema deve permitir o cadastro de usuários, identificados pelo seu *username* ou *email*.|
| RF02 | O sistema deve permitir login/autenticação de usuários. |
| RF03 | O sistema deve permitir que um usuário crie **amizade** com vários usuários. | 
| RF04 | O sistema deve permitir que um usuário crie **planos de treino**, que consistem em um conjunto de treinos definidos para um período de tempo. |
| RF05 | O sistema deve permitir que o usuário adicione **exercícios** a um treino. |
| RF06 | O sistema deve permitir que o usuário adicione **treinos** a um plano de treino. |
| RF07 | O sistema deve permitir que o usuário crie **metas pessoais**. |
| RF08 | O sistema deve permitir que o usuário crie **grupos privados**. |
| RF09 | O sistema deve permitir que um usuário *com permissão* possa criar **metas conjuntas** para um grupo que participa. |
| RF10 | O sistema deve permitir que um usuário crie **competições públicas ou privadas**. |
| RF11 | O sistema deve permitir que o usuário seja categorizado em **níveis**.| 
| RF12 | O sistema deve mostrar o histórico de métricas e gerar gráficos de tendência. |
| RF13 | O sistema deve permitir a consulta da pontuação e nível atuais do usuário. |
| RF14 | O sistema deve permitir a consulta e visualização do histórico de plano de treinos, treinos e competições do usuário. |
| RF15 | O sistema deve permitir a consulta e visualização de plano de treinos, treinos e competições vigentes do usuário. |
| RF16 | O sistema deve permitir a consulta e visualização do histórico de metas pessoais e conjuntas do usuário. |
| RF17 | O sistema deve permitir a consulta e visualização de metas pessoais e conjuntas vigentes do usuário. |
| RF18 | O sistema deve permitir a consulta de amizades criadas. |
| RF19 | O sistema deve permitir a consulta de grupos vigentes que o usuário participa. |


## 🔧 Requisitos não funcionais (RNF)

| CÓDIGO | REQUISITO | TIPO |
|:--------:|:-----------:|:------:|
| RNF01  | A API deve estar implementada em Java 21+ usando Spring Boot 3.4.3+. | Técnico |
| RNF02  | O banco de dados relacional adotado será o PostgreSQL. | Técnico |
| RNF03  | Todas as APIs devem seguir o padrão RESTful e retornar objetos no formato JSON. | Técnico |
| RNF04  | A autenticação deve ser baseada em OAuth2 com tokens JWT (Bearer Token). | Segurança |
| RNF05  | O sistema deve garantir que apenas usuários autenticados possam acessar seus dados. | Segurança |
| RNF06  | As senhas devem ser armazenadas com hash seguro. | Segurança |
| RNF07  | A comunicação com o sistema deve ocorrer exclusivamente via HTTPS. | Segurança |
| RNF08  | O sistema deve implementar controle de acesso baseado em papéis (roles). | Segurança |
| RNF09  | O sistema deve possuir testes unitários com cobertura mínima de 70% nas classes de domínio. | Manutenibilidade |
| RNF10  | O frontend (futuro) deve ser responsivo e compatível com navegadores modernos. | Usabilidade |
| RNF11  | O sistema deve fornecer mensagens claras de erro e feedbacks ao usuário. | Usabilidade |
| RNF12  | A arquitetura da API deve ser em camadas (Controller, Service, Repository). | Arquitetura |
| RNF13  | O sistema deve ser modular para facilitar evolução e manutenção futuras. | Arquitetura |

## 🤝 Regras de negócio (RN)

| CÓDIGO | REGRA |
|:--------:|:----------:|
| RN01 | Um usuário só pode visualizar treinos, planos, competições e evoluções de outro usuário se forem amigos. |
| RN02 | A entrada em um grupo privado é permitida apenas se o usuário possuir um token-convite válido. |
| RN03 | Existem três papéis de usuário em um grupo: **Host**, **Administrador** e **Membro**. |
| RN04 | Permissões em grupos são definidas pelo papel do usuário: o **Host** pode criar, editar, convidar e remover; o **Administrador** pode convidar e remover; o **Membro** apenas participa. |
| RN05 | Apenas usuários com papel de **Host** ou **Administrador** podem criar metas conjuntas em grupos. |
| RN06 | Competições podem ser públicas (acesso livre) ou privadas (participação limitada aos amigos do criador). |
| RN07 | O nível de um usuário é calculado com base em sua evolução em planos de treino, participação em competições e outros indicadores de desempenho definidos pelo sistema. |
| RN08 | O usuário deve ter controle total de inserção, edição e exclusão sobre seus próprios dados (perfil, planos, metas, etc.). |