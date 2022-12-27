package vss.jp.net.flywaydemo;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * 現在登録している database 内の table に対して truncate をする TestExecutionListener.
 *
 * <p>MySQL 専用です
 */
@Slf4j
public class JdbcDataCleanTestExecutionListener extends AbstractTestExecutionListener
    implements TestExecutionListener {

  @Override
  public void afterTestMethod(final TestContext testContext) throws Exception {
    var jdbcTemplate = buildJdbcTemplate(testContext);
    var schemaName = getSchemaName(jdbcTemplate);
    var tableNames = getTableNames(jdbcTemplate, schemaName);
    cleanTable(jdbcTemplate, tableNames);
  }

  private void cleanTable(JdbcTemplate jdbcTemplate, List<String> tableNames) throws SQLException {
    jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");
    tableNames.forEach(
        tableName -> {
          try {
            truncateOrDeleteTable(jdbcTemplate, tableName);
          } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
          }
        });
    jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");
    jdbcTemplate.getConnection().close();
  }

  private void truncateOrDeleteTable(JdbcTemplate jdbcTemplate, String tableName)
      throws SQLException {

    if (isHistoryTable(tableName)) {
      deleteHostoryRecord(jdbcTemplate, tableName);
    } else {
      truncateTable(jdbcTemplate, tableName);
    }
  }

  private void deleteHostoryRecord(JdbcTemplate jdbcTemplate, String tableName)
      throws SQLException {
    jdbcTemplate.execute(
        "DELETE FROM `" + tableName + "` WHERE installed_rank > ?", getDeleteFromVersion());
  }

  private void truncateTable(JdbcTemplate jdbcTemplate, String tableName) throws SQLException {
    jdbcTemplate.execute("TRUNCATE TABLE `" + tableName + "`");
  }

  protected boolean isHistoryTable(String tableName) {
    return Objects.equals(tableName, "flyway_schema_history");
  }

  protected int getDeleteFromVersion() {
    return 1000;
  }

  private List<String> getTableNames(JdbcTemplate jdbcTemplate, String schemaName)
      throws SQLException {
    return jdbcTemplate.queryForStringList(
        "SELECT table_name FROM information_schema.tables WHERE table_schema=?"
            + " AND table_type IN ('BASE TABLE', 'SYSTEM VERSIONED')",
        schemaName);
  }

  private String getSchemaName(JdbcTemplate jdbcTemplate) throws SQLException {
    return jdbcTemplate.queryForString("SELECT DATABASE()");
  }

  private JdbcTemplate buildJdbcTemplate(TestContext testContext) throws SQLException {
    var dataSource = testContext.getApplicationContext().getBean(DataSource.class);
    return new JdbcTemplate(dataSource.getConnection());
  }
}
