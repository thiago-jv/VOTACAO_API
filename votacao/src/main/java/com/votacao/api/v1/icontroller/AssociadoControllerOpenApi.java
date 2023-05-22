package com.votacao.api.v1.icontroller;

import com.votacao.api.v1.dto.associado.AssociadoPostDTO;
import com.votacao.api.v1.dto.associado.AssociadoResponseDTO;
import io.swagger.annotations.*;

@Api(tags = "Associado")
public interface AssociadoControllerOpenApi {

    @ApiOperation("Cria um novo associado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Associado cadastrado"),
    })
    AssociadoResponseDTO criar(
            @ApiParam(name = "corpo", value = "Representação de um associado") AssociadoPostDTO associadoPostDTO);

}
