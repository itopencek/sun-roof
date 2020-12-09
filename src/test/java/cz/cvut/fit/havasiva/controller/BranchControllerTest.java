package cz.cvut.fit.havasiva.controller;

import cz.cvut.fit.havasiva.dto.BranchCreateDTO;
import cz.cvut.fit.havasiva.dto.BranchDTO;
import cz.cvut.fit.havasiva.entity.Branch;
import cz.cvut.fit.havasiva.entity.Employee;
import cz.cvut.fit.havasiva.service.BranchService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BranchService branchService;

    Employee employee4 = new Employee("Saul", "Goodman", "lawyer@saulgoodman.com");
    Employee employee5 = new Employee("Josh", "Nice", "josh.nice96@gmail.com");
    List<Employee> employees = Arrays.asList(employee4, employee5);
    List<Employee> emptyList = Arrays.asList();
    List<Integer> employeeIds = Arrays.asList(employee4.getId(), employee5.getId());
    List<Integer> emptyList2 = Arrays.asList();

    BranchDTO branchDTO = new BranchDTO(1, "SVK", true, 200, employeeIds);
    BranchDTO emptyBranchDTO = new BranchDTO(2,"CZ", false, 345, emptyList2);

    @Test
    void all() throws Exception {
        List<BranchDTO> branchesDTO = Arrays.asList(branchDTO, emptyBranchDTO);

        BDDMockito.given(branchService.findAll()).willReturn(branchesDTO);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/branch/all")
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(branchDTO.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].country", CoreMatchers.is(branchDTO.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].webStore", CoreMatchers.is(branchDTO.isWebStore())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].yearlyProfit", CoreMatchers.is(branchDTO.getYearlyProfit())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeIds", CoreMatchers.is(branchDTO.getEmployeeIds())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", CoreMatchers.is(emptyBranchDTO.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].country", CoreMatchers.is(emptyBranchDTO.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].webStore", CoreMatchers.is(emptyBranchDTO.isWebStore())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].yearlyProfit", CoreMatchers.is(emptyBranchDTO.getYearlyProfit())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employeeIds", CoreMatchers.is(emptyBranchDTO.getEmployeeIds())));

        BDDMockito.verify(branchService, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void byId() throws Exception {
        BDDMockito.given(branchService.findByIdAsDTO(branchDTO.getId())).willReturn(Optional.of(branchDTO));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/branch/{id}", branchDTO.getId())
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(branchDTO.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(branchDTO.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.webStore", CoreMatchers.is(branchDTO.isWebStore())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.yearlyProfit", CoreMatchers.is(branchDTO.getYearlyProfit())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeIds", CoreMatchers.is(branchDTO.getEmployeeIds())));

        BDDMockito.verify(branchService, Mockito.atLeastOnce()).findByIdAsDTO(branchDTO.getId());
    }

    @Test
    void byCountry() throws Exception {
        List<BranchDTO> branches = Arrays.asList(branchDTO);
        BDDMockito.given(branchService.findByCountry(branchDTO.getCountry())).willReturn(branches);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/branch/country/{country}", branchDTO.getCountry())
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(branchDTO.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].country", CoreMatchers.is(branchDTO.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].webStore", CoreMatchers.is(branchDTO.isWebStore())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].yearlyProfit", CoreMatchers.is(branchDTO.getYearlyProfit())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeIds", CoreMatchers.is(branchDTO.getEmployeeIds())));

        BDDMockito.verify(branchService, Mockito.atLeastOnce()).findByCountry(branchDTO.getCountry());
    }

    @Test
    void save() throws Exception {
        BranchCreateDTO branchCreateDTO = new BranchCreateDTO(branchDTO.getCountry(), branchDTO.isWebStore(), branchDTO.getYearlyProfit(), branchDTO.getEmployeeIds());
        BDDMockito.given(branchService.create(branchCreateDTO)).willReturn(branchDTO);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/branch")
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
                        .content("{\"country\": \"" + branchDTO.getCountry()
                                + "\", \"isWebStore\": \"" + branchDTO.isWebStore()
                                + "\", \"yearlyProfit\": \"" + branchDTO.getYearlyProfit()
                                + "\", \"employeeIds\": " + branchDTO.getEmployeeIds()
                                +"}")
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        BDDMockito.verify(branchService, Mockito.atLeastOnce()).create(branchCreateDTO);
    }

    @Test
    void update() throws Exception {
        BranchCreateDTO branchCreateDTO = new BranchCreateDTO(branchDTO.getCountry(), branchDTO.isWebStore(), branchDTO.getYearlyProfit(), branchDTO.getEmployeeIds());
        BDDMockito.given(branchService.update(branchDTO.getId(), branchCreateDTO)).willReturn(branchDTO);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/branch/{id}", branchDTO.getId())
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
                        .content("{\"country\": \"" + branchDTO.getCountry()
                                + "\", \"isWebStore\": \"" + branchDTO.isWebStore()
                                + "\", \"yearlyProfit\": \"" + branchDTO.getYearlyProfit()
                                + "\", \"employeeIds\": " + branchDTO.getEmployeeIds()
                                +"}")
        ).andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.verify(branchService, Mockito.atLeastOnce()).update(branchDTO.getId(), branchCreateDTO);
    }

    @Test
    void delete() throws Exception {
        BDDMockito.doNothing().when(branchService).deleteById(branchDTO.getId());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/branch/{id}", branchDTO.getId())
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.verify(branchService, Mockito.atLeastOnce()).deleteById(branchDTO.getId());
    }
}