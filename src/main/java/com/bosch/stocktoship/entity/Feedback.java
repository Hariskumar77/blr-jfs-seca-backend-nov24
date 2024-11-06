package com.bosch.stocktoship.entity;
/**
 * @author BAP3COB
 * Class representing feedback information for a particular part or product stage.
 * It includes details like the stage, part number, part description, type of feedback,
 * defect type, and any additional remarks.
 */
public class Feedback {
    private String stage;
    private String partNumber;
    private String partDescription;
    private String feedbackType;
    private DefectType defectType;
    private String remarks;

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public DefectType getDefectType() {
        return defectType;
    }

    public void setDefectType(DefectType defectType) {
        this.defectType = defectType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stage: ").append(stage).append("\n");
        sb.append("Part Number: ").append(partNumber).append("\n");
        sb.append("Part Description: ").append(partDescription).append("\n");
        sb.append("Feedback: ").append(feedbackType).append("\n");
        if (defectType != null) {
            sb.append("Defect Type: ").append(defectType.getLabel()).append("\n");
        }
        sb.append("Remarks: ").append(remarks).append("\n");
        return sb.toString();
    }
}