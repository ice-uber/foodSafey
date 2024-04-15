package com.weike.common.controller;

import com.weike.common.utils.AliOssUtil;
import com.weike.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName: UploadFile
 * @Author: YuanDing
 * @Date: 2024/2/3 22:07
 * @Description:
 */

@RestController
@RequestMapping("/admin/common")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;


    @PostMapping("/upload")
    public R uploadFile(@RequestBody MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        // 原始文件后缀
        String fileFormat = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileFormat;
        String filePath = null;
        try {
            filePath = aliOssUtil.upload(file.getBytes(), fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.ok().put("data" , filePath);
    }
}
