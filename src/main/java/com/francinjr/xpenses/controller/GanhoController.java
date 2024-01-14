package com.francinjr.xpenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.francinjr.xpenses.domain.model.Ganho;
import com.francinjr.xpenses.domain.service.GanhoService;
import com.francinjr.xpenses.dto.GanhoDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/ganhos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GanhoController {
	
	@Autowired
	GanhoService ganhoService;
	
	
	@GetMapping
	public ResponseEntity<List<GanhoDTO>> listar() {
		List<GanhoDTO> todosGanhos = ganhoService.buscarTodos();
		return new ResponseEntity<List<GanhoDTO>>(todosGanhos, HttpStatus.OK);
	}
	
	
	@GetMapping("/{ganhoId}")
	public ResponseEntity<GanhoDTO> buscar(@PathVariable Long ganhoId) {
		GanhoDTO ganhoDTO = ganhoService.buscarOuFalhar(ganhoId);
		return new ResponseEntity<GanhoDTO>(ganhoDTO, HttpStatus.OK);
	}
	
	
    @PostMapping
    public ResponseEntity<GanhoDTO> adicionar(@Valid @RequestBody GanhoDTO ganhoDTO)
    		throws Exception {
        GanhoDTO ganhoSalvo = ganhoService.criar(ganhoDTO);
        return new ResponseEntity<GanhoDTO>(ganhoSalvo, HttpStatus.CREATED);
    }
	
    
	@PutMapping("/{ganhoId}")
	public ResponseEntity<GanhoDTO> atualizar(@PathVariable Long ganhoId,
			@Valid @RequestBody GanhoDTO ganhoDTO) throws Exception {
		
		Ganho ganho = new Ganho(ganhoDTO);
		ganho.setId(ganhoId);
		
		GanhoDTO ganhoDTO2 = new GanhoDTO(ganho);
		
		GanhoDTO ganhoSalvo = ganhoService.atualizar(ganhoDTO2);
		return new ResponseEntity<GanhoDTO>(ganhoSalvo, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{ganhoId}")
	public ResponseEntity<Void> deletar(@PathVariable Long ganhoId) throws Exception {
		ganhoService.excluir(ganhoId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
