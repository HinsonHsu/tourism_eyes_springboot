package cb.tourism.service;

import cb.tourism.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class WXService {
    public JSONObject getOpenIdAndSenssion_keyByCode(String code){
        System.out.println("code: " + code);
        String WX_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        JSONObject jsonObject = null;
        try {
            String requestUrl = WX_URL.replace("APPID", "wx32672e87f03da727").
                    replace("SECRET", "601debbb04689794fcd84080e4098bf2").replace("JSCODE", code).
                    replace("authorization_code", "authorization_code");
            // 发起GET请求获取凭证
            jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
            System.out.println(jsonObject.toJSONString());
        } catch (Exception e) {
            System.out.println(e);
        }
        return jsonObject;
    }
}
