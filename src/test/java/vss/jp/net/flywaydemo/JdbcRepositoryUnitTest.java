package vss.jp.net.flywaydemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.junit.jupiter.api.extension.ExtendWith;
import org.seasar.doma.boot.autoconfigure.DomaAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/** JDBC を使用した Repository のテストクラスを意味する annotation */
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  FlywayTestExecutionListener.class,
  JdbcDataCleanTestExecutionListener.class
})
@TestPropertySource("/application-unittest.properties")
@ExtendWith(SpringExtension.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({
  DomaAutoConfiguration.class,
  DataSourceAutoConfiguration.class,
  JdbcRepositoryTestConfiguration.class,
  FlywayAutoConfiguration.class
})
public @interface JdbcRepositoryUnitTest {}
