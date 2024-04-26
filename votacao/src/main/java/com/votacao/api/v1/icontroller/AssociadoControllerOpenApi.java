package com.votacao.api.v1.icontroller;

import com.votacao.api.v1.dto.associado.AssociadoPostDTO;
import com.votacao.api.v1.dto.associado.AssociadoResponseDTO;
import com.votacao.domain.model.Associado;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Associado")
public interface AssociadoControllerOpenApi {

    @ApiOperation("Cria um novo associado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Associado cadastrado"),
    })
    AssociadoResponseDTO criar(
            @ApiParam(name = "corpo", value = "Representação de um associado") AssociadoPostDTO associadoPostDTO);

    @ApiOperation("Busca todos associados sem paginação")
    List<Associado> listar();

}
