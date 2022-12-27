package vss.jp.net.flywaydemo.domain.tasks;

import java.util.Optional;

public interface TaskRepository {
  Task createTask(Task task);

  Optional<Task> findByTaskCode(String taskCode);
}
