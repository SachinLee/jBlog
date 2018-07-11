package cn.sachin.jaBlog.service.impl;

import cn.sachin.jaBlog.common.Constant;
import cn.sachin.jaBlog.service.FileService;
import cn.sachin.jaBlog.util.MD5Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String uploadFile(MultipartFile file) {
        String filename = file.getOriginalFilename(); //文件名
        String fileName = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
        GridFSInputFile gridFSInputFile = null;
        try {
            DB db = mongoTemplate.getDb(); //获取数据库信息
            GridFS gridFS = new GridFS(db, Constant.MONGO_AVATAR); //获取集合

            //判断是否已经存在，防止重复上传
            GridFSDBFile gridFSDBFile = this.getByMD5(file, gridFS);
            if (gridFSDBFile != null) {
                return gridFSDBFile.getId().toString();
            }

            gridFSInputFile = gridFS.createFile(file.getInputStream());
            gridFSInputFile.put("filename", fileName);
            if(fileName.lastIndexOf(".")<0) {
                gridFSInputFile.put("contentType","");
            }else {
                gridFSInputFile.put("contentType", fileName.substring(fileName.lastIndexOf(".")));
            }

            gridFSInputFile.save();

        } catch (Exception e) {
            log.error("上传出错，错误原因：{}"+e.getMessage());
            e.printStackTrace();
        }

        return gridFSInputFile.getId().toString();
    }

    @Override
    public GridFSDBFile getFileById(String id) {
        DB db = mongoTemplate.getDb();
        GridFSDBFile gfsFile = null;
        try {
            gfsFile = new GridFS(db, Constant.MONGO_AVATAR).find(new ObjectId(id));
        } catch (Exception e) {
            log.error("文件获取失败,失败原因{}", e.getMessage());
            e.printStackTrace();
        }
        return gfsFile;

    }

    private GridFSDBFile getByMD5(MultipartFile file, GridFS gridFS) {
        try {
            String md5Num = MD5Util.getMD5(file.getInputStream());
            DBObject query = new BasicDBObject().append("md5", md5Num);
            GridFSDBFile hasFile = gridFS.findOne(query);

            return hasFile;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
