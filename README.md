# Projeto Votação em Sembleia 

Projeto para controle de votações em assembleia, utilizando os seviços da AMAZON - AWS, utilizando SpringBoot, SpringData, Maven, mysql, Docker compose, MapStruct, DTO - Data Transfer Object, Design Patterns, lombok, mensageria com rabbitmq, restassured, Logger, Patterns Reposiroty, Junit, Swagger-API, Clean Code e mais. 


# Serviços AWS
Amazon RDS - MySQL no Amazon RDS
https://aws.amazon.com/pt/rds/

Amazon Elastic Container Service - ECS
https://aws.amazon.com/pt/ec2/

Amazon Elastic Container Registry - ECR
https://aws.amazon.com/pt/ecr/

Link de Acesso a API - Publica
http://xx.xx.117.143:8089/votacaoapi/swagger-ui.html#

# Passos para conectar no banco dados mysql na AWS
 ![workbeanch](https://github.com/thiago-jv/VOTACAO_API/blob/main/banco_de_dados_mysql_aws.png)
  HOSTNAME: votacao-mysql.cs6cmhb8whps.us-east-1.rds.amazonaws.com
  PASSWORD: my_secret_password
  
  
 ![workbeanch](https://github.com/thiago-jv/VOTACAO_API/blob/main/connection_sucess_mysql_aws.png)
 ![workbeanch](https://github.com/thiago-jv/VOTACAO_API/blob/main/tabelas_mysql_aws.png)
 


# Usabilidade dos end-points

1- Realizar cadastro dos associados no end-poit /v1/associados

2- Realizar cadastro da Pauta no end-point /v1/pautas

3- Após ter realizado cadastro de uma Pauta, deve ser habilitada para votação no end-poits /v1/pautas/{idPauta}/habilitaVotacaoSim

4- Após Pauta habilitada, realizar votação no end-poit /v1/votacoes

5- Caso queira parar votação, desabilitar no end-point /v1/pautas/{idPauta}/habilitaVotacaoNao

6- Caso queira fechar a votação utilizar end-point /v1/pautas/{idPauta}/habilitaVotacaoFechado

7- Após votação ser fechada no end-point /v1/pautas/{idPauta}/habilitaVotacaoFechado um producer é criado e enviado para a fila 'VOTACAO_RESULTADO' no rabbitMQ. 

8- Para uma votação ser enviada ao rabbitMQ, habilitar status SIM no end-point /v1/pautas/{idPauta}/habilitaVotacaoSim

9- O resultado da votação pode ser visualizado na fila no rabbitMQ 'VOTACAO_RESULTADO' criado, bem como na tabela Pauta.

10- Caso queira visualizar o resultado da votação no end-point /v1/pautas/{id}


# Passos para rodar aplicação via docker-compose

1- git clone https://github.com/thiago-jv/VOTACAO_API.git

2- dentro do path /votacao -> mvn package -Pdocker 

3- dentro do path /votacao -> docker compose up

Link de Acesso a API - Local
http://localhost:8089/votacaoapi/swagger-ui.html#/

Link de Acesso RabbitMQ - Local
http://localhost:15672/#

Usuario: admin
Senha: admin

Link de Acesso phpmy-admin - Local
http://localhost:8080/index.php?route=/

Usuario: root
Senha: my_secret_password


# Requisitos

1- Instalar ou configura manven 3.8.7 ou superior compativel.

2- Instalar jdk-11.0.18 ou superior compativel.

3- Instalar docker 4.6.1 ou superior compativel.


# Considerações

* Não foi possível subir o serviço do RabbitQM na AWS, porem o projeto local está funcionando.
* O projeto foi desenvolvido levando em consideração os requisitos solicitados abaixo.


                                                         Desafio Técnico
Objetivo

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação.
A partir disso, você precisa criar uma solução back-end para gerenciar essas sessões de votação.
Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API

REST:
* Cadastrar uma nova pauta;
* Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default);
* Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta);
* Contabilizar os votos e dar o resultado da votação na pauta.

Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces
pode ser considerada como autorizada. A escolha da linguagem, frameworks e bibliotecas é livre (desde que
não infrinja direitos de uso).

É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.

Tarefas bônus
As tarefas bônus não são obrigatórias, mas nos permitem avaliar outros conhecimentos que você possa ter.
A gente sempre sugere que o candidato pondere e apresente até onde consegue fazer, considerando o seu
nível de conhecimento e a qualidade da entrega.


* Tarefa Bônus 1 - Mensageria e filas
   Classificação da informação: Uso Interno
   O resultado da votação precisa ser informado para o restante da plataforma, isso deve ser feito preferencialmente através de mensageria. Quando a sessão de votação fechar, poste uma mensagem com o resultado da votação.


* Tarefa Bônus 2 - Performance
   Imagine que sua aplicação possa ser usada em cenários que existam centenas de milhares de votos. Ela deve se comportar de maneira performática nesses cenários;
   Testes de performance são uma boa maneira de garantir e observar como sua aplicação se comporta.


* Tarefa Bônus 3 - Versionamento da API
   Como você versionaria a API da sua aplicação? Que estratégia usar?



