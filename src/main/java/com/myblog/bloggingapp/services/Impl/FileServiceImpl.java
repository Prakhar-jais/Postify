package com.myblog.bloggingapp.services.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myblog.bloggingapp.services.FileService;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+File.separator+fileName;
        InputStream is = new FileInputStream(fullPath);


        return is;
    }

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        
        // file name extraction 

        String name = file.getOriginalFilename();


        //generating random name of file in a project
        String randomId = UUID.randomUUID()
.toString();
        String fileName_new = randomId.concat(name.substring(name.lastIndexOf(".")));


        // path creation till file i.e full path

        String filePath = path+File.separator+fileName_new;


        // create foldr of images if not created 

        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }


        // copy the file

        Files.copy(file.getInputStream(),Paths.get(filePath));

        return fileName_new;
    }
    
}
