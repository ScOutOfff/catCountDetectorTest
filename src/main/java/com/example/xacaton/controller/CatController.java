package com.example.xacaton.controller;

import com.example.xacaton.model.Cat;
import com.example.xacaton.service.CatService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

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
    public ResponseEntity<Cat> countCat(@RequestPart("cat") MultipartFile multipartFile) {
        log.info("Request for counting cats");
        if (multipartFile.isEmpty()) {
            return new ResponseEntity("Select a file", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(catService.countCatInImage(multipartFile));
    }

    @GetMapping("/getCats/{id}")
    public ResponseEntity<Cat> getCats(@PathVariable("id") Long id) {
        return ResponseEntity.ok(catService.getCatFromDB(id));
    }

    @GetMapping(value = "/getCatImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {
        Cat cat = catService.getCatFromDB(id);
        BufferedImage image = ImageIO.read(new File("src/main/resources/images/" + cat.getFileName()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
