package module06.question04.dao;

import module06.question04.ds.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentsDao extends CrudRepository<Department, Integer> {
}
