package customer.feedback.com.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseDto {

    private Date createdDate;
    private Date lastModifiedDate;

}
