package cb.tourism.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.demo.SimpleUploadFileDemo;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class QCloudUpload {
//    public static void pushToBuckets() {
//        // 1 初始化用户身份信息(secretId, secretKey)
//        COSCredentials cred = new BasicCOSCredentials("AKIDKYbeZLybwX2MURnXL2Xs32qSQFsJYlWG", "2yOhojUAfy54vfE0hIk1634xjJiw2xhR");
//        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
//        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));
//        // 3 生成cos客户端
//        COSClient cosclient = new COSClient(cred, clientConfig);
//        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
//        String bucketName = "calabash-brothers-eyes-1256400655";
//        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口
//        // 大文件上传请参照 API 文档高级 API 上传
//        File localFile = new File("E://springboot_upload//微信图片_20180330093612.jpg");
//        // 指定要上传到 COS 上的路径
//        String key = "/image/test.jpg";
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
//        PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
//        // 关闭客户端(关闭后台线程)
//        cosclient.shutdown();
//    }

    public static void SimpleUploadFileFromLocal(String localPath, String fileName, String destPath) {
        COSCredentials cred = new BasicCOSCredentials("AKIDKYbeZLybwX2MURnXL2Xs32qSQFsJYlWG", "2yOhojUAfy54vfE0hIk1634xjJiw2xhR");
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));
        COSClient cosclient = new COSClient(cred, clientConfig);
        String bucketName = "calabash-brothers-eyes-1256400655";
        String key = destPath + fileName;
        File localFile = new File(localPath + fileName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);

        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            String var8 = putObjectResult.getETag();
        } catch (CosServiceException var9) {
            var9.printStackTrace();
        } catch (CosClientException var10) {
            var10.printStackTrace();
        }
        cosclient.shutdown();
    }

    public static void SimpleUploadFileFromStream() {
//        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
//        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
//        COSClient cosclient = new COSClient(cred, clientConfig);
//        String bucketName = "mybucket-1251668577";
//        String key = "/aaa/bbb.jpg";
//        new File("src/test/resources/len10M.txt");
//        InputStream input = new ByteArrayInputStream(new byte[10]);
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(10L);
//        objectMetadata.setContentType("image/jpeg");
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, input, objectMetadata);
//        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
//
//        try {
//            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
//            String var10 = putObjectResult.getETag();
//        } catch (CosServiceException var11) {
//            var11.printStackTrace();
//        } catch (CosClientException var12) {
//            var12.printStackTrace();
//        }
//
//        cosclient.shutdown();
    }

    public static void main(String args[]){
//        SimpleUploadFileDemo s  = new SimpleUploadFileDemo();
//        s.SimpleUploadFileFromLocal();
//        QCloudUpload.pushToBuckets();
        long i = System.currentTimeMillis();
        System.out.println(i);
    }

}
