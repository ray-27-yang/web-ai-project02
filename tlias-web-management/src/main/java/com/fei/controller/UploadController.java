package com.fei.controller;

import com.fei.pojo.Result;
import com.fei.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {
    /**
     * 本地磁盘存储的方案
     */
//    @PostMapping("/upload")
//    //保证前端传输的参数名和服务器端方法形参一致，@RequestParam注解可省略
//    public Result upload(String name, Integer age, MultipartFile file) throws Exception {
//        log.info("接收到的参数：{}，{}，{}",name,age,file);
//        //获取原始文件名
//        String originalFilename = file.getOriginalFilename();
//        //新的文件名
//        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String newFileName = UUID.randomUUID().toString() + extension;
//        //保存文件
//        file.transferTo(new File("D:/virtual c/学校杂七杂八/" + newFileName));
//        return Result.success();
//    }
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}",file.getOriginalFilename());
        //文件交给OSS存储管理
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传到OSS的url：{}",url);
        return Result.success(url);
    }
}
