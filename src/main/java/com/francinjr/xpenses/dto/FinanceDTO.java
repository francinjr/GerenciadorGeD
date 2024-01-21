package com.francinjr.xpenses.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.francinjr.xpenses.domain.model.FinanceType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonPropertyOrder({"id", "name", "value", "description", "type", "paiday" })
public class FinanceDTO extends RepresentationModel<FinanceDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long key;

	@Size(min = 5, max = 30, message = "O nome deve ter entre 5 e 30 caracteres")
	private String name;

	@NotNull(message = "O valor não pode ser nulo")
	private Double value;
	
	private String description;
	private FinanceType type;
	//private LocalDateTime paiday;
	

	public FinanceDTO(String name, Double value, String description, FinanceType type, LocalDateTime paiday, Long key) {
		setName(name);
		setValue(value);
		setDescription (description);
		setType(type);
		//setPaiday(paiday);
		setKey(key);
	}
	
	public FinanceDTO() {}

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
	/*public LocalDateTime getPaiday() {
		return paiday;
	}
	public void setPaiday(LocalDateTime paiday) {
		this.paiday = paiday;
	}*/
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, key, name, type, value);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinanceDTO other = (FinanceDTO) obj;
		return Objects.equals(description, other.description) && Objects.equals(key, other.key)
				&& Objects.equals(name, other.name) && type == other.type && Objects.equals(value, other.value);
	}


	

	
}
