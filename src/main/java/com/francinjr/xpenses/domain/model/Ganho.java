package com.francinjr.xpenses.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ganhos")
public class Ganho {
	
	// ,length = 255
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Double valor;
	
	private String descricao;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	public Ganho(String nome, Double valor, String descricao, Long id) {
		setNome(nome);
		setValor(valor);
		setDescricao(descricao);
		setId(id);
	}
	
	public Ganho() {}
	
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
}
