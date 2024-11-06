package com.bosch.stocktoship.entity;

/**
 * ParameterData class represents the parameters related to a specific part in
 * the quality control process. This class includes attributes for various
 * mechanical and geometric properties, as well as getters and setters for each
 * parameter.
 * 
 * @author BPOO1KOR
 */
public class ParameterData {
	private String partCode;
	private double sheetMetalThickness;
	private String lubricationCondition;
	private double sheetMetalStrength;
	private String toolGeometryDie;
	private String toolGeometryPunch;
	private double punchPressure;
	private double punchSpeed;

	/**
	 * Constructor to initialize ParameterData with a specific part code.
	 * 
	 * @param partCode Unique identifier for the part
	 */

	public ParameterData(String partCode) {
		this.partCode = partCode;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public double getSheetMetalThickness() {
		return sheetMetalThickness;
	}

	public void setSheetMetalThickness(double sheetMetalThickness) {
		this.sheetMetalThickness = sheetMetalThickness;
	}

	public String getLubricationCondition() {
		return lubricationCondition;
	}

	public void setLubricationCondition(String lubricationCondition) {
		this.lubricationCondition = lubricationCondition;
	}

	public double getSheetMetalStrength() {
		return sheetMetalStrength;
	}

	public void setSheetMetalStrength(double sheetMetalStrength) {
		this.sheetMetalStrength = sheetMetalStrength;
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

	public double getPunchPressure() {
		return punchPressure;
	}

	public void setPunchPressure(double punchPressure) {
		this.punchPressure = punchPressure;
	}

	public double getPunchSpeed() {
		return punchSpeed;
	}

	public void setPunchSpeed(double punchSpeed) {
		this.punchSpeed = punchSpeed;
	}
}