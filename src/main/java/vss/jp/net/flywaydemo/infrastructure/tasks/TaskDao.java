package vss.jp.net.flywaydemo.infrastructure.tasks;

import java.util.Optional;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Sql;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;

@Dao
@ConfigAutowireable
public interface TaskDao {
  @Insert
  Result<TaskEntity> create(TaskEntity taskEntity);

  @Sql(
      "SELECT task_id, task_code, status, version_no  FROM tasks WHERE task_code = /*taskCode*/'abc'")
  @Select
  Optional<TaskEntity> findByTaskCode(String taskCode, SelectOptions selectOptions);
}
