package vss.jp.net.flywaydemo.infrastructure.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import vss.jp.net.flywaydemo.domain.tasks.Task;

@Entity(immutable = true)
@Table(name = "tasks")
@Getter
@AllArgsConstructor
public class TaskEntity {
  @Id
  @Column(name = "task_id")
  private String taskId;

  @Column(name = "task_code")
  private String taskCode;

  @Column(name = "status")
  private String status;

  @Column(name = "version_no")
  private Long version;

  public Task toTask() {
    return new Task(taskId, taskCode, status, version);
  }

  public static TaskEntity fromTask(Task task) {
    return new TaskEntity(
        task.getTaskId(), task.getTaskCode(), task.getTaskStatus(), task.getVersion());
  }
}
