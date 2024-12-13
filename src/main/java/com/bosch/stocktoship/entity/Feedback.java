package com.bosch.stocktoship.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedback")
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = true)
	private boolean QAFailed;
	@Column(nullable = true)
	private boolean metRequirements;
	@Column(nullable = true)
	private String defectType;
	private String remarks;

	private int partCode;

	public int getPartCode() {
		return partCode;
	}

	public void setPartCode(int partCode) {
		this.partCode = partCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isQAFailed() {
		return QAFailed;
	}

	public void setQAFailed(boolean qAFailed) {
		QAFailed = qAFailed;
	}

	public boolean isMetRequirements() {
		return metRequirements;
	}

	public void setMetRequirements(boolean metRequirements) {
		this.metRequirements = metRequirements;
	}

	public String getDefectType() {
		return defectType;
	}

	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
