package cz.cvut.fit.havasiva.repository;

import cz.cvut.fit.havasiva.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findEmployeesByFirstName(String firstName);
}
