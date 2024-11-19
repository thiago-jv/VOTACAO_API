package com.votacao.api.v1.icontroller;

import com.votacao.api.v1.dto.associado.AssociadoFilterDTO;
import com.votacao.api.v1.dto.associado.AssociadoPostDTO;
import com.votacao.api.v1.dto.associado.AssociadoPutDTO;
import com.votacao.api.v1.dto.associado.AssociadoResponseDTO;
import com.votacao.domain.model.Associado;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Associado")
public interface AssociadoControllerOpenApi {

    @ApiOperation("Cria um novo associado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Associado cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Dados inválidos")
    })
    @PostMapping
    AssociadoResponseDTO criarAssociado(
            @ApiParam(name = "corpo", value = "Representação de um novo associado", required = true)
            @Valid @RequestBody AssociadoPostDTO associadoPostDTO);

    @ApiOperation("Busca todos associados paginados com filtros")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca realizada com sucesso")
    })
    @GetMapping
    Page<Associado> listarAssociados(
            @ApiParam(name = "filtro", value = "Filtros para a busca dos associados")
            @Valid AssociadoFilterDTO associadoFilterDTO,
            Pageable pageable);

    @ApiOperation("Busca um associado pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Associado encontrado"),
            @ApiResponse(code = 404, message = "Associado não encontrado")
    })
    @GetMapping("/{id}")
    AssociadoResponseDTO obterAssociadoPorId(
            @ApiParam(value = "ID do associado", required = true)
            @PathVariable Long id);

    @ApiOperation("Atualiza os dados de um associado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Associado atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Dados inválidos"),
            @ApiResponse(code = 404, message = "Associado não encontrado")
    })
    @PutMapping("/{id}")
    AssociadoResponseDTO atualizarAssociado(
            @ApiParam(value = "ID do associado", required = true) @PathVariable Long id,
            @Valid @RequestBody AssociadoPutDTO associadoPutDTO);

    @ApiOperation("Lista todos os associados")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de associados retornada com sucesso")
    })
    @GetMapping("/todos")
    List<Associado> listarTodosAssociados();

    @ApiOperation("Deleta um asociado por id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associado excluído com sucesso"),
            @ApiResponse(code = 404, message = "Associado não encontrado"),
            @ApiResponse(code = 400, message = "Entidade não pode ser deletada, pois está em uso"),
            @ApiResponse(code = 409, message = "Conflito: Entidade em uso")
    })
    void remover(@ApiParam(value = "ID de associado", example = "1") Long id);

}
