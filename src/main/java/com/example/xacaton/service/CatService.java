package com.example.xacaton.service;

import com.example.xacaton.model.Cat;
import org.springframework.web.multipart.MultipartFile;

public interface CatService {
    Cat countCatInImage(MultipartFile cat);
    Cat getCatFromDB(Long id);
}
