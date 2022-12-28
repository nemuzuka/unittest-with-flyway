package vss.jp.net.flywaydemo.infrastructure.tasks;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seasar.doma.jdbc.Config;
import org.springframework.beans.factory.annotation.Autowired;
import vss.jp.net.flywaydemo.domain.tasks.Task;

abstract class AbstractJdbcTaskRepositoryTest {

  @Autowired private Config config;

  private JdbcTaskRepository sut;

  @BeforeEach
  void setUp() {
    var taskDao = new TaskDaoImpl(config);
    sut = new JdbcTaskRepository(taskDao);
  }

  @Test
  @FlywayTest(invokeCleanDB = false)
  void testCreateTask() {
    // setup
    var task = Task.fromCreate("taskCode001", "OPEN");

    // exercise
    var actual = sut.createTask(task);

    // verify
    var expected = new Task(task.getTaskId(), task.getTaskCode(), task.getTaskStatus(), 0L);
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @FlywayTest(invokeCleanDB = false)
  void testFindByTaskCode() {
    // setup
    var task = Task.fromCreate("taskCode001", "OPEN");
    var created = sut.createTask(task);

    // exercise
    var actual = sut.findByTaskCode("taskCode001");

    // verify
    assertThat(actual).hasValue(created);
  }

  @Test
  @FlywayTest(invokeCleanDB = false)
  void testFindByTaskCode_NotFound() {
    // exercise
    var actual = sut.findByTaskCode("notfound_001");

    // verify
    assertThat(actual).isEmpty();
  }

  @Test
  @FlywayTest(invokeCleanDB = false, locationsForMigrate = "sqlfixtures/foo")
  void testFindByTaskCode_WothLocation() {
    // exercise
    var actual = sut.findByTaskCode("taskCode_001");

    // verify
    assertThat(actual).isNotNull();
  }

  @Test
  @FlywayTest(invokeCleanDB = false, locationsForMigrate = "sqlfixtures/bar")
  void testFindByTaskCode_WothLocation2() {
    // exercise
    var actual = sut.findByTaskCode("taskCode_101");

    // verify
    assertThat(actual).isNotNull();
  }
}
