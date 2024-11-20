package com.example.test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "drug_info")
public class Pill {

    @Id
    @Column(name = "drug_num")
    private Integer drugNum;

    @Column(name = "drug_name")
    private String drugName;

    @Column(name = "formulation")
    private String formulation;

    @Column(name = "color")
    private String color;

    @Column(name = "Separating_Line")
    private String separatingLine;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    @Column(name = "efficacy")
    private String efficacy;

    @Column(name = "usage_method")
    private String usageMethod;

    @Column(name = "warning")
    private String warning;

    @Column(name = "precautions")
    private String precautions;

    @Column(name = "interactions")
    private String interactions;

    @Column(name = "side_effects")
    private String sideEffects;

    @Column(name = "storage_method")
    private String storageMethod;

    // Getters and setters
    public Integer getDrugNum() {
        return drugNum;
    }

    public void setDrugNum(Integer drugNum) {
        this.drugNum = drugNum;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSeparatingLine() {
        return separatingLine;
    }

    public void setSeparatingLine(String separatingLine) {
        this.separatingLine = separatingLine;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
    }

    public String getUsageMethod() {
        return usageMethod;
    }

    public void setUsageMethod(String usageMethod) {
        this.usageMethod = usageMethod;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public String getInteractions() {
        return interactions;
    }

    public void setInteractions(String interactions) {
        this.interactions = interactions;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getStorageMethod() {
        return storageMethod;
    }

    public void setStorageMethod(String storageMethod) {
        this.storageMethod = storageMethod;
    }
}
