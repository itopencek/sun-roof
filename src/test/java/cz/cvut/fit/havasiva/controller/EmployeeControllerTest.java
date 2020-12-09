package cz.cvut.fit.havasiva.controller;

import cz.cvut.fit.havasiva.dto.EmployeeCreateDTO;
import cz.cvut.fit.havasiva.dto.EmployeeDTO;
import cz.cvut.fit.havasiva.entity.Employee;
import cz.cvut.fit.havasiva.repository.EmployeeRepository;
import cz.cvut.fit.havasiva.service.EmployeeService;
import org.checkerframework.checker.nullness.Opt;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    EmployeeDTO employee1 = new EmployeeDTO(1,"John", "Smith", "john@smith.com");
    EmployeeDTO employee2 = new EmployeeDTO(2,"Laura", "Adams", "laura212@gmail.com");
    EmployeeDTO employee3 = new EmployeeDTO(3,"Donald", "McDonald", "donald@mcd.com");

    @Test
    void all() throws Exception {
        List<EmployeeDTO> employeesDTO = Arrays.asList(employee1, employee2, employee3);

        BDDMockito.given(employeeService.findAll()).willReturn(employeesDTO);

        mockMvc.perform(
                MockMvcRequestBuilders
                .get("/employee/all")
                .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(employee1.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is(employee1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", CoreMatchers.is(employee1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mail", CoreMatchers.is(employee1.getMail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", CoreMatchers.is(employee2.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", CoreMatchers.is(employee2.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", CoreMatchers.is(employee2.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mail", CoreMatchers.is(employee2.getMail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", CoreMatchers.is(employee3.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].firstName", CoreMatchers.is(employee3.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].lastName", CoreMatchers.is(employee3.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].mail", CoreMatchers.is(employee3.getMail())));

        BDDMockito.verify(employeeService, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void byId() throws Exception{
        BDDMockito.given(employeeService.findByIdAsDTO(employee2.getId())).willReturn(Optional.of(employee2));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/employee/{id}", employee2.getId())
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(employee2.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee2.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee2.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail", CoreMatchers.is(employee2.getMail())));

        BDDMockito.verify(employeeService, Mockito.atLeastOnce()).findByIdAsDTO(employee2.getId());
    }

    @Test
    void save() throws Exception{
        EmployeeCreateDTO employeeCreateDTO = new EmployeeCreateDTO(employee1.getFirstName(), employee1.getLastName(), employee1.getMail());
        BDDMockito.given(employeeService.create(employeeCreateDTO)).willReturn(employee1);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/employee")
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
                        .content("{\"firstName\": \"" + employee1.getFirstName() + "\", \"lastName\": \"" + employee1.getLastName() + "\", \"mail\": \"" + employee1.getMail() + "\"}")
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        BDDMockito.verify(employeeService, Mockito.atLeastOnce()).create(employeeCreateDTO);
    }

    @Test
    void update() throws Exception {
        EmployeeCreateDTO employeeCreateDTO = new EmployeeCreateDTO(employee1.getFirstName(), employee1.getLastName(), employee1.getMail());
        BDDMockito.given(employeeService.update(employee1.getId(), employeeCreateDTO)).willReturn(employee1);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/employee/{id}", employee1.getId())
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
                        .content("{\"firstName\": \"" + employee1.getFirstName() + "\", \"lastName\": \"" + employee1.getLastName() + "\", \"mail\": \"" + employee1.getMail() + "\"}")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(employee1.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail", CoreMatchers.is(employee1.getMail())));

        BDDMockito.verify(employeeService, Mockito.atLeastOnce()).update(employee1.getId(), employeeCreateDTO);
    }

    @Test
    void delete() throws Exception {
        BDDMockito.doNothing().when(employeeService).deleteById(employee1.getId());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/employee/{id}", employee1.getId())
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.verify(employeeService, Mockito.atLeastOnce()).deleteById(employee1.getId());
    }

}