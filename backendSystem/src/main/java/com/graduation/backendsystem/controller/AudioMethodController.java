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

//TODO：更改responseBody
@RestController
@RequestMapping("/audioMethod")
public class AudioMethodController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BackendSystemProperties backendSystemProperties;

    @Autowired
    private RunPythonFileService runPythonFileService;

    private ResponseBody process(MultipartFile file, String algorithm, String audioFilePath, String fileName, String outputPath)throws IOException{
        //判断文件类型是否错误
        if(audioFilePath.isBlank()){
            ResponseBody responseBody = new ResponseBody();
            responseBody.setSucceed("false");
            responseBody.setConclusion("文件类型错误");
            return responseBody;
        }

        //判断文件是否已下载
        File uploadFile = new File(audioFilePath);
        if(!uploadFile.exists()){
            if (!uploadFile.getParentFile().exists())
                uploadFile.getParentFile().mkdirs();
            file.transferTo(uploadFile);
        }

        String python = backendSystemProperties.getPython();
        String[] args;
        if(fileName!=null && outputPath!=null) {
            args = new String[]{python, algorithm, audioFilePath, fileName, outputPath};
        }
        else {
            args = new String[]{python, algorithm, audioFilePath};
        }
        ResponseBody responseBody = runPythonFileService.runPythonFile(args);
        if(Objects.isNull(responseBody) || responseBody.getSucceed().isBlank() || responseBody.getSucceed().equals("false")){
            logger.error("Python算法调用失败");
        }
        return responseBody;
    }

    @PostMapping("/audioRepeat")
    public ResponseBody audioRepeat(@RequestParam MultipartFile file)throws IOException {
        logger.info("arrive audioRepeat");
        String algorithm = backendSystemProperties.getAudioRepeatAlgorithmFile();
        String fileName = file.getOriginalFilename();
        String filePath = runPythonFileService.getFileUploadPath(fileName, file.getContentType());
        return process(file, algorithm, filePath, null, null);
    }

    @PostMapping("/audioResample")
    public ResponseBody audioResample(@RequestParam MultipartFile file)throws IOException{
        logger.info("arrive audioResample");
        String algorithm = backendSystemProperties.getAudioRepeatAlgorithmFile();
        String fileName = file.getOriginalFilename();
        String filePath = runPythonFileService.getFileUploadPath(fileName, file.getContentType());
        return process(file, algorithm, filePath, null, null);
    }

    @PostMapping("/audioSplice")
    public ResponseBody audioSplice(@RequestParam MultipartFile file)throws IOException{
        logger.info("arrive audioSplice");
        String algorithm = backendSystemProperties.getAudioSpliceAlgorithmFile();
        String fileName = file.getOriginalFilename();
        String filePath = runPythonFileService.getFileUploadPath(fileName, file.getContentType());
        String outputPath = backendSystemProperties.getAudioResultPath();
        ResponseBody responseBody = process(file, algorithm, filePath, fileName, outputPath);
        if(responseBody == null ||
                (responseBody.getSucceed().equals("false") &&
                        responseBody.getFileExist().equals("false")))return responseBody;

        //方式一：生成静态资源URL，直接供浏览器访问
        if (fileName != null) {
            fileName = fileName.substring(0, fileName.indexOf("."));
        }
        String audioResultUrl = backendSystemProperties.getAudioResultFileUrl() + "/" + fileName + ".png";
        List<HashMap<String,String>> features = responseBody.getFeatures();
        if(features.isEmpty())features.add(new HashMap<>());
        features.get(0).put("fileSrc",audioResultUrl);
        features.get(0).put("fileType","audio");
        responseBody.setFeatures(features);
        logger.info("正常返回");
        //方式二：通过Nginx反向代理图像资源
        return responseBody;
    }

}
