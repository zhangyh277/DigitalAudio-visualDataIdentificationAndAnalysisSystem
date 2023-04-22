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
@RequestMapping("/imageMethod")
public class ImageMethodController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BackendSystemProperties backendSystemProperties;

    @Autowired
    private RunPythonFileService runPythonFileService;

    private ResponseBody process(MultipartFile file, String algorithm, String imageFileName, String filePath)throws IOException{
        //判断文件类型是否错误
        if(filePath.isBlank()){
            ResponseBody responseBody = new ResponseBody();
            responseBody.setSucceed("false");
            responseBody.setConclusion("文件类型错误");
            return responseBody;
        }

        //判断文件是否已下载
        File uploadFile = new File(filePath);
        if(!uploadFile.exists()){
            if (!uploadFile.getParentFile().exists())
                uploadFile.getParentFile().mkdirs();
            file.transferTo(uploadFile);
        }

        String python = backendSystemProperties.getPython();
        String imageResultPath = backendSystemProperties.getImgaeResultPath();

        String[] args = new String[]{python, algorithm, filePath, imageResultPath, imageFileName};
        ResponseBody responseBody = runPythonFileService.runPythonFile(args);
        if(Objects.isNull(responseBody) || responseBody.getSucceed().isBlank() || responseBody.getSucceed().equals("false")){
            logger.error("Python算法调用失败");
            return null;
        }
        //方式一：生成静态资源URL，直接供浏览器访问
        String imageResultUrL = backendSystemProperties.getImageResultFileUrl() + "/" + imageFileName;
        List<HashMap<String,String>> features = responseBody.getFeatures();
        if(features.isEmpty())features.add(new HashMap<>());
        features.get(0).put("fileSrc",imageResultUrL);
        features.get(0).put("fileType","image");
        responseBody.setFeatures(features);
        System.out.println(responseBody);
        //TODO：通过Nginx反向代理图像资源，而不是直接供浏览器访问
        return responseBody;
    }

    @PostMapping("/imageDetection")
    public ResponseBody imageDetection(@RequestParam MultipartFile file)throws IOException{
        logger.info("arrive imageDetection");
        String algorithm = backendSystemProperties.getImageDetectionAlgorithmFile();
        String fileName = file.getOriginalFilename();
        String filePath = runPythonFileService.getFileUploadPath(fileName, file.getContentType());
        return process(file, algorithm, fileName, filePath);
    }

    @PostMapping("/imageForenSic")
    public ResponseBody imageForenSic(@RequestParam MultipartFile file)throws IOException{
        logger.info("arrive imageForenSic");
        String algorithm = backendSystemProperties.getImageForensicAlgorithmFile();
        String fileName = file.getOriginalFilename();
        String filePath = runPythonFileService.getFileUploadPath(fileName, file.getContentType());
        return process(file, algorithm, fileName, filePath);
    }
}
