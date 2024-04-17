package com.example.tsgbackend.system.controller;

import com.example.tsgbackend.system.bean.ImageMap;
import com.example.tsgbackend.system.service.ImageMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/image-caption")
public class ImageUploadController {

    @Autowired
    private ImageMapService imageMapService;

    private final String modelServerUrl = "";
    private final String imageSavePath = "";

    private final String test_user = "test_user_1";
    private final String save_path = "C:\\Users\\llt02\\Desktop\\images";

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveImage(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(setResponse(null));
        }
        try {
            File directory = new File(save_path);
            if(!directory.exists()) {
                directory.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID() + "-" + originalFilename;
            String filePath = save_path + File.separator + uniqueFilename;

            String fileId = UUID.randomUUID().toString();
            file.transferTo((new File(filePath)));

            ImageMap tmpImageMap = setImageMap(fileId, uniqueFilename, save_path, test_user, new Timestamp(System.currentTimeMillis()));

            imageMapService.insertImage(tmpImageMap);

            return ResponseEntity.ok(setResponse(fileId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(setResponse(null));
        }
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Map<String, Object>> deleteImage(@PathVariable String fileId) {
        try {
            ImageMap imageMap = imageMapService.selectImageById(fileId);
            File toDeleteFile = new File(imageMap.getSave_path() + "/" + imageMap.getImage_name());
            if(toDeleteFile.exists()) {
                if(toDeleteFile.delete()) {
                    imageMapService.deleteImageById(fileId);
                    return ResponseEntity.status(HttpStatus.OK).body(null);
                } else {
                    throw new Exception("Delete failed");
                }
            } else {
                throw new Exception("File not exist");
            }
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(setResponse(null, e));
        }
    }

    @DeleteMapping("/delete-all/{user_id}")
    public ResponseEntity<Map<String, Object>> deleteByUser(@PathVariable String user_id) {
        return ResponseEntity.status(HttpStatus.OK).body(setResponse(null));
    }

//    @GetMapping("/generate")
//    public ResponseEntity<String> generateDescription() {
//        try {
//            String description = sendGenerateRequest();
//            return new ResponseEntity<>(description, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Generate Error", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    private void save2server(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File destinationFile = new File(imageSavePath + File.separator + fileName);

        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    private Map<String, Object> setResponse(Object fileId, Object... args) {
        Map<String, Object> response = new HashMap<>();
        response.put("fileId", fileId);
        for(int i = 0; i < args.length; i++) {
            response.put("arg-" + i, args[i]);
        }
        return response;
    }

    private ImageMap setImageMap(String image_id, String image_name, String _save_path, String _belongs_to, Timestamp time) {
        ImageMap imageMap = new ImageMap();
        imageMap.setImage_id(image_id);
        imageMap.setImage_name(image_name);
        imageMap.setSave_path(_save_path);
        imageMap.setBelongs_to(_belongs_to);
        imageMap.setLast_modified(time);
        return imageMap;
    }
}
