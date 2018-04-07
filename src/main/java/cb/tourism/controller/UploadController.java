package cb.tourism.controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
public class UploadController {
    @RequestMapping("/hello")
    public String index(){
        return "Hello World";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("photo") MultipartFile file){
        if (file.isEmpty()){
            return "文件为空";
        }
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        String sufffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + sufffixName);
//        String filePath = "E://upload_image//"; //windows下路径
        String filePath = "/data/upload/image/";
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return "上传失败";
    }
}
