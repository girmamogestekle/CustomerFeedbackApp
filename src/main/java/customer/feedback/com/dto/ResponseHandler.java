package customer.feedback.com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus httpStatus, Object returnObject){

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("message", message);
        returnMap.put("status", httpStatus);
        returnMap.put("data", returnObject);
        return new ResponseEntity<>(returnMap, httpStatus);

    }

}
