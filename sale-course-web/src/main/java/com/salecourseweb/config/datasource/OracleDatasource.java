package com.salecourseweb.config.datasource;

import com.salecourseweb.constant.PackageConst;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "dbSecondJpaEntityManagerFactory",
        transactionManagerRef = "dbSecondJpaTransactionManager",
        basePackages = {PackageConst.SALECOURSE_PLUS_PACKAGE_REPOS_REF}
)
public class OracleDatasource {
    //kết nối đến database thứ 2
    @Primary
    @Bean(name = "dbSecondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource-second.hikari")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    //cấu hình hibernate
    @Primary
    @Bean(name = "dbSecondOracleJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa-second.properties")
    public Properties oracleProperties() {
        return new Properties();
    }

    //quản lý các entity để ánh xạ đến CSDL
    @Primary
    @Bean(name = "dbSecondJpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dbFirstJpaEntityManagerFactory(
            @Qualifier("dbSecondOracleJpaProperties") Properties oracleProperties,
            @Qualifier("dbSecondDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean firstFactory = new LocalContainerEntityManagerFactoryBean();
        firstFactory.setDataSource(dataSource);
        firstFactory.setPackagesToScan(PackageConst.SALECOURSE_PLUS_PACKAGE_ENTITY_REF);
        firstFactory.setPersistenceUnitName(PackageConst.SALECOURSE_PLUS_DATABASE_UN);
        firstFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        firstFactory.setJpaProperties(oracleProperties);
        return firstFactory;
    }

    //truy vấn nhanh không ánh xạ đến object
    @Primary
    @Bean(name = "dbSecondOracleJdbcTemplate")
    public JdbcTemplate jdbcFirstTemplate(@Qualifier("dbSecondDataSource") DataSource oracleDataSource) {
        return new JdbcTemplate(oracleDataSource);
    }

    //dùng để truy vấn dễ đọc hơn với các tham số
    @Primary
    @Bean(name = "dbSecondOracleNamedJdbcTemplate")
    public NamedParameterJdbcTemplate jdbcFirstNamedTemplate(@Qualifier("dbSecondDataSource") DataSource oracleDataSource) {
        return new NamedParameterJdbcTemplate(oracleDataSource);
    }

    //quản lý các begin/commit/rollback khi thao thác với jpa
    @Primary
    @Bean(name = "dbSecondJpaTransactionManager")
    public PlatformTransactionManager dbFirstJpaTransactionManager(
            @Qualifier("dbSecondJpaEntityManagerFactory") EntityManagerFactory db1EntityManagerFactory) {
        return new JpaTransactionManager(db1EntityManagerFactory);
    }
}
