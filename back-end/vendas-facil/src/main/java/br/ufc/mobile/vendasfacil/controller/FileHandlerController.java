package br.ufc.mobile.vendasfacil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.ufc.mobile.vendasfacil.interfaces.AmazonS3ClientService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileHandlerController {

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    @PostMapping
    public Map<String, String> uploadFile(@RequestPart(value = "file") MultipartFile file)
    {
        //this.amazonS3ClientService.uploadFileToS3Bucket(file, true);

        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + file.getOriginalFilename() + "] uploading request submitted successfully.");

        return response;
    }
}
