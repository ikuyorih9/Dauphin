# Estrutura da base de dados.

O diagrama entidade-relacionamento apresentado na Figura 1 apresenta a formação básica da base de dados.
![database](images/Dauphin.jpg)
###### *<center>Figura 1: diagrama ER da base de dados.</center>* 

## Descrição da base de dados

* **EXERCÍCIOS CATALOGADOS** são os exercícios catalogados no sistema, identificados pelo seu ***Nome*** único. Cada exercício contém uma ***Pontuação base*** que contará para a pontuação total do usuário e influenciará no seu nível. Além disso, há outras informações como o ***Estilo*** do exercício (Crawl, Costas, Peito e Borboleta), a ***Dificuldade***, a ***Distância*** exigida pelo exercício, a ***Quantidade*** de repetições, uma breve ***Descrição*** da execução e a URL de um ***Vídeo*** para auxiliar. Cada exercício catalogado pode estar relacionado com vários treinos simultaneamente.

* **TREINOS** são um conjunto de exercícios catalogados. Cada treino pode ser criado por um único usuário e depende dele para existir. Dessa forma, cada treino é identificado pelo seu usuário, em conjunto de seu ***Nome*** único. Os treinos são definidos, ainda, pelo seu ***Estilo*** (Crawl, Costas, Peito, Borboleta e Medley). Uma vez criado, um treino pode ser executado por vários usuários e pode estar em vários planos de treinamento.

* **TREINO CONCLUIDO** é o resultado da execução de um treino pelo usuário. Ele é identificado pelo usuário, pelo treino e pela ***Data/Hora de conclusão***. Cada conclusão de treino carrega consigo as estatísticas finais, como o ***BPM médio***, ***Tempo total***, ***Calorias***, ***Distância*** e a ***Porcentagem de conclusão***. Cada exercício concluído recebe uma ***Pontuação***, que é obtida com base nas pontuações dos exercícios do treino e no percentual de conclusão.

* **PLANO DE TREINAMENTO** é montado por um único usuário e internamente identificado pelo seu ***Nome*** único. Um plano de treinamento pode conter vários treinos e cada par (Plano, Treino) gera uma atividade. Conforme o plano de treinamento é realizado, a ***Porcentagem de conclusão*** indica a sua conclusão.

* **ATIVIDADE** é o resultado de um relacionamento entre um plano de treinamento e um treino. Sempre que um treino for inserido num plano, uma atividade é gerada e, por isso, ela é identificada por ambas as entidades, conjuntamento pela ***Data/Hora*** estipulada.

* **USUÁRIO** é o ponto central da base: ele pode criar e executar vários treinos, montar vários planos, estabelecer várias metas e participar de várias competições. Dentro do sistema, todo usuário vai ter um ***Id*** que o identifica univocamente. No cadastro no sistema, informações como ***Login***, ***Senha***, ***Email*** e ***Nome*** são obrigatórios. Outras informações como ***Sexo*** e ***Idade*** são opcionais. Cada usuário possui uma ***Pontuação Total***, definida como 0 no momento do cadastro e aumentando conforme a execução de treinos. A pontuação é importante para definir o único nível de cada usuário dentro do sistema.

* **NÍVEL** se refere ao progresso do usuário na execução de seus treinos. Cada nível é identificado por sua ***Classificação*** (iniciante, intermediário, avançado, ...) e tem um ***Mínimo de pontos*** necessários para atingí-lo. A ***Descrição*** é uma mensagem de apoio, junto a explicação do nível e como atingir o próximo. Um mesmo nível pode ser atribuído a diversos usuários.

* **META PESSOAL** é um conjunto de objetivos definido por um usuário e, por isso, ela é definida pelo usuário, em conjunto com seu ***Tipo***. O tipo é, especificamente, uma métrica da meta, como distância, frequência, tempo etc. que é indicada numericamente pelo ***Valor***. É importante que as metas possuam ***Data de início*** e ***Data de fim***, sendo esse último definido pelo usuário e depende se a meta é aberta ou fechada. A ***Porcentagem de conclusão*** indica o progesso de conclusão da meta e define um ***Status*** para dar uma visão geral para o usuário.

## Restrições e observações

* Para garantir a consistência da base de dados, é preciso que uma instância de **TREINO** corresponda o *Estilo* com todas as suas instâncias de **EXERCÍCIOS CATALOGADOS**. Essa restrição não é válida somente para o treino do tipo *Medley*, que pode conter exercícios de vários Estilos. Por exemplo, um treino de estilo *Crawl* não pode ter exercícios do estilo *Peito*, mas um treino de estilo *Medley* pode ter exercícios de todos os estilos.

* A entidade **TREINO CONCLUÍDO** deve ser uma agregação pois, dado uma instância de usuário e de treino, deve ser permitido que elas se relacionem mais de uma vez na base de dados. Dessa forma, surge o atributo ***Data/Hora de conclusão***, para garantir a unicidade do relacionamento.

* Da mesma forma, uma ***ATIVIDADE*** deve ser uma agregação para garantir que uma instância de usuário e de treino possam se relacionar mais de uma vez, visto que um plano de treinamento pode ter repetição de treinos. A ***Data/Hora*** garante a unicidade do relacionamento.

* As strings pré-definidas, como a ***Classificação*** em **NÍVEL**, os ***Estilos*** em **TREINO** e **EXERCÍCIOS CATALOGADOS**, os ***Tipos*** em **META PESSOAL** etc., devem ser garantidas em aplicação.