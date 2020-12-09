package cz.cvut.fit.havasiva.controller;

import cz.cvut.fit.havasiva.dto.EmployeeCreateDTO;
import cz.cvut.fit.havasiva.dto.EmployeeDTO;
import cz.cvut.fit.havasiva.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    List<EmployeeDTO> all() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    EmployeeDTO byId(@PathVariable int id) {
        return employeeService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EmployeeDTO save(@RequestBody EmployeeCreateDTO employee) {
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    EmployeeDTO update(@PathVariable int id, @RequestBody EmployeeCreateDTO employee) {
        try {
            return employeeService.update(id, employee);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        try {
            employeeService.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}
