package com.francinjr.xpenses.dto.finance;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "description"})
public class UpdateFinanceDescriptionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long key;
	private String description;
	
	
	public UpdateFinanceDescriptionDTO() {}
	
	public UpdateFinanceDescriptionDTO(Long key, String description) {
		this.key = key;
		this.description = description;
		
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
		result = prime * result + Objects.hash(description, key);
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
		UpdateFinanceDescriptionDTO other = (UpdateFinanceDescriptionDTO) obj;
		return Objects.equals(description, other.description) && Objects.equals(key, other.key);
	}

	

	


	

	
}
