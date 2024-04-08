package com.example.tsgbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tsgbackend.bean.ImageMap;
import com.example.tsgbackend.mapper.ImageMapMapper;
import com.example.tsgbackend.service.ImageMapService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageMapServiceImpl extends ServiceImpl<ImageMapMapper, ImageMap> implements ImageMapService {
    @Resource
    private ImageMapMapper imageMapMapper;

    public ImageMap selectImageById(String image_id) throws Exception {
        return imageMapMapper.selectImageById(image_id);
    }

    public void insertImage(ImageMap imageMap) throws Exception {
        imageMapMapper.insertImage(imageMap);
    }

    public void deleteImageById(String image_id) throws Exception {
        imageMapMapper.deleteImageById(image_id);
    }

    public List<ImageMap> selectImageByUserId(String user_id) throws Exception {
        return imageMapMapper.selectImageByUserId(user_id);
    }

    public void deleteImageByUserId(String user_id) throws Exception {
        imageMapMapper.deleteImageByUserId(user_id);
    }
}
