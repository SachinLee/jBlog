package cn.sachin.jaBlog.service;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    String uploadFile(MultipartFile file);

    GridFSDBFile getFileById(String id);
}
