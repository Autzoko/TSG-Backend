package com.example.tsgbackend.system.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@TableName("ImageMap")
@Data
public class ImageMap {
    private String image_id;
    private String image_name;
    private String save_path;
    private String belongs_to;
    private Timestamp last_modified;
}
