package com.francinjr.xpenses.domain.model;

import java.time.LocalDateTime;

import com.francinjr.xpenses.dto.FinanceDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "finances")
public class Finance {
	private String name;
	private Double value;
	private String description;
	private FinanceType type;
	private LocalDateTime paiday;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	public Finance(String name, Double value, String description, FinanceType type, LocalDateTime paiday,
			Long id) {
		setName(name);
		setValue(value);
		setDescription(description);
		setType(type);
		setPaiday(paiday);
		setId(id);
	}
	
	public Finance() {}
	
	public Finance(FinanceDTO dto) {
		setName(dto.name());
		setValue(dto.value());
		setDescription(dto.description());
		setId(dto.id());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FinanceType getType() {
		return type;
	}

	public void setType(FinanceType type) {
		this.type = type;
	}

	public LocalDateTime getPaiday() {
		return paiday;
	}

	public void setPaiday(LocalDateTime paiday) {
		this.paiday = paiday;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
