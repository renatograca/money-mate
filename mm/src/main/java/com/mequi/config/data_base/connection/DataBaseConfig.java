package com.mequi.config.data_base.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DataBaseConfig {
  public static DataSource getDataSource() {
    final var config = new HikariConfig();
    config.setJdbcUrl(System.getenv("db_url"));
    config.setUsername(System.getenv("db_user"));
    config.setPassword(System.getenv("db_pass"));
    config.setDriverClassName("org.postgresql.Driver");
    config.setMaximumPoolSize(10);
    return new HikariDataSource(config);
  }
}
