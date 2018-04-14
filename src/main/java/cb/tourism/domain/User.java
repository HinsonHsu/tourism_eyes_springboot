package cb.tourism.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_user")
public class User {
    private Long id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;
}
