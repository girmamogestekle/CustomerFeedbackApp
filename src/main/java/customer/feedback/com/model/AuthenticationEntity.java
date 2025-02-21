package customer.feedback.com.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Authentication")
public class AuthenticationEntity {

    @Id
    @Column(name = "Username", unique = true, nullable = false)
    @Setter(AccessLevel.NONE)
    private String username;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastLoginDate", nullable = false)
    private Date lastLoginDate;

}
