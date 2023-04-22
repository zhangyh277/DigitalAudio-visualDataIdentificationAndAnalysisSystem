package com.graduation.backendsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation.backendsystem.entity.ResponseBody;
import com.graduation.backendsystem.service.RunPythonFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.graduation.backendsystem.config.WebMvcConfig.*;

@SpringBootTest
class BackendSystemApplicationTests {

    @Autowired
    private RunPythonFileService runPythonFileService;

    private String basePythonFilePath = "D:/WorkSpace/graduation-project/项目资料/application/";
    private String baseImageFilePath = "D:/WorkSpace/graduation-project/test-upload-dest/image/";
    private String baseImageResultPath = "D:/WorkSpace/graduation-project/test-result/imageResult/";

    private String baseVideoFilePath = "D:/WorkSpace/graduation-project/test-upload-dest/video/";
    private String baseVideoResultPath = "D:/WorkSpace/graduation-project/test-result/videoResult";


    @Test
    void contextLoads() throws JsonProcessingException {

    }

}
