package com.bosch.stocktoship.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "part_details1")
public class PartDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int partId;

    @Column(unique = true, nullable = false)
    private int partCode;
    private String partDescription;

    @OneToMany(mappedBy = "partDetails", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StageDetails> stageDetails;

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public int getPartCode() {
        return partCode;
    }

    public void setPartCode(int partCode) {
        this.partCode = partCode;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public List<StageDetails> getStageDetails() {
        return stageDetails;
    }

    public void setStageDetails(List<StageDetails> stageDetails) {
        this.stageDetails = stageDetails;
    }
}

