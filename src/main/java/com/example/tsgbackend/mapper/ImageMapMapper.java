package com.example.tsgbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tsgbackend.bean.ImageMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapMapper extends BaseMapper<ImageMap> {
    ImageMap selectImageById(String image_id);
    void insertImage(ImageMap imageMap);
    void deleteImageById(String image_id);
    List<ImageMap> selectImageByUserId(String user_id);
    void deleteImageByUserId(String user_id);
}
