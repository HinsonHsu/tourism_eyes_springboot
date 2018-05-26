import cb.tourism.util.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.plaf.basic.BasicTreeUI;


public class StepDefsIntegrationTest {
//    @Autowired
//    private TestRestTemplate template;
//
//    private String res;
//    @When("^the client calls /strategy$")
//    public void the_client_issues_GET_strategy() throws Throwable{
//        res = HttpRequest.sendHttpGet("/strategy", "spotId=5afa3a9629a8e651d0269718");
////        res =template.getForObject("/hello", String.class);
//        System.out.println(res);
//    }
//    @Then("^the client receives response status code of (\\d+)$")
//    public void the_client_receives_response_status_code_of(int arg1) throws Throwable {
//        JSONObject object = JSONObject.parseObject(res);
//        int status = (Integer)object.get("code");
//        System.out.println("status:" + status);
//        Assert.assertTrue("错误，正确的返回值为200", status == arg1);
//    }
//    @And("^the client receives response json includes1 (.+)")
//    public void the_client_receives_response_json_includes_place(String arg) throws Throwable {
//        // Write code here that turns the phrase above into concrete actions
//        JSONObject object = JSONObject.parseObject(res);
//        JSONObject data = (JSONObject)object.get("data");
//        JSONObject place = (JSONObject)data.get(arg);
//        Assert.assertFalse("错误，正确json返回值包含route对象", place.isEmpty());
//    }
//    @And("^the client receives response json includes2 (.+)")
//    public void the_client_receives_response_json_includes_routes(String arg) throws Throwable {
//        // Write code here that turns the phrase above into concrete actions
//        JSONObject object = JSONObject.parseObject(res);
//        JSONObject data = (JSONObject)object.get("data");
//        JSONArray routes = (JSONArray)data.get(arg);
//        Assert.assertFalse("错误，正确json返回值包含routes数组", routes.isEmpty());
//    }
//    @And("^the client receives response json includes3 (.+)")
//    public void the_client_receives_response_json_includes_recommend(String arg) throws Throwable {
//        // Write code here that turns the phrase above into concrete actions
//        JSONObject object = JSONObject.parseObject(res);
//        JSONObject data = (JSONObject)object.get("data");
//        JSONArray recommend = (JSONArray)data.get(arg);
//        Assert.assertFalse("错误，正确json返回值包含recommend数组", recommend.isEmpty());
//    }
}
