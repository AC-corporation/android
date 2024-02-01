package com.example.allclear.data.model;

public class GraduationModel {
    private String requirementArgument;
    private int requirementCriteria;
    private int requirementComplete;

    public GraduationModel(String requirementArgument, int requirementCriteria, int requirementComplete) {
        this.requirementArgument = requirementArgument;
        this.requirementCriteria = requirementCriteria;
        this.requirementComplete = requirementComplete;
    }

    public String getRequirementArgument() {
        return requirementArgument;
    }

    public int getRequirementCriteria() {
        return requirementCriteria;
    }

    public int getRequirementComplete() {
        return requirementComplete;
    }
}
