package com.votacao.votacao;

import io.restassured.RestAssured;
import com.votacao.util.FileUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application.properties")
class VotacaoApplicationTests {

	// private final String ASSOCIADO_PATH = "/votacaoapi/v1/associados";

	// private final String PAUTA_PATH = "/votacaoapi/v1/pautas";

	// private final String VOTACAO_PATH = "/votacaoapi/v1/votacoes";

	// @LocalServerPort
	// private int PORT = 8089;

	// @Test
	// @Order(1)
	// void deveSalvarAssociado() throws IOException {
	// 	RestAssured.given()
	// 			.basePath(ASSOCIADO_PATH)
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.body(FileUtils.readFileContent("deve_salvar_associado_post.json"))
	// 			.when()
	// 			.post()
	// 			.then()
	// 			.statusCode(HttpStatus.OK.value());
	// }

	// @Test
	// @Order(2)
	// void deveSalvarPauta1() throws IOException {
	// 	RestAssured.given()
	// 			.basePath(PAUTA_PATH)
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.body(FileUtils.readFileContent("deve_salvar_pauta_1_post.json"))
	// 			.when()
	// 			.post()
	// 			.then()
	// 			.statusCode(HttpStatus.OK.value());
	// }

	// @Test
	// @Order(3)
	// void deveSalvarPauta2() throws IOException {
	// 	RestAssured.given()
	// 			.basePath(PAUTA_PATH)
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.body(FileUtils.readFileContent("deve_salvar_pauta_2_post.json"))
	// 			.when()
	// 			.post()
	// 			.then()
	// 			.statusCode(HttpStatus.OK.value());
	// }

	// @Test
	// @Order(4)
	// void deveHabilitarPautaSim() throws IOException {
	// 	RestAssured.given()
	// 			.pathParam("idPauta", 1)
	// 			.basePath(PAUTA_PATH + "/{idPauta}/habilitaVotacaoSim")
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.when()
	// 			.put()
	// 			.then()
	// 			.statusCode(HttpStatus.OK.value());
	// }

	// @Test
	// @Order(5)
	// void deveHabilitarPautaNao() throws IOException {
	// 	RestAssured.given()
	// 			.pathParam("idPauta", 2)
	// 			.basePath(PAUTA_PATH + "/{idPauta}/habilitaVotacaoNao")
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.when()
	// 			.put()
	// 			.then()
	// 			.statusCode(HttpStatus.OK.value());
	// }

	// @Test
	// @Order(6)
	// void deveSalvarUmaVotacao() throws IOException {
	// 	RestAssured.given()
	// 			.basePath(VOTACAO_PATH)
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.body(FileUtils.readFileContent("deve_salvar_votacao_post.json"))
	// 			.when()
	// 			.post()
	// 			.then()
	// 			.statusCode(HttpStatus.OK.value());
	// }

	// @Test
	// @Order(7)
	// void deveHabilitarPautaFechadoParaProduzirUmaFilaNoRabitMQ() throws IOException {
	// 	RestAssured.given()
	// 			.pathParam("idPauta", 1)
	// 			.basePath(PAUTA_PATH + "/{idPauta}/habilitaVotacaoFechado")
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.when()
	// 			.put()
	// 			.then()
	// 			.statusCode(HttpStatus.OK.value());
	// }

	// @Test
	// @Order(8)
	// void deveLancarAssociadoJaRealizouVotacao() throws IOException {
	// 	RestAssured.given()
	// 			.basePath(VOTACAO_PATH)
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.body(FileUtils.readFileContent("deve_salvar_votacao_post.json"))
	// 			.when()
	// 			.post()
	// 			.then()
	// 			.statusCode(HttpStatus.NOT_FOUND.value());
	// }

	// @Test
	// @Order(9)
	// void deveLancarAssociadoNaoEncontradoParaUmaVotacao() throws IOException {
	// 	RestAssured.given()
	// 			.basePath(VOTACAO_PATH)
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.body(FileUtils.readFileContent("deve_lancar_associado_nao_encontrado_para_uma_votacao_post.json"))
	// 			.when()
	// 			.post()
	// 			.then()
	// 			.statusCode(HttpStatus.NOT_FOUND.value());
	// }

	// @Test
	// @Order(10)
	// void deveLancarPautaNaoEncontradoParaUmaVotacao() throws IOException {
	// 	RestAssured.given()
	// 			.basePath(VOTACAO_PATH)
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.body(FileUtils.readFileContent("deve_lancar_pauta_nao_econtrada_para_uma_votacao_post.json"))
	// 			.when()
	// 			.post()
	// 			.then()
	// 			.statusCode(HttpStatus.NOT_FOUND.value());
	// }

	// @Test
	// @Order(11)
	// void deveLancarPautaNaoHabilitadaParaUmaVotacao() throws IOException {
	// 	RestAssured.given()
	// 			.basePath(VOTACAO_PATH)
	// 			.port(PORT)
	// 			.contentType(ContentType.JSON)
	// 			.accept(ContentType.JSON)
	// 			.body(FileUtils.readFileContent("deve_lancar_pauta_nao_econtrada_para_uma_votacao_post.json"))
	// 			.when()
	// 			.post()
	// 			.then()
	// 			.statusCode(HttpStatus.NOT_FOUND.value());
	// }

}
