package com.francinjr.xpenses.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.francinjr.xpenses.domain.exception.GanhoNaoEncontradoException;
import com.francinjr.xpenses.domain.model.Ganho;
import com.francinjr.xpenses.domain.repository.GanhoRepository;

@Service
public class GanhoService {

	@Autowired
	private GanhoRepository ganhoRepository;

	public List<Ganho> buscarTodos() {
		return ganhoRepository.findAll();
	}
	
	public Ganho buscarOuFalhar(Long ganhoId) {
		return ganhoRepository.findById(ganhoId)
				.orElseThrow(() -> new GanhoNaoEncontradoException(ganhoId));
	}
	
	@Transactional
	public Ganho salvar(Ganho ganho) {
		return ganhoRepository.save(ganho);
	}

	@Transactional
	public void excluir(Long ganhoId) {
		try {
			ganhoRepository.deleteById(ganhoId);
			//ganhoRepository.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
