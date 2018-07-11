package cn.sachin.jaBlog.controller;

import cn.sachin.jaBlog.service.FileService;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/{avatar}")
    @ResponseBody
    public void getAvatar(HttpServletRequest request, HttpServletResponse response,
                         @PathVariable(value = "avatar") String avatar) {
        try {
            OutputStream out = response.getOutputStream();

            GridFSDBFile gridFSDBFile = fileService.getFileById(avatar);

            String filename = gridFSDBFile.getFilename();
            String fileName = new String(filename.getBytes("GBK"), "ISO8859-1");

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            gridFSDBFile.writeTo(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
