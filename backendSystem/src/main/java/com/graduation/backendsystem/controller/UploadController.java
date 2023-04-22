package com.graduation.backendsystem.controller;

import com.graduation.backendsystem.config.BackendSystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
public class UploadController{

    @Autowired
    private BackendSystemProperties backendSystemProperties;

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping("/test-ip")
    public String testIp(){
        System.out.println("arrive testIp");
        return "arrive testIp";
    }

    //将上传的文件暂存到后台
    @PostMapping("upload")
    public void upload(@RequestParam MultipartFile file) throws IOException/* throws Exception*/ {
        logger.info("arrive upload");
        String fileName = file.getOriginalFilename();
        String uploadPath = "";
        if(file.getContentType() != null && (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png"))){
            uploadPath = backendSystemProperties.getImageUploadPath() +"/" + fileName;
        }
        else if(file.getContentType() != null && (file.getContentType().equals("audio/mp3") || file.getContentType().equals("audio/wav"))){
            uploadPath = backendSystemProperties.getAudioUploadPath() +"/" + fileName;
        }
        else if(file.getContentType() != null && file.getContentType().equals("video/mp4" )){
            uploadPath = backendSystemProperties.getVideoUploadPath() +"/" + fileName;
        }
        else{
            logger.error("文件类型出错,必须是图片/音频/视频");
            return;
        }
        File uploadFile = new File(uploadPath);
        if (!uploadFile.getParentFile().exists())
            uploadFile.getParentFile().mkdirs();
        file.transferTo(uploadFile);
    }
}
