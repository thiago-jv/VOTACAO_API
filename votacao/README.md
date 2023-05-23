# Projeto Votação em Sembleia 

Projeto para controle de votações em assembleia.


Link de Acesso a API
http://54.90.117.143:8089/votacaoapi/swagger-ui.html#

* Usabilidade dos end-points

1- Realizar cadastro dos associados no end-poit /v1/associados

2- Realizar cadastro da Pauta no end-point /v1/pautas

3- Após ter realizado cadastro de uma Pauta, deve ser habilitada para votação no end-poits /v1/pautas/{idPauta}/habilitaVotacaoSim

4- Após Pauta habilitada, realizar votação no end-poit /v1/votacoes

5- Caso queira parar votação, desabilitar no end-point /v1/pautas/{idPauta}/habilitaVotacaoNao

6- Caso queira fechar a votação utilizar end-point /v1/pautas/{idPauta}/habilitaVotacaoFechado

7- Após votação ser fechada no end-point /v1/pautas/{idPauta}/habilitaVotacaoFechado um producer é criado e enviado para a fila 'VOTACAO_RESULTADO' no rabbitMQ. 

8- Para uma votação ser enviada ao rabbitMQ, habilitar status SIM no end-point /v1/pautas/{idPauta}/habilitaVotacaoSim

# Considerações

* Não foi possível subir o serviço do RabbitQM na AWS, porem o projeto o dorando local está funcionando.

# Passos

* git clone https://github.com/thiago-jv/VOTACAO_API.git


