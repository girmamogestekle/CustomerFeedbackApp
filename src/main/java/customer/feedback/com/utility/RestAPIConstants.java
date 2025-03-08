package customer.feedback.com.utility;

public class RestAPIConstants {

    // Authentication APIs
    public static final String AUTHENTICATION = "/api/v1/authenticate";
    public static final String AUTHENTICATION_LOGIN = AUTHENTICATION + "/login";

    // User APIs
    public static final String CUSTOMER = "/api/v1/customer";
    public static final String REGISTER_CUSTOMER = CUSTOMER + "/register";
    public static final String GET_CUSTOMER = CUSTOMER + "/get";


    // Testing APIs
    public static final String TESTING = "/api/v1/tester";
    public static final String GET_TESTING = TESTING + "/get";

}
