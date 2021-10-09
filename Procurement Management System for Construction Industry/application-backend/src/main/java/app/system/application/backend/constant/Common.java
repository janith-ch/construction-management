package app.system.application.backend.constant;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Common {

    private int ONE = 1;
    private int TWO = 2;
    private int THREE = 3;
    private int ZERO = 0;
    private String SYSTEM_JDBC_URL = "system.jdbc.url";
    private String SYSTEM_JDBC_USER = "system.jdbc.user";
    private String SYSTEM_JDBC_PASSWORD = "system.jdbc.pass";
    private String SYSTEM_JDBC_CONNECTION_POOL = "system.jdbc.connectionPool";
    private int POOL_COUNT = 12000;


}
