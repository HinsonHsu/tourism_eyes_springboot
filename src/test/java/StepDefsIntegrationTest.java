import cb.tourism.util.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;


public class StepDefsIntegrationTest {
    @Autowired
    private TestRestTemplate template;

    private String res;
    @When("^the client calls /strategy$")
    public void the_client_issues_GET_strategy() throws Throwable{
        res = HttpRequest.sendGet("http://www.hinson.xyz/strategy", "spotId=5afa3a9629a8e651d0269718");
//        res =template.getForObject("/hello", String.class);
        System.out.println(res);
    }

    @Then("^the client receives response status code of (\\d+)$")
    public void the_client_receives_response_status_code_of(int arg1) throws Throwable {
        JSONObject object = JSONObject.parseObject(res);
        int status = (Integer)object.get("code");
        System.out.println("status:" + status);
        Assert.assertTrue("错误，正确的返回值为200", status == 400);
    }
}
