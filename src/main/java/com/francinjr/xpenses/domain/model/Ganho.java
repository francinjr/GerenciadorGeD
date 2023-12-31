package com.francinjr.xpenses.domain.model;

import java.time.LocalDateTime;

import com.francinjr.xpenses.dto.GanhoDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ganhos")
public class Ganho {

	private String nome;

	private Double valor;

	private String descricao;

	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Ganho(String nome, Double valor, String descricao, Long id) {
		setNome(nome);
		setValor(valor);
		setDescricao(descricao);
		setId(id);
	}

	public Ganho() {
	}
	
	public Ganho(GanhoDTO data) {
		setNome(data.nome());
		setValor(data.valor());
		setDescricao(data.descricao());
		setId(data.id());
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}
}
