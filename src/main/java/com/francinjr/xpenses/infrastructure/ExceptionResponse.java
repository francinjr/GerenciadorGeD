package com.francinjr.xpenses.infrastructure;

import java.io.Serializable;
import java.util.List;

public class ExceptionResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	private List<ValidationField> fields;
	
	public ExceptionResponse(Integer status, String type, String title, String detail, 
			List<ValidationField> fields) {
		setStatus(status);
		setType(type);
		setTitle(title);
		setDetail(detail);
		setFields(fields);
	}
	
	public ExceptionResponse() {}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public List<ValidationField> getFields() {
		return fields;
	}

	public void setFields(List<ValidationField> fields) {
		this.fields = fields;
	}
}
