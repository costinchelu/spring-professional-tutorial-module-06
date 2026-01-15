package module06.question05.dao;

import module06.question05.ds.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeesDao extends CrudRepository<Employee, Integer> {
}
