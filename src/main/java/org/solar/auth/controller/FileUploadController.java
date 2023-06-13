package org.solar.auth.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("upload")
@RestController
public class FileUploadController {


    @Value("${imagesPath}")
    String imagesPath;

    @PostMapping
    public String uploadFile(@RequestPart Part file) throws IOException {

//
//        System.out.println(file.getContentType());
//        System.out.println(file.getName());
//        System.out.println(file.getSize());
//        for (String headerName : file.getHeaderNames()) {
//            System.out.println("headerName" + headerName);
//            System.out.println(file.getHeader(headerName));
//        }
//
//        System.out.println(file.getSubmittedFileName());
        //
        String uuid = UUID.randomUUID().toString();

        //
        file.write("C:\\Users\\solar\\Desktop\\auth\\src\\main\\resources\\static\\"+uuid);

        return uuid;
    }
}
