package cz.cvut.fit.havasiva.service;

import cz.cvut.fit.havasiva.dto.EmployeeCreateDTO;
import cz.cvut.fit.havasiva.dto.EmployeeDTO;
import cz.cvut.fit.havasiva.entity.Employee;
import cz.cvut.fit.havasiva.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    EmployeeDTO employee1 = new EmployeeDTO(1,"John", "Smith", "john@smith.com");
    EmployeeDTO employee2 = new EmployeeDTO(2,"Laura", "Adams", "laura212@gmail.com");
    EmployeeDTO employee6 = new EmployeeDTO(3,"Donald", "McDonald", "donald@mcd.com");
    Employee employee3 = new Employee("Mark", "Twain", "mark@twain.co.nz");
    Employee employee4 = new Employee("Saul", "Goodman", "lawyer@saulgoodman.com");
    Employee employee5 = new Employee("Josh", "Nice", "josh.nice96@gmail.com");

    @Test
    void findAll() {
        List<Employee> employees = Arrays.asList(employee3, employee4, employee5);
        List<EmployeeDTO> employeesDTO = Arrays.asList(
                new EmployeeDTO(employee3.getId(), employee3.getFirstName(), employee3.getLastName(), employee3.getMail()),
                new EmployeeDTO(employee4.getId(), employee4.getFirstName(), employee4.getLastName(), employee4.getMail()),
                new EmployeeDTO(employee5.getId(), employee5.getFirstName(), employee5.getLastName(), employee5.getMail()));

        BDDMockito.given(employeeRepository.findAll()).willReturn(employees);

        Assertions.assertEquals(employeesDTO, employeeService.findAll());

        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void update() throws Exception {
        EmployeeCreateDTO employeeCreateDTO = new EmployeeCreateDTO("Ivan", "Ivanovic", "ivan@ruski.ru");
        EmployeeDTO employeeDTO = new EmployeeDTO(employee5.getId(), employee5.getFirstName(), employee5.getLastName(), employee5.getMail());

        BDDMockito.given(employeeRepository.findById(employee5.getId())).willReturn(Optional.of(employee5));

        employeeService.update(employee5.getId(), employeeCreateDTO);

        Assertions.assertEquals(employeeDTO, employeeService.update(employee5.getId(), employeeCreateDTO));

        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findById(employee5.getId());
    }

    @Test
    void create() {
        EmployeeCreateDTO employeeCreateDTO = new EmployeeCreateDTO("Ivan", "Ivanovic", "ivan@ruski.ru");
        Employee employee = new Employee(employeeCreateDTO.getFirstName(), employeeCreateDTO.getLastName(), employeeCreateDTO.getMail());
        EmployeeDTO employeeDTO = new EmployeeDTO(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getMail());

        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        Assertions.assertEquals(employeeDTO, employeeService.create(employeeCreateDTO));

        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).save(employee);

    }

    @Test
    void deleteById() {
        employeeService.deleteById(employee5.getId());

        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).deleteById(employee5.getId());
    }

    @Test
    void findByIds() {
        List<Employee> employees = Arrays.asList(employee3, employee4, employee5);
        List<Integer> employeesIds = Arrays.asList(employee3.getId(), employee4.getId(), employee5.getId());

        BDDMockito.given(employeeRepository.findAllById(employeesIds)).willReturn(employees);

        Assertions.assertEquals(employees, employeeService.findByIds(employeesIds));

        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findAllById(employeesIds);

    }

    @Test
    void findById() {

        BDDMockito.given(employeeRepository.findById(employee5.getId())).willReturn(Optional.of(employee5));

        Assertions.assertEquals(Optional.of(employee5), employeeService.findById(employee5.getId()));

        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findById(employee5.getId());
    }

    @Test
    void findByIdAsDTO() {
        EmployeeDTO employee5DTO = new EmployeeDTO(employee5.getId(), employee5.getFirstName(), employee5.getLastName(), employee5.getMail());

        BDDMockito.given(employeeRepository.findById(employee5.getId())).willReturn(Optional.of(employee5));

        Assertions.assertEquals(Optional.of(employee5DTO), employeeService.findByIdAsDTO(employee5.getId()));

        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findById(employee5.getId());

    }
}