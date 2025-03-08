package customer.feedback.com;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import customer.feedback.com.dto.ShadyRequest;

public class AWSHandler implements RequestHandler<ShadyRequest, String> {

    @Override
    public String handleRequest(ShadyRequest shadyRequest, Context context) {
        return shadyRequest.getName();
    }

}
