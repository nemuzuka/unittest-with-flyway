package vss.jp.net.flywaydemo.infrastructure.tasks;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.stereotype.Repository;
import vss.jp.net.flywaydemo.domain.tasks.Task;
import vss.jp.net.flywaydemo.domain.tasks.TaskRepository;

@RequiredArgsConstructor
@Repository
public class JdbcTaskRepository implements TaskRepository {

  private final TaskDao taskDao;

  @Override
  public Task createTask(Task task) {
    return taskDao.create(TaskEntity.fromTask(task)).getEntity().toTask();
  }

  @Override
  public Optional<Task> findByTaskCode(String taskCode) {
    return taskDao.findByTaskCode(taskCode, SelectOptions.get()).map(TaskEntity::toTask);
  }
}
