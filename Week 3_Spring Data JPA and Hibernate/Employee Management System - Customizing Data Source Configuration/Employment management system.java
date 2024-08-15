package com.example.employeemanagementsystem.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.employeemanagementsystem.repository.employee",
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
@EntityScan("com.example.employeemanagementsystem.entity.employee")
public class PrimaryDataSourceConfig {
}

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.employeemanagementsystem.repository.department",
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
@EntityScan("com.example.employeemanagementsystem.entity.department")
public class SecondaryDataSourceConfig {
}
