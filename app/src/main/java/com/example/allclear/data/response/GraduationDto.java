package com.example.allclear.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
        private ArrayList<RequirementComponentDto> requirementComponentList;

        public ArrayList<RequirementComponentDto> getRequirementComponentList() {
            return this.requirementComponentList;
        }

        public static class RequirementComponentDto {
            @SerializedName("requirementComponentId")
            private int requirementComponentId;
            @SerializedName("requirementCategory")
            private String requirementCategory;
            @SerializedName("requirementArgument")
            private String requirementArgument;
            @SerializedName("requirementCriteria")
            private int requirementCriteria;
            @SerializedName("requirementComplete")
            private int requirementComplete;
            @SerializedName("requirementResult")
            private String requirementResult;

            public String getRequirementCategory() {
                return this.requirementCategory;
            }

            public String getRequirementArgument() {
                return this.requirementArgument;
            }

            public int getRequirementCriteria() {
                return this.requirementCriteria;
            }

            public int getRequirementComplete() {
                return this.requirementComplete;
            }

        }
    }
}