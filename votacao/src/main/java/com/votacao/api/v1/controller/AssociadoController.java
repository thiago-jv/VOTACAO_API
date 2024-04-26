package com.votacao.api.v1.controller;

import com.votacao.api.v1.dto.associado.AssociadoPostDTO;
import com.votacao.api.v1.dto.associado.AssociadoResponseDTO;
import com.votacao.api.v1.icontroller.AssociadoControllerOpenApi;
import com.votacao.api.v1.mapper.AssociadoMapper;
import com.votacao.domain.model.Associado;
import com.votacao.domain.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/v1/associados", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssociadoController implements Serializable, AssociadoControllerOpenApi {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private AssociadoMapper associadoMapper;

    private static Logger log = Logger.getLogger(AssociadoController.class.getName());

    @PostMapping
    @Override
    public AssociadoResponseDTO criar(@RequestBody AssociadoPostDTO associadoPostDTO) {
        log.info("Iniciando Criação de Associado");
        return associadoMapper.toAssociadoResponseDTO(associadoRepository.save(associadoMapper.toAssociado(associadoPostDTO)));
    }

    @GetMapping
    @Override
    public List<Associado> listar() {
        return associadoRepository.findAll();
    }

}
