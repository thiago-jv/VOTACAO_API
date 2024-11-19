package com.votacao.api.v1.controller;

import com.votacao.api.v1.dto.associado.AssociadoFilterDTO;
import com.votacao.api.v1.dto.associado.AssociadoPostDTO;
import com.votacao.api.v1.dto.associado.AssociadoPutDTO;
import com.votacao.api.v1.dto.associado.AssociadoResponseDTO;
import com.votacao.api.v1.icontroller.AssociadoControllerOpenApi;
import com.votacao.api.v1.mapper.AssociadoMapper;
import com.votacao.domain.model.Associado;
import com.votacao.domain.repository.AssociadoRepository;
import com.votacao.domain.service.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private AssociadoService associadoService;

    private static Logger log = Logger.getLogger(AssociadoController.class.getName());

    @PostMapping
    @Override
    public AssociadoResponseDTO criarAssociado(@RequestBody AssociadoPostDTO associadoPostDTO) {
        log.info("Iniciando Criação de Associado");
        return associadoMapper.toAssociadoResponseDTO(associadoRepository.save(associadoMapper.toAssociado(associadoPostDTO)));
    }

    @GetMapping
    @Override
    public Page<Associado> listarAssociados(AssociadoFilterDTO associadoFilterDTO, Pageable pageable) {
        return associadoRepository.filtrar(associadoFilterDTO, pageable);
    }

    @GetMapping("/{id}")
    @Override
    public AssociadoResponseDTO obterAssociadoPorId(@PathVariable Long id) {
        log.info("Busca Votação por ID " + id);
        return associadoMapper.toAssociadoResponseDTO(associadoService.buscarOuFalhar(id));
    }

    @PutMapping("/{id}")
    @Override
    public AssociadoResponseDTO atualizarAssociado(@PathVariable Long id, @Valid @RequestBody AssociadoPutDTO associadoPutDTO) {
        return associadoMapper.toAssociadoResponseDTO(associadoService.atualizar(id, associadoMapper.toAssociado(associadoPutDTO)));
    }

    @GetMapping("/todos")
    @Override
    public List<Associado> listarTodosAssociados() {
        return associadoRepository.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void remover(@PathVariable Long id) {
        associadoService.excluir(id);
    }

}
