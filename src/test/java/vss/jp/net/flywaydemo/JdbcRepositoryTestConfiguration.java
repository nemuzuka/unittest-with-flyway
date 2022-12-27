package vss.jp.net.flywaydemo;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@TestConfiguration
public class JdbcRepositoryTestConfiguration {

  private final DataSource dataSource;

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource);
  }
}
