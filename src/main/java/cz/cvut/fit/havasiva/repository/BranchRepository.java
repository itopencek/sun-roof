package cz.cvut.fit.havasiva.repository;

import cz.cvut.fit.havasiva.dto.BranchDTO;
import cz.cvut.fit.havasiva.dto.EmployeeDTO;
import cz.cvut.fit.havasiva.entity.Branch;
import cz.cvut.fit.havasiva.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

    List<Branch> findByCountry(String country);

    List<Branch> findBranchByEmployees(Optional<Employee> employee);
}
