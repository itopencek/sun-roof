package cz.cvut.fit.havasiva.controller;

import cz.cvut.fit.havasiva.dto.EmployeeCreateDTO;
import cz.cvut.fit.havasiva.dto.EmployeeDTO;
import cz.cvut.fit.havasiva.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/all")
    List<EmployeeDTO> all() {
        return employeeService.findAll();
    }

    @GetMapping("/employee/{id}")
    EmployeeDTO byId(@PathVariable int id) {
        return employeeService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/employee")
    EmployeeDTO save(@RequestBody EmployeeCreateDTO employee) {
        return employeeService.create(employee);
    }

    @PostMapping("/employee/{id}")
    EmployeeDTO save(@PathVariable int id, @RequestBody EmployeeCreateDTO employee) throws Exception {
        return employeeService.update(id, employee);
    }

}
