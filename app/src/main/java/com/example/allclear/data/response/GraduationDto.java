package com.example.allclear.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GraduationDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    public RequirementResponseDto data;

    public static class RequirementResponseDto {
        @SerializedName("requirementId")
        private int requirementId;
        @SerializedName("requirementComponentList")
        private List<RequirementComponentDto> requirementComponentList;

        public List<RequirementComponentDto> getRequirementComponentList() {
            return this.requirementComponentList;
        }

        public static class RequirementComponentDto {
            @SerializedName("requirementComponentId")
            private long requirementComponentId;
            @SerializedName("requirementCategory")
            private String requirementCategory;
            @SerializedName("requirementArgument")
            private String requirementArgument;
            @SerializedName("requirementCriteria")
            private double requirementCriteria;
            @SerializedName("requirementComplete")
            private double requirementComplete;
            @SerializedName("requirementResult")
            private String requirementResult;

            public String getRequirementCategory() {
                return this.requirementCategory;
            }

            public String getRequirementArgument() {
                return this.requirementArgument;
            }

            public double getRequirementCriteria() {
                return this.requirementCriteria;
            }

            public double getRequirementComplete() {
                return this.requirementComplete;
            }

        }
    }
}