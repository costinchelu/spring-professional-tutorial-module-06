package module06.question04.dao;

import module06.question04.ds.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeesDao extends CrudRepository<Employee, Integer> {
}
