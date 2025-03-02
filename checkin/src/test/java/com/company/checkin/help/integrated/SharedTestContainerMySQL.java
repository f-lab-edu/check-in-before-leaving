package com.company.checkin.help.integrated;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MySQLContainer;

import java.sql.*;

public class SharedTestContainerMySQL {
    // 싱글톤 패턴 적용
    private static SharedTestContainerMySQL instance;
    private final MySQLContainer<?> mySqlContainer;

    private SharedTestContainerMySQL() {
        mySqlContainer = new MySQLContainer<>("mysql:8.0")
                .withUsername("test")
                .withPassword("test");

        mySqlContainer.start();
        grantDatabaseCreationPrivileges(mySqlContainer);
    }

    private void grantDatabaseCreationPrivileges(MySQLContainer<?> mySqlContainer) {
        try (Connection conn = DriverManager.getConnection(
                mySqlContainer.getJdbcUrl(),
                "root", "test")) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("GRANT ALL PRIVILEGES ON *.* TO 'test'@'%'");

            } catch (SQLException e) {
                throw new RuntimeException("권한 쿼리 실행 중 오류 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("권한 설정 중 오류 발생", e);
        }
    }

    public static synchronized SharedTestContainerMySQL getInstance() {
        if (instance == null) {
            instance = new SharedTestContainerMySQL();
        }
        return instance;
    }

    public MySQLContainer<?> getMySqlContainer() {
        return mySqlContainer;
    }

    public String getJdbcUrl() {
        return mySqlContainer.getJdbcUrl();
    }

    public String getUsername() {
        return mySqlContainer.getUsername();
    }

    public String getPassword() {
        return mySqlContainer.getPassword();
    }

    //스키마 셋업
    public static void setUpSchema(DynamicPropertyRegistry registry, String SCHEMA_NAME) {
        SharedTestContainerMySQL container = getInstance();
        container.createSchema(container, SCHEMA_NAME);
        container.setConnectionForNewSchema(container, registry, SCHEMA_NAME);
    }

    private void createSchema(SharedTestContainerMySQL container, String schema_name) {
        try (Connection conn = DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DROP SCHEMA IF EXISTS " + schema_name);
                stmt.execute("CREATE SCHEMA " + schema_name);
            } catch (SQLException e) {
                throw new RuntimeException("스키마 쿼리 실행 중 오류 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("스키마 생성 중 오류 발생", e);
        }
    }

    private void setConnectionForNewSchema(SharedTestContainerMySQL container, DynamicPropertyRegistry registry, String SCHEMA_NAME) {
        registry.add("spring.datasource.url", () ->
                container.getJdbcUrl().replace("/test", "/" + SCHEMA_NAME));
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.jpa.properties.hibernate.default_schema", () -> SCHEMA_NAME);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }


    public void removeSchema(SharedTestContainerMySQL container, String schemaName) {
        try (Connection conn = DriverManager.getConnection(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword())) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DROP SCHEMA IF EXISTS " + schemaName);
            } catch (SQLException e) {
                throw new RuntimeException("스키마 삭제 실행 중 오류 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("스키마 삭제 중 오류 발생", e);
        }
    }
}
