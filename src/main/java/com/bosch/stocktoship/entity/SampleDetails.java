package com.bosch.stocktoship.entity;

public class SampleDetails {
	private int sampleId;
	private String stageName;
	private double sheetMetalThickness;
	private String lubricationCondition;
	private String SheetMetalStrength;
	private String toolGeometryDie;
	private String toolGeometryPunch;
	private double punchPressure;
	private double punchSpeed;
	private double bendRadius;
	private double formingForce;
	private String paintType;
	private double coatingThickness;
	private String alignment;
	private String functionalChecks;
	private String ductility;
	private double weldingSpeed;
	private String weldingMethod;
	private PartDetails partDetails;

	public SampleDetails(int sampleId, String stageName, double sheetMetalThickness, String lubricationCondition,
			String sheetMetalStrength, String toolGeometryDie, String toolGeometryPunch, double punchPressure,
			double punchSpeed, double bendRadius, double formingForce, String paintType, double coatingThickness,
			String alignment, String functionalChecks, String ductility, double weldingSpeed, String weldingMethod,
			PartDetails partDetails) {
		super();
		this.sampleId = sampleId;
		this.stageName = stageName;
		this.sheetMetalThickness = sheetMetalThickness;
		this.lubricationCondition = lubricationCondition;
		SheetMetalStrength = sheetMetalStrength;
		this.toolGeometryDie = toolGeometryDie;
		this.toolGeometryPunch = toolGeometryPunch;
		this.punchPressure = punchPressure;
		this.punchSpeed = punchSpeed;
		this.bendRadius = bendRadius;
		this.formingForce = formingForce;
		this.paintType = paintType;
		this.coatingThickness = coatingThickness;
		this.alignment = alignment;
		this.functionalChecks = functionalChecks;
		this.ductility = ductility;
		this.weldingSpeed = weldingSpeed;
		this.weldingMethod = weldingMethod;
		this.partDetails = partDetails;
	}

	public SampleDetails() {
		// TODO Auto-generated constructor stub
	}

	public int getSampleId() {
		return sampleId;
	}

	public void setSampleId(int sampleId) {
		this.sampleId = sampleId;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
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

	public String getSheetMetalStrength() {
		return SheetMetalStrength;
	}

	public void setSheetMetalStrength(String sheetMetalStrength) {
		SheetMetalStrength = sheetMetalStrength;
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

	public double getBendRadius() {
		return bendRadius;
	}

	public void setBendRadius(double bendRadius) {
		this.bendRadius = bendRadius;
	}

	public double getFormingForce() {
		return formingForce;
	}

	public void setFormingForce(double formingForce) {
		this.formingForce = formingForce;
	}

	public String getPaintType() {
		return paintType;
	}

	public void setPaintType(String paintType) {
		this.paintType = paintType;
	}

	public double getCoatingThickness() {
		return coatingThickness;
	}

	public void setCoatingThickness(double coatingThickness) {
		this.coatingThickness = coatingThickness;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getFunctionalChecks() {
		return functionalChecks;
	}

	public void setFunctionalChecks(String functionalChecks) {
		this.functionalChecks = functionalChecks;
	}

	public String getDuctility() {
		return ductility;
	}

	public void setDuctility(String ductility) {
		this.ductility = ductility;
	}

	public double getWeldingSpeed() {
		return weldingSpeed;
	}

	public void setWeldingSpeed(double weldingSpeed) {
		this.weldingSpeed = weldingSpeed;
	}

	public String getWeldingMethod() {
		return weldingMethod;
	}

	public void setWeldingMethod(String weldingMethod) {
		this.weldingMethod = weldingMethod;
	}

	public PartDetails getPartDetails() {
		return partDetails;
	}

	public void setPartDetails(PartDetails partDetails) {
		this.partDetails = partDetails;
	}

}
