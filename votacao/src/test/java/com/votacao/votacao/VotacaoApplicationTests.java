package com.votacao.votacao;

import com.votacao.api.v1.dto.associado.AssociadoFilterDTO;
import com.votacao.api.v1.dto.associado.AssociadoIdDTO;
import com.votacao.api.v1.dto.associado.AssociadoPostDTO;
import com.votacao.api.v1.dto.enuns.SimNao;
import com.votacao.api.v1.dto.pauta.PautaIdDTO;
import com.votacao.api.v1.dto.pauta.PautaPostDTO;
import com.votacao.api.v1.dto.pauta.PautaPutDTO;
import com.votacao.api.v1.dto.votacao.VotacaoPostDTO;
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
import java.util.List;

import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
class VotacaoApplicationTests {

    private final String ASSOCIADO_PATH = "/votacaoapi/v1/associados";
    private final String PAUTA_PATH = "/votacaoapi/v1/pautas";

    @LocalServerPort
    private int PORT = 8089;

    @Test
    @Order(1)
    void deveSalvarAssociado() throws IOException {
        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("deve_salvar_associado_post.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo("João de Melo"));

        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("deve_salvar_novo_associado_post.json"))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo("Thiago melo"));
    }



    @Test
    @Order(2)
    void deveListarAssociados() {
        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content.size()", greaterThan(0));
    }

    @Test
    @Order(3)
    void deveObterAssociadoPorId() {
        Long id = 1L;

        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(id.intValue()))
                .body("nome", equalTo("João de Melo"));
    }

    @Test
    @Order(4)
    void deveAtualizarAssociado() throws IOException {
        Long id = 1L;

        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(FileUtils.readFileContent("deve_atualizar_associado_put.json"))
                .when()
                .put("/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(id.intValue()))
                .body("nome", equalTo("João da Silva"));
    }

    @Test
    @Order(5)
    void deveDeletarAssociado() {
        Long id = 1L;

        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .accept(ContentType.JSON)
                .when()
                .delete("/{id}", id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @Order(6)
    void deveRetornarErroAoBuscarAssociadoInexistente() {
        Long id = 999L;

        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}", id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("mensagemUsuario", equalTo("Não existe cadastro de associado com o código 999"));
    }

    @Test
    @Order(7)
    void deveFiltrarAssociadosPorNome() {
        AssociadoFilterDTO filtro = new AssociadoFilterDTO();
        filtro.setNome("João");

        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(filtro)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get()  // Alterado para POST
                .then()
                .extract()
                .asString();

        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(filtro)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", containsString("João"));
    }

    @Test
    @Order(8)
    void deveRemoverAssociado() {
        AssociadoPostDTO associado = new AssociadoPostDTO();
        associado.setNome("João");

        Long idAssociado = RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(associado)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().getLong("id");


        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .when()
                .delete("/" + idAssociado)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        RestAssured.given()
                .basePath(ASSOCIADO_PATH)
                .port(PORT)
                .when()
                .get("/" + idAssociado)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Order(7)
    void deveCriarPauta() {
        PautaPostDTO pautaPostDTO = new PautaPostDTO();
        pautaPostDTO.setDescricao("Votação para admissão de novo desenvolvedor Java");
        pautaPostDTO.setAssociadosId(List.of(new AssociadoIdDTO(2L)));

        RestAssured.given()
                .basePath(PAUTA_PATH)
                .port(PORT)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(pautaPostDTO)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("descricao", equalTo(pautaPostDTO.getDescricao()))
                .body("totalSim", equalTo(0));

    }

    @Test
    @Order(8)
    void deveBuscarPautaPorId() {
        Long id = 1L;

        RestAssured.given()
                .basePath(PAUTA_PATH)
                .port(PORT)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(id.intValue()));
    }

    @Test
    @Order(9)
    void deveAtualizarPauta() {
        Long idPauta = 1L;
        Long idAssociado = 2L;

        PautaPutDTO pautaPutDTO = new PautaPutDTO();
        pautaPutDTO.setId(idPauta);
        pautaPutDTO.setDescricao("Votação para admissão de novo desenvolvedor Python");

        pautaPutDTO.setAssociados(List.of(new AssociadoIdDTO(idAssociado)));

        RestAssured.given()
                .basePath(PAUTA_PATH)
                .port(PORT)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(pautaPutDTO)
                .when()
                .put()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("descricao", equalTo("Votação para admissão de novo desenvolvedor Python"));
    }

    @Test
    @Order(10)
    void deveListarPautas() {
        RestAssured.given()
                .basePath(PAUTA_PATH)
                .port(PORT)
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content.size()", greaterThan(0));
    }

    @Test
    @Order(11)
    void deveRetornarErroAoBuscarPautaInexistente() {
        Long id = 999L;

        RestAssured.given()
                .basePath(PAUTA_PATH)
                .port(PORT)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}", id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("mensagemUsuario", equalTo("Não existe cadastro de pauta com o código 999"));
    }

    @Test
    @Order(12)
    void deveHabilitarVotacao() {
        Long idPauta = 1L;

        RestAssured.given()
                .basePath(PAUTA_PATH + "/{idPauta}/habilitaVotacaoSim")
                .port(PORT)
                .pathParam("idPauta", idPauta)
                .when()
                .put()
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    @Test
    @Order(13)
    void deveCriarVotacao() {
        Long idAssociado = 2L;
        Long idPauta = 1L;
        SimNao voto = SimNao.SIM;

        VotacaoPostDTO votacaoPostDTO = new VotacaoPostDTO();
        votacaoPostDTO.setVoto(voto);
        votacaoPostDTO.setAssociado(new AssociadoIdDTO(idAssociado));
        votacaoPostDTO.setPauta(new PautaIdDTO(idPauta));

        RestAssured.given()
                .basePath("/votacaoapi/v1/votacoes")
                .port(PORT)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(votacaoPostDTO)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("voto[0]", equalTo(voto.toString()))
                .body("associado.id[0]", equalTo(idAssociado.intValue()))
                .body("pauta.id[0]", equalTo(idPauta.intValue()));
    }


}
