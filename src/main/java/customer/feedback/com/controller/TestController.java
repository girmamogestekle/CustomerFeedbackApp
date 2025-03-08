package customer.feedback.com.controller;

import customer.feedback.com.utility.RestAPIConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = RestAPIConstants.GET_TESTING)
    public String getTestMessage(){
        return "Test was Successfully Invoked!!!";
    }

}
