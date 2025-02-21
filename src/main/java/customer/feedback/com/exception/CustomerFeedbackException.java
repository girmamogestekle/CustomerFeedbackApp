package customer.feedback.com.exception;

import customer.feedback.com.dto.ErrorDetailsDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomerFeedbackException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleCustomerFeedbackException(Exception exception, HttpServletRequest httpServletRequest){
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), exception.getMessage(), httpServletRequest.getRequestURL().toString());
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, HttpServletRequest httpServletRequest){
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto(new Date(), HttpStatus.NOT_FOUND.toString(), resourceNotFoundException.getMessage(), httpServletRequest.getRequestURL().toString());
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.NOT_FOUND);
    }

}
