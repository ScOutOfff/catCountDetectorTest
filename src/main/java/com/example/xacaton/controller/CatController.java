package com.example.xacaton.controller;

import com.example.xacaton.service.CatService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/cats")
@Log
public class CatController {
    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @PostMapping("/count")
    public ResponseEntity<HttpStatus> countCat(@RequestPart("Cat") MultipartFile multipartFile) {
        log.info("Request for counting cats");
        if (multipartFile.isEmpty()) {
            return new ResponseEntity("Select a file", HttpStatus.NOT_FOUND);
        }
        catService.countCatInImage(multipartFile);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
