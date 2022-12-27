package vss.jp.net.flywaydemo.domain.tasks;

import java.util.UUID;
import lombok.Value;

@Value
public class Task {

  String taskId;
  String taskCode;
  String taskStatus;

  long version;

  public static Task fromCreate(String taskCode, String taskStatus) {
    return new Task(UUID.randomUUID().toString(), taskCode, taskStatus, 0);
  }
}
