package com.francinjr.xpenses.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.francinjr.xpenses.domain.exception.GanhoNaoEncontradoException;
import com.francinjr.xpenses.domain.model.Ganho;
import com.francinjr.xpenses.domain.repository.GanhoRepository;
import com.francinjr.xpenses.dto.GanhoDTO;

@Service
public class GanhoService {

	@Autowired
	private GanhoRepository ganhoRepository;

	public List<GanhoDTO> buscarTodos() {
		List<Ganho> ganhos = ganhoRepository.findAll();

		return ganhos.stream().map(ganho -> new GanhoDTO(ganho.getNome(), ganho.getValor(), 
				ganho.getDescricao(), ganho.getId())).toList();
		
	}
	
	public GanhoDTO buscarOuFalhar(Long ganhoId) {
		Ganho ganho = ganhoRepository.findById(ganhoId)
				.orElseThrow(() -> new GanhoNaoEncontradoException(ganhoId));
		
		GanhoDTO ganhoDTO = new GanhoDTO(ganho.getNome(), ganho.getValor(), 
				ganho.getDescricao(), ganho.getId());
		
		return ganhoDTO;
	}
	
	@Transactional
	public GanhoDTO criar(GanhoDTO data) throws Exception {
		Ganho ganho = new Ganho(data);
		
		Ganho ganhoCriado = ganhoRepository.save(ganho);
		
		GanhoDTO ganhoSalvo = new GanhoDTO(ganhoCriado.getNome(), ganhoCriado.getValor(), 
				ganhoCriado.getDescricao(), ganhoCriado.getId());
		
		return ganhoSalvo;
	}
	
	@Transactional
	public GanhoDTO atualizar(GanhoDTO data) throws Exception {
		//Ganho ganho = new Ganho(data);
		GanhoDTO ganhoBuscado = buscarOuFalhar(data.id());
		
		if(ganhoBuscado != null) {
			Ganho ganhoEncontrado = new Ganho(data);
			ganhoEncontrado = ganhoRepository.save(ganhoEncontrado);
			
			GanhoDTO ganhoSalvo = new GanhoDTO(ganhoEncontrado.getNome(), 
					ganhoEncontrado.getValor(), ganhoEncontrado.getDescricao(), 
					ganhoEncontrado.getId());
			
			return ganhoSalvo;
		} else {
			throw new GanhoNaoEncontradoException(data.id());
		}
	}

	@Transactional
	public void excluir(Long ganhoId) {
		GanhoDTO ganhoBuscado = buscarOuFalhar(ganhoId);
		
		if(ganhoBuscado != null) {
			ganhoRepository.deleteById(ganhoId);
		} else {
			throw new GanhoNaoEncontradoException(ganhoId);
		}
	}
}
