package com.francinjr.xpenses.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "finances")
public class Finance implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Double value;
	private String description;
	private FinanceType type;
	private LocalDate startDate;
	private LocalDate endDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	public Finance(String name, Double value, String description, FinanceType type, 
			LocalDate startDate, LocalDate endDate,Long id) {
		setName(name);
		setValue(value);
		setDescription(description);
		setType(type);
		setStartDate(startDate);
		setEndDate(endDate);
		setId(id);
	}
	
	public Finance() {}
	
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

	

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		Finance other = (Finance) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && type == other.type && Objects.equals(value, other.value);
	}

	
}
