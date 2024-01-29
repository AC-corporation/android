package com.example.allclear.data.responese;


import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

class RequirementResponseDto {
    @SerializedName("requirementId")
    private int requirementId;
    @SerializedName("requirementComponentList")
    private ArrayList<RequirementComponentDto> requirementComponentList;

    class RequirementComponentDto {
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
    }
}