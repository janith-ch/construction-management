package app.system.application.backend.configuration;


import app.system.application.backend.constant.Common;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
public class DbConfig {

    @Autowired
    private Environment env;

    @Autowired
    private Common common;

    @Bean(name = "system-jdbc-template")
    public JdbcTemplate jdbcTemplate(@Qualifier("system-datasource") DataSource dataSource) throws PropertyVetoException {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "system-transaction-manager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("system-datasource") DataSource dataSource) throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "system-datasource")
    public DataSource popsaxonyDataSource() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty(common.getSYSTEM_JDBC_URL()));
        config.setUsername(env.getProperty(common.getSYSTEM_JDBC_USER()));
        config.setPassword(env.getProperty(common.getSYSTEM_JDBC_PASSWORD()));
        config.setMaximumPoolSize(Integer.parseInt(env.getProperty(common.getSYSTEM_JDBC_CONNECTION_POOL())));
        config.setLeakDetectionThreshold(common.getPOOL_COUNT());
        return new HikariDataSource(config);
    }

    @Bean
    @ConditionalOnMissingBean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Logger logger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}


