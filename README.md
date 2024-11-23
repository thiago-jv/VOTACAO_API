# Projeto Votação em Sembleia 

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


Tecnologias Utilizadas

# Stack utilizada e mais

1. Spring Boot
2. Spring Data
3. Spring Profiles
4. Partner - DTO Data Transfer Object
5. Partner - Repository
6. Arquitetura de MicroServiços
7. MapStruct
8. Lombok
9. Logger
10. Maven
11. junit
12. RestAssured
13. Swagger
14. Clean Code
15. SOLID
16. Mensageria - RabbitMQ
17. Docker compose
18. PostgreSQL


# Frontend 
```
(Front-end do projeto): VOTACAO_UI - Responsável pela interface de acesso as funcionalidade e integração desenvolvidas no backend.

https://github.com/thiago-jv/VOTACAO-UI
```

# Passos para rodar aplicação via docker-compose

```
1- git clone https://github.com/thiago-jv/VOTACAO_API.git

2- dentro do path /votacao -> docker compose up
```

Link de Acesso a API - Local

```
http://localhost:8089/votacaoapi/swagger-ui.html#/
```

Link de Acesso RabbitMQ - Local
```
http://localhost:15672/#

Usuario: admin
Senha: admin
```

![swagger](https://github.com/thiago-jv/VOTACAO_API/blob/main/swagger.png)

# Requisitos
```
1- Instalar ou configura manven 3.8.7 ou superior compativel.

2- Instalar jdk-11.0.18 ou superior compativel.

3- Instalar docker 4.6.1 ou superior compativel.
```



