package cz.cvut.fit.havasiva.service;

import cz.cvut.fit.havasiva.dto.EmployeeCreateDTO;
import cz.cvut.fit.havasiva.dto.EmployeeDTO;
import cz.cvut.fit.havasiva.entity.Employee;
import cz.cvut.fit.havasiva.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDTO> findAll() {
        return employeeRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // either use @Transactional or as in create() use .save()
    @Transactional
    public EmployeeDTO update(int id, EmployeeCreateDTO employeeCreateDTO) throws Exception {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isEmpty())
                throw new Exception("No employee found!");
        Employee employee = optionalEmployee.get();
        employee.setFirstName(employeeCreateDTO.getFirstName());
        employee.setLastName(employeeCreateDTO.getLastName());
        employee.setMail(employeeCreateDTO.getMail());

        return toDTO(employee);
    }

    public EmployeeDTO create(EmployeeCreateDTO employeeCreateDTO) {
        return toDTO(
            employeeRepository.save(
                    new Employee(employeeCreateDTO.getFirstName(), employeeCreateDTO.getLastName(), employeeCreateDTO.getMail())
            )
        );
    }

    public List<Employee> findByIds(List<Integer> ids) {
        return employeeRepository.findAllById(ids);
    }

    public Optional<Employee> findById(int id) {
        return employeeRepository.findById(id);
    }

    public Optional<EmployeeDTO> findByIdAsDTO(int id) {
        return toDTO(employeeRepository.findById(id));
    }

    private EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getMail());
    }

    private Optional<EmployeeDTO> toDTO(Optional<Employee> optionalEmployee) {
        if(optionalEmployee.isEmpty())
                return Optional.empty();
        return Optional.of(toDTO(optionalEmployee.get()));
    }

}
