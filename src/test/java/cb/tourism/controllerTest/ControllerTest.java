package cb.tourism.controllerTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/** TestRestTemplate 需要运行在web 项目中 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testController(){
        // template.getForObject() 会得到controller 返回的json 值
        String content = template.getForObject("/hello", String.class);
        // 使用断言测试，使用正确的断言
        System.out.println("testController:" + content);
        Assert.assertEquals("Hello World", content);
    }
}
