package com.example.xacaton.service;

import com.example.xacaton.model.Cat;
import com.example.xacaton.neuronet.CatImageDetector;
import com.example.xacaton.repository.CatRepo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

public class CatServiceImpl implements CatService {

    private final CatRepo catRepo;

    @Autowired
    public CatServiceImpl(CatRepo catRepo) {
        this.catRepo = catRepo;
    }

    @Override
    public void countCatInImage(MultipartFile multipartFile) {
        BufferedImage image;
        try {
            image = ImageIO.read(multipartFile.getInputStream());
            ImageIO.write(image, Objects.requireNonNull(FilenameUtils.getExtension(multipartFile.getOriginalFilename())),
                    new File(multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int[] count = CatImageDetector.method(image);

        Cat cat = Cat.builder()
                .fileName(multipartFile.getName())
                .catsFirstCount(count[0])
                .catsSecondCount(count[1])
                .build();
        catRepo.save(cat);
    }

    @Override
    public Cat getCatFromDB(Long id) {
        return catRepo.getCatById(id);
    }
}
