package com.bosch.stocktoship.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stage_details")
public class StageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stageId;

    @ManyToOne
    @JoinColumn(name = "part_id", nullable = false)
    @JsonBackReference
    private PartDetails partDetails;
    
    private String stageName;
    @Column(nullable = true)
    private Double sheetMetalThickness;
    private String lubricationCondition;
    private String sheetMetalStrength;
    private String toolGeometryDie;
    private String toolGeometryPunch;
    @Column(nullable = true)
    private Double punchPressure;
    @Column(nullable = true)
    private Double punchSpeed;
    @Column(nullable = true)
    private Double bendRadius;
    @Column(nullable = true)
    private Double formingForce;
    private String paintType;
    @Column(nullable = true)
    private Double coatingThickness;
    private String alignment;
    private String functionalCheck;
    private String ductilityOfWeldedJoints;
    @Column(nullable = true)
    private Double weldingSpeed;
    private String weldingMethod;

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Double getSheetMetalThickness() {
        return sheetMetalThickness;
    }

    public void setSheetMetalThickness(Double sheetMetalThickness) {
        this.sheetMetalThickness = sheetMetalThickness;
    }

    public String getLubricationCondition() {
        return lubricationCondition;
    }

    public void setLubricationCondition(String lubricationCondition) {
        this.lubricationCondition = lubricationCondition;
    }

    public String getSheetMetalStrength() {
        return sheetMetalStrength;
    }

    public void setSheetMetalStrength(String sheetMetalStrngth) {
        this.sheetMetalStrength = sheetMetalStrngth;
    }

    public String getToolGeometryDie() {
        return toolGeometryDie;
    }

    public void setToolGeometryDie(String toolGeometryDie) {
        this.toolGeometryDie = toolGeometryDie;
    }

    public String getToolGeometryPunch() {
        return toolGeometryPunch;
    }

    public void setToolGeometryPunch(String toolGeometryPunch) {
        this.toolGeometryPunch = toolGeometryPunch;
    }

    public Double getPunchPressure() {
        return punchPressure;
    }

    public void setPunchPressure(Double punchPressure) {
        this.punchPressure = punchPressure;
    }

    public Double getPunchSpeed() {
        return punchSpeed;
    }

    public void setPunchSpeed(Double punchSpeed) {
        this.punchSpeed = punchSpeed;
    }

    public Double getBendRadius() {
        return bendRadius;
    }

    public void setBendRadius(Double bendRadius) {
        this.bendRadius = bendRadius;
    }

    public Double getFormingForce() {
        return formingForce;
    }

    public void setFormingForce(Double formingForce) {
        this.formingForce = formingForce;
    }

    public String getPaintType() {
        return paintType;
    }

    public void setPaintType(String paintType) {
        this.paintType = paintType;
    }

    public Double getCoatingThickness() {
        return coatingThickness;
    }

    public void setCoatingThickness(Double coatingThickness) {
        this.coatingThickness = coatingThickness;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getFunctionalCheck() {
        return functionalCheck;
    }

    public void setFunctionalCheck(String functionalCheck) {
        this.functionalCheck = functionalCheck;
    }

    public String getDuctilityOfWeldedJoints() {
        return ductilityOfWeldedJoints;
    }

    public void setDuctilityOfWeldedJoints(String ductilityOfWeldedJoints) {
        this.ductilityOfWeldedJoints = ductilityOfWeldedJoints;
    }

    public PartDetails getPartDetails() {
		return partDetails;
	}

	public void setPartDetails(PartDetails partDetails) {
		this.partDetails = partDetails;
	}

	public Double getWeldingSpeed() {
        return weldingSpeed;
    }

    public void setWeldingSpeed(Double weldingSpeed) {
        this.weldingSpeed = weldingSpeed;
    }

    public String getWeldingMethod() {
        return weldingMethod;
    }

    public void setWeldingMethod(String weldingMethod) {
        this.weldingMethod = weldingMethod;
    }
}

