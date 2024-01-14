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

		return ganhos.stream().map(ganho -> new GanhoDTO(ganho)).toList();
	}
	
	
	public GanhoDTO buscarOuFalhar(Long ganhoId) {
		Ganho ganho = ganhoRepository.findById(ganhoId)
				.orElseThrow(() -> new GanhoNaoEncontradoException(ganhoId));
		
		GanhoDTO ganhoDTO = new GanhoDTO(ganho);
		return ganhoDTO;
	}
	
	
	@Transactional
	public GanhoDTO criar(GanhoDTO data) throws Exception {
		Ganho ganho = new Ganho(data);
		
		try {
			Ganho ganhoCriado = ganhoRepository.save(ganho);
			
			GanhoDTO ganhoCriadoDTO = new GanhoDTO(ganhoCriado);
			return ganhoCriadoDTO;
		} catch(Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}
	
	
	@Transactional
	public GanhoDTO atualizar(GanhoDTO data) throws Exception {
		GanhoDTO ganhoBuscadoDTO = buscarOuFalhar(data.id());
		
		if(ganhoBuscadoDTO != null) {
			Ganho ganho = new Ganho(data);
			
			try {
				ganho = ganhoRepository.save(ganho);
			} catch(Exception exception) {
				throw new Exception(exception.getMessage());
			}
			
			GanhoDTO ganhoSalvoDTO = new GanhoDTO(ganho);
			return ganhoSalvoDTO;
		} else {
			throw new GanhoNaoEncontradoException(data.id());
		}
	}

	
	@Transactional
	public void excluir(Long ganhoId) throws Exception {
		GanhoDTO ganhoBuscadoDTO = buscarOuFalhar(ganhoId);
		
		if(ganhoBuscadoDTO != null) {
			try {
				ganhoRepository.deleteById(ganhoId);
			} catch(Exception exception) {
				new Exception(exception.getMessage());
			}
		} else {
			throw new GanhoNaoEncontradoException(ganhoId);
		}
	}
}
