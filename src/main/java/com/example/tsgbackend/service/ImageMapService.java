package com.example.tsgbackend.service;

import com.example.tsgbackend.bean.ImageMap;

import java.util.List;

public interface ImageMapService {
    ImageMap selectImageById(String image_id) throws Exception;
    void insertImage(ImageMap imageMap) throws Exception;
    void deleteImageById(String image_id) throws Exception;
    List<ImageMap> selectImageByUserId(String user_id) throws Exception;
    void deleteImageByUserId(String user_id) throws Exception;
}
