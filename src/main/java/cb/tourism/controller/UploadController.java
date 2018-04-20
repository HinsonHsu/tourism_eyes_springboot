package cb.tourism.controller;
import cb.tourism.domain.ResponseBean;
import cb.tourism.service.RecognitionService;
import cb.tourism.util.AESDecode;
import cb.tourism.util.QCloudUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
public class UploadController {

    @Autowired
    private RecognitionService recognitionService;
    /*
    测试api接口 get
     */
    @RequestMapping("/hello")
    public String index(){
        return "Hello World";
    }

    /*
    支持场景识别和图像打标
    type：0 场景识别（默认）
    type：1 图像打标
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseBean upload(@RequestParam("photo") MultipartFile file){
        if (file.isEmpty()){
            return new ResponseBean(400, "文件为空", "请选择上传文件");
        }
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        String sufffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + sufffixName);
        String filePath = "/data/upload/image/";
        //判断当前是否为windows环境
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
            filePath = "E://upload_image//"; //windows下路径
        }

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

            //调用阿里云图像打标解析
            String image_url = "https://calabash-brothers-eyes-1256400655.cos.ap-beijing.myqcloud.com" + destPath + newFileName;
            String recognition = AESDecode.Recognition(image_url, 1);
            return new ResponseBean(20, "upload success", recognitionService.parseFromString(recognition));
//            return "上传成功";
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return new ResponseBean(500, "上传失败", "请稍后重试");
    }
}
