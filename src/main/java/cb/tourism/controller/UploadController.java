package cb.tourism.controller;
import cb.tourism.util.AESDecode;
import cb.tourism.util.QCloudUpload;
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
        String newFileName = Long.toString(System.currentTimeMillis()) + sufffixName;
        File dest = new File(filePath + newFileName);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);

            //上传至腾讯云对象存储
            String destPath = "/image/";
            QCloudUpload.SimpleUploadFileFromLocal(filePath, newFileName, destPath);
            String image_url = "https://calabash-brothers-eyes-1256400655.cos.ap-beijing.myqcloud.com" + destPath + newFileName;
            String recognition = AESDecode.Recognition(image_url);
            return recognition;
//            return "上传成功";
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return "上传失败";
    }
}
