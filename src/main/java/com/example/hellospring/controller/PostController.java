package com.example.hellospring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class PostController {
    @PostMapping("/post/img")
    @ResponseBody
    public String create(@RequestPart List<MultipartFile> files) throws IllegalStateException, IOException {
        PostImg post = new PostImg();
        for (MultipartFile file: files) {
            PostImg img = new PostImg(UUID.randomUUID().toString(), file.getOriginalFilename(), file.getContentType());
            File newFileName = new File(img.getUuid() + "_" + img.getFileName());
            file.transferTo(newFileName);
        }

        return "이미지 업로드 완료"; //home 화면으로 보내기
    }
}
