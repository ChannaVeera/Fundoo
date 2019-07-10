package com.BridgeIt.FundooApp.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.BridgeIt.FundooApp.response.Response;
import com.BridgeIt.FundooApp.user.Service.AmazonService;




@RestController
@RequestMapping("/storage/")
public class BucketController {

    private AmazonService amazonClient;

    @Autowired
    BucketController(AmazonService amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<Response> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile,@RequestHeader String token ) throws IOException {
    	String messege = amazonClient.uploadFile(multipartFile, token);
    	Response response = new Response(HttpStatus.OK.value(), messege, "");
    	return new ResponseEntity<Response>(response,HttpStatus.OK);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<Response> deleteFile(@RequestHeader String fileName, @RequestHeader String token) {
    	String messege = amazonClient.deleteFileFromS3Bucket(fileName, token);
    	Response response = new Response(HttpStatus.OK.value(), messege, "");
      return new ResponseEntity<Response>(response,HttpStatus.OK);
    }
}