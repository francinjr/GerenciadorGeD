package com.francinjr.xpenses.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.francinjr.xpenses.domain.model.Ganho;
import com.francinjr.xpenses.domain.repository.GanhoRepository;
import com.francinjr.xpenses.domain.service.GanhoService;

@RestController
@RequestMapping(value = "/ganhos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GanhoController {
	
	@Autowired
	GanhoRepository ganhoRepository;
	
	@Autowired
	GanhoService ganhoService;
	
	
	@GetMapping
	public ResponseEntity<List<Ganho>> listar() {
		List<Ganho> todosGanhos = ganhoService.buscarTodos();
		return new ResponseEntity<List<Ganho>>(todosGanhos, HttpStatus.OK);
	}
	
	@GetMapping("/{ganhoId}")
	public ResponseEntity<Ganho> buscar(@PathVariable Long ganhoId) {
		Ganho ganho = ganhoService.buscarOuFalhar(ganhoId);
		return new ResponseEntity<Ganho>(ganho, HttpStatus.OK);
	}
	
    @PostMapping
    public ResponseEntity<Ganho> adicionar(@RequestBody Ganho ganho) {
        Ganho novoGanho = ganhoService.salvar(ganho);
        return new ResponseEntity<Ganho>(novoGanho, HttpStatus.CREATED);
    }
	
	@PutMapping("/{ganhoId}")
	public ResponseEntity<Ganho> atualizar(@PathVariable Long ganhoId,
			@RequestBody Ganho ganho) {
		Optional<Ganho> ganhoAtual = ganhoRepository.findById(ganhoId);
		if(ganhoAtual.isPresent()) {
			BeanUtils.copyProperties(ganho, ganhoAtual.get(), "id");
			Ganho ganhoSalvo = ganhoService.salvar(ganhoAtual.get());
			return ResponseEntity.ok(ganhoSalvo);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{ganhoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long ganhoId) {
		ganhoService.excluir(ganhoId);
	}
}
