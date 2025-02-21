package customer.feedback.com.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @CreatedDate
    @Temporal(TemporalType.DATE)
    @Column(name = "CreatedDate", updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "LastModifiedDate")
    private Date lastModifiedDate;
}
