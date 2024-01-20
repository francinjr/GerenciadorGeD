package com.francinjr.xpenses.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

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
	private LocalDateTime paiday;
	

	public FinanceDTO(String name, Double value, String description, FinanceType type, LocalDateTime paiday, Long key) {
		setName(name);
		setValue(value);
		setDescription (description);
		setType(type);
		setPaiday(paiday);
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
	public LocalDateTime getPaiday() {
		return paiday;
	}
	public void setPaiday(LocalDateTime paiday) {
		this.paiday = paiday;
	}
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((paiday == null) ? 0 : paiday.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (type != other.type)
			return false;
		if (paiday == null) {
			if (other.paiday != null)
				return false;
		} else if (!paiday.equals(other.paiday))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	
}
