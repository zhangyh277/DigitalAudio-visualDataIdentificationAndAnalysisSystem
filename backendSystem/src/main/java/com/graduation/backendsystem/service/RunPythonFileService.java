package com.graduation.backendsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.backendsystem.config.BackendSystemProperties;
import com.graduation.backendsystem.entity.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Service
public class RunPythonFileService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BackendSystemProperties backendSystemProperties;

    public ResponseBody runPythonFile(String[] args){
        try {
            // 执行py脚本
            Process proc = Runtime.getRuntime().exec(args);
            String line, ret;
            // py脚本返回值为算法执行状态，结果图片从RESULT_PATH中读取并返回给前端
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder(40);
            while ((line = in.readLine()) != null){
                sb.append(line);
            }
            //ret返回值是一个json数组，图片通过URL存储src
            ret = sb.toString().replace('\'','\"' );
            if(ret.isBlank()){
                logger.error("python算法执行失败，内部报错信息：");
                BufferedReader errorIn = new BufferedReader(new InputStreamReader(proc.getErrorStream(), Charset.forName("UTF-8")));
                StringBuilder errorSb = new StringBuilder(40);
                while ((line = errorIn.readLine()) != null) errorSb.append(line);
                //ret返回值是一个json数组，图片通过URL存储src
                String errorRet = errorSb.toString();
                System.out.println(errorRet);
                errorIn.close();
                proc.waitFor();
                return null;
            }
            System.out.println("inputStream is:" + ret);
            ObjectMapper mapper = new ObjectMapper();
            ResponseBody responseBody = mapper.readValue(ret, ResponseBody.class);

            in.close();
            proc.waitFor();
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFileUploadPath(String fileName, String fileType){
        String uploadPath = "";
        if(fileType.isBlank()){
            logger.error("Content-Type不存在");
        }
        else if(fileType.equals("image/jpeg") || fileType.equals("image/png")){
            uploadPath = backendSystemProperties.getImageUploadPath() +"/" + fileName;
        }
        else if(fileType.equals("audio/mp3") || fileType.equals("audio/wav")){
            uploadPath = backendSystemProperties.getAudioUploadPath() +"/" + fileName;
        }
        else if(fileType.equals("video/mp4" )){
            uploadPath = backendSystemProperties.getVideoUploadPath() +"/" + fileName;
        }
        else{
            logger.error("文件类型出错,必须是图片/音频/视频");
        }
        return uploadPath;
    }
}
