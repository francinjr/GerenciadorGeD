package com.francinjr.xpenses.integrationtests.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.francinjr.xpenses.domain.model.FinanceType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FinanceDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@Size(min = 5, max = 30, message = "O nome deve ter entre 5 e 30 caracteres")
	private String name;

	@NotNull(message = "O valor não pode ser nulo")
	private Double value;

	private String description;
	private FinanceType type;
	// private LocalDateTime paiday;

	public FinanceDTO(String name, Double value, String description, FinanceType type, LocalDateTime paiday, Long id) {
		setName(name);
		setValue(value);
		setDescription(description);
		setType(type);
		// setPaiday(paiday);
		setId(id);
	}

	public FinanceDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	/*
	 * public LocalDateTime getPaiday() { return paiday; } public void
	 * setPaiday(LocalDateTime paiday) { this.paiday = paiday; }
	 */

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, type, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinanceDTO other = (FinanceDTO) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && type == other.type && Objects.equals(value, other.value);
	}
	
	

}
