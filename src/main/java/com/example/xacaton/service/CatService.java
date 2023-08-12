package com.example.xacaton.service;

import com.example.xacaton.model.Cat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface CatService {
    void countCatInImage(MultipartFile cat);
    Cat getCatFromDB(Long id);
}
