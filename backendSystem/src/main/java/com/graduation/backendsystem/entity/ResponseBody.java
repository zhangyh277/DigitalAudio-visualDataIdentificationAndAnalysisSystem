package com.graduation.backendsystem.entity;

import java.util.HashMap;
import java.util.List;

public class ResponseBody {
    private String succeed = "true";
    private String status = "true";
    private String conclusion = "";
    private String threshold = "";
    private String message = "";
    private String confidence = "";
    private String fileExist = "true";
    private List<HashMap<String, String>> features;
    private List<String> marks;
    private HashMap<String, String> ext;
    private List<List<String>> mask_index;
    private List<String> mask_data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public List<HashMap<String, String>> getFeatures() {
        return features;
    }

    public void setFeatures(List<HashMap<String, String>> features) {
        this.features = features;
    }

    public List<String> getMarks() {
        return marks;
    }

    public void setMarks(List<String> marks) {
        this.marks = marks;
    }

    public HashMap<String, String> getExt() {
        return ext;
    }

    public void setExt(HashMap<String, String> ext) {
        this.ext = ext;
    }

    public String getFileExist() {
        return fileExist;
    }

    public void setFileExist(String fileExist) {
        this.fileExist = fileExist;
    }


    public List<String> getMask_data() {
        return mask_data;
    }

    public void setMask_data(List<String> mask_data) {
        this.mask_data = mask_data;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public List<List<String>> getMask_index() {
        return mask_index;
    }

    public void setMask_index(List<List<String>> mask_index) {
        this.mask_index = mask_index;
    }


    public String getSucceed() {
        return succeed;
    }

    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }
}
