package com.example.allclear.data.request;

import com.google.gson.annotations.SerializedName;

public class SubjectSearchsRequestDto {
    @SerializedName("year")
    private String year;
    @SerializedName("searchString")
    private String searchString;
    @SerializedName("courseClassification")
    private String courseClassification;
    @SerializedName("majorName")
    private String majorName;
    @SerializedName("electivesYear")
    private String electivesYear;
    @SerializedName("electivesClassification")
    private String electivesClassification;
    @SerializedName("requiredElectivesClassification")
    private String requiredElectivesClassification;
    @SerializedName("requiredElectivesName")
    private String requiredElectivesName;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getCourseClassification() {
        return courseClassification;
    }

    public void setCourseClassification(String courseClassification) {
        this.courseClassification = courseClassification;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getElectivesYear() {
        return electivesYear;
    }

    public void setElectivesYear(String electivesYear) {
        this.electivesYear = electivesYear;
    }

    public String getElectivesClassification() {
        return electivesClassification;
    }

    public void setElectivesClassification(String electivesClassification) {
        this.electivesClassification = electivesClassification;
    }

    public String getRequiredElectivesClassification() {
        return requiredElectivesClassification;
    }

    public void setRequiredElectivesClassification(String requiredElectivesClassification) {
        this.requiredElectivesClassification = requiredElectivesClassification;
    }

    public String getRequiredElectivesName() {
        return requiredElectivesName;
    }

    public void setRequiredElectivesName(String requiredElectivesName) {
        this.requiredElectivesName = requiredElectivesName;
    }
}
