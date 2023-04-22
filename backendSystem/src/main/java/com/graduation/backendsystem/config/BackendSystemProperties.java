package com.graduation.backendsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("graduation.backend-system")
public class BackendSystemProperties {
    private String python;

    private String algorithmFileBase;
    private String imageDetectionAlgorithmFile;
    private String imageForensicAlgorithmFile;
    private String audioRepeatAlgorithmFile;
    private String audioResampleAlgorithmFile;
    private String audioSpliceAlgorithmFile;
    private String videoDetectionMethod1AlgorithmFile;
    private String videoDetectionMethod2AlgorithmFile;
    private String videoMethod1AlgorithmFile;

    private String uploadBase;
    private String imageUploadPath;
    private String audioUploadPath;
    private String videoUploadPath;

    private String fileBaseUrl;
    private String imageResultFileUrl;
    private String audioResultFileUrl;
    private String videoResultFileUrl;

    private String resultBasePath;
    private String imgaeResultPath;
    private String audioResultPath;
    private String videoResultPath;

    private String videoDetectionMethod1TmpPath;
    private String videoDetectionMethod2TmpPath;
    private String videoMethod1TmpPath;

    public String getPython() {
        return python;
    }

    public void setPython(String python) {
        this.python = python;
    }

    public String getAlgorithmFileBase() {
        return algorithmFileBase;
    }

    public void setAlgorithmFileBase(String algorithmFileBase) {
        this.algorithmFileBase = algorithmFileBase;
    }

    public String getImageDetectionAlgorithmFile() {
        return algorithmFileBase.concat("/image_detection/SingleImageDetectionProcess.py");
    }

    public String getImageForensicAlgorithmFile() {
        return algorithmFileBase.concat("/image_forensic/SingleImageForensicProcess.py");
    }


    public String getAudioRepeatAlgorithmFile() {
        return algorithmFileBase.concat("/audio_repeat/SingleAudioRepeatProcess.py");
    }


    public String getAudioResampleAlgorithmFile() {
        return algorithmFileBase.concat("/audio_resample/SingleAudioResampleProcess.py");
    }


    public String getAudioSpliceAlgorithmFile() {
        return algorithmFileBase.concat("/audiosplice/SingleAudioSpliceProcess.py");
    }

    public String getVideoDetectionMethod1AlgorithmFile() {
        return algorithmFileBase.concat("/video_detection_method1/SingleVideoDetectionMethod1Process.py");
    }


    public String getVideoDetectionMethod2AlgorithmFile() {
        return algorithmFileBase.concat("/video_detection_method2/SingleVideoDetectionMethod2.py");
    }

    public String getVideoMethod1AlgorithmFile() {
        return algorithmFileBase.concat("/videomethod1/SingleVideoMethod1Process.py");
    }

    public String getUploadBase() {
        return uploadBase;
    }

    public void setUploadBase(String uploadBase) {
        this.uploadBase = uploadBase;
    }

    public String getImageUploadPath() {
        return uploadBase.concat("/image");
    }


    public String getAudioUploadPath() {
        return uploadBase.concat("/audio");
    }


    public String getVideoUploadPath() {
        return uploadBase.concat("/video");
    }


    public String getFileBaseUrl() {
        return fileBaseUrl;
    }

    public void setFileBaseUrl(String fileBaseUrl) {
        this.fileBaseUrl = fileBaseUrl;
    }

    public String getImageResultFileUrl() {
        return fileBaseUrl.concat("/image");
    }


    public String getAudioResultFileUrl() {
        return fileBaseUrl.concat("/audio");
    }


    public String getVideoResultFileUrl() {
        return fileBaseUrl.concat("/video");
    }

    public void setResultBasePath(String resultBasePath) {
        this.resultBasePath = resultBasePath;
    }

    public String getResultBasePath() {
        return resultBasePath;
    }

    public String getImgaeResultPath() {
        return resultBasePath.concat("/imageResult/");
    }

    public String getAudioResultPath() {
        return resultBasePath.concat("/audioResult/");
    }

    public String getVideoResultPath() {
        return resultBasePath.concat("/videoResult/");
    }

    public String getVideoDetectionMethod1TmpPath() {
        return this.algorithmFileBase.concat("/video_detection_method1").concat("/tmp");
    }


    public String getVideoDetectionMethod2TmpPath() {
        return this.algorithmFileBase.concat("/video_detection_method2").concat("/tmp");
    }

    public String getVideoMethod1TmpPath() {
        return this.algorithmFileBase.concat("/videomethod1").concat("/tmp");
    }
}
