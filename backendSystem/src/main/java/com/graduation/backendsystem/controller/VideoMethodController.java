package com.graduation.backendsystem.controller;

import com.graduation.backendsystem.config.BackendSystemProperties;
import com.graduation.backendsystem.entity.ResponseBody;
import com.graduation.backendsystem.service.RunPythonFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/videoMethod")
public class VideoMethodController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BackendSystemProperties backendSystemProperties;

    @Autowired
    private RunPythonFileService runPythonFileService;

    private ResponseBody process(MultipartFile file, String fileName, String algorithm, String videoFilePath, String videoTempFilePath)throws IOException {
        //判断文件类型是否错误
        if(videoFilePath.isBlank()){
            ResponseBody responseBody = new ResponseBody();
            responseBody.setSucceed("false");
            responseBody.setConclusion("文件类型错误");
            return responseBody;
        }

        //判断文件是否已下载
        File uploadFile = new File(videoFilePath);
        if(!uploadFile.exists()){
            if (!uploadFile.getParentFile().exists())
                uploadFile.getParentFile().mkdirs();
            file.transferTo(uploadFile);
        }

        String python = backendSystemProperties.getPython();
        String videoResultPath = backendSystemProperties.getVideoResultPath();
        if(!new File(videoFilePath).exists()){
            ResponseBody responseBody = new ResponseBody();
            responseBody.setSucceed("false");
            responseBody.setFileExist("false");
            responseBody.setConclusion("文件未上传");
            return responseBody;
        }
        String[] args = new String[]{python, algorithm, videoFilePath, videoResultPath, videoTempFilePath};
        ResponseBody responseBody = runPythonFileService.runPythonFile(args);
        if(Objects.isNull(responseBody) || responseBody.getSucceed().equals("false")){
            logger.error("Python算法调用失败");
        }
        return responseBody;
    }

    @PostMapping("/videoMethod1")
    public ResponseBody videoMethod1(@RequestParam MultipartFile file) throws IOException {
        logger.info("arrive videoMethod1");
        String fileName = file.getOriginalFilename();
        String filePath = runPythonFileService.getFileUploadPath(fileName, file.getContentType());
        String algorithm = backendSystemProperties.getVideoMethod1AlgorithmFile();
        String videoTempFilePath = backendSystemProperties.getVideoMethod1TmpPath();

        ResponseBody responseBody = process(file, fileName, algorithm, filePath, videoTempFilePath);
        if(responseBody == null ||
                (responseBody.getSucceed().equals("false") &&
                        responseBody.getFileExist().equals("false")))return responseBody;

        //方式一：生成静态资源URL，直接供浏览器访问
        if (fileName != null) {
            fileName = fileName.substring(0, fileName.indexOf("."));
        }
        String videoResultUrl = backendSystemProperties.getVideoResultFileUrl() + "/" + fileName + ".png";
        List<HashMap<String,String>> features = responseBody.getFeatures();
        if(features.isEmpty())features.add(new HashMap<>());
        features.get(0).put("fileSrc",videoResultUrl);
        features.get(0).put("fileType","video");
        responseBody.setFeatures(features);
        logger.info("正常返回");
        //方式二：通过Nginx反向代理图像资源
        return responseBody;
    }
}
