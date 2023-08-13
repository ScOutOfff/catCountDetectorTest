package com.example.xacaton.service;

import com.example.xacaton.model.Cat;
import com.example.xacaton.neuronet.CatImageDetector;
import com.example.xacaton.repository.CatRepo;
import lombok.extern.java.Log;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@Log
public class CatServiceImpl implements CatService {

    private final CatRepo catRepo;

    @Autowired
    public CatServiceImpl(CatRepo catRepo) {
        this.catRepo = catRepo;
    }

    @Override
    public Cat countCatInImage(MultipartFile multipartFile) {
        BufferedImage image;
        try {
            image = ImageIO.read(multipartFile.getInputStream());
            File outputFile = new File("src/main/resources/images/" + multipartFile.getOriginalFilename());
            ImageIO.write(image, Objects.requireNonNull(FilenameUtils.getExtension(multipartFile.getOriginalFilename())), outputFile);
            log.info("saved image");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int[] count = CatImageDetector.countCatsAndMarkIt(image);
        File file = new File("src/main/resources/images/marked_" + multipartFile.getOriginalFilename());
        try {
            ImageIO.write(image, Objects.requireNonNull(FilenameUtils.getExtension(multipartFile.getOriginalFilename())), file);
            log.info("saved marked image");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Cat cat = Cat.builder()
                .fileName(multipartFile.getOriginalFilename())
                .markedFileName("marked_" + multipartFile.getOriginalFilename())
                .catsFirstCount(count[0])
                .catsSecondCount(count[1])
                .build();
        return catRepo.save(cat);
    }

    @Override
    public Cat getCatFromDB(Long id) {
        return catRepo.getCatById(id);
    }
}
