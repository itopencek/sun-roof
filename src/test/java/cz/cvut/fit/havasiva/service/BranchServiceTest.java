package cz.cvut.fit.havasiva.service;

import cz.cvut.fit.havasiva.dto.BranchCreateDTO;
import cz.cvut.fit.havasiva.dto.BranchDTO;
import cz.cvut.fit.havasiva.entity.Branch;
import cz.cvut.fit.havasiva.entity.Employee;
import cz.cvut.fit.havasiva.repository.BranchRepository;
import cz.cvut.fit.havasiva.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class BranchServiceTest {

    @Autowired
    private BranchService branchService;
    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private BranchRepository branchRepository;
    @MockBean
    private EmployeeRepository employeeRepository;

    Employee employee4 = new Employee("Saul", "Goodman", "lawyer@saulgoodman.com");
    Employee employee5 = new Employee("Josh", "Nice", "josh.nice96@gmail.com");
    List<Employee> employees = Arrays.asList(employee4, employee5);
    List<Employee> emptyList = Arrays.asList();
    List<Integer> employeeIds = Arrays.asList(employee4.getId(), employee5.getId());
    List<Integer> emptyList2 = Arrays.asList();

    Branch branch = new Branch("SVK", 200, true, employees);
    Branch emptyBranch = new Branch("CZ", 345, false, emptyList);

    @Test
    void findAll() {
        List<Branch> branches = Arrays.asList(branch, emptyBranch);
        List<Integer> branchIds = Arrays.asList(branch.getId(), emptyBranch.getId());
        List<BranchDTO> branchDTOs = Arrays.asList(
                new BranchDTO(branch.getId(), branch.getCountry(), branch.isWebStore(), branch.getYearlyProfit(), employeeIds),
                new BranchDTO(emptyBranch.getId(), emptyBranch.getCountry(), emptyBranch.isWebStore(), emptyBranch.getYearlyProfit(), emptyList2)
        );

        BDDMockito.given(branchRepository.findAll()).willReturn(branches);

        Assertions.assertEquals(branchDTOs, branchService.findAll());

        Mockito.verify(branchRepository, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void update() throws Exception {
        BranchCreateDTO branchCreateDTO = new BranchCreateDTO("SVK", false, 420, employeeIds);
        BranchDTO branchDTO = new BranchDTO(branch.getId(), branch.getCountry(), branch.isWebStore(), branch.getYearlyProfit(), employeeIds);


        BDDMockito.given(employeeRepository.findAllById(branchCreateDTO.getEmployeeIds())).willReturn(employees);
        BDDMockito.given(branchRepository.findById(branch.getId())).willReturn(Optional.of(branch));

        Assertions.assertEquals(branchDTO, branchService.update(branch.getId(), branchCreateDTO));

        Mockito.verify(branchRepository, Mockito.atLeastOnce()).findById(branch.getId());
        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findAllById(branchCreateDTO.getEmployeeIds());
    }

    @Test
    void create() throws Exception {
        BranchCreateDTO branchCreateDTO = new BranchCreateDTO("SVK", false, 420, employeeIds);
        BranchDTO branchDTO = new BranchDTO(branch.getId(), branch.getCountry(), branch.isWebStore(), branch.getYearlyProfit(), employeeIds);


        BDDMockito.given(employeeRepository.findAllById(branchCreateDTO.getEmployeeIds())).willReturn(employees);
        BDDMockito.given(branchRepository.save(new Branch(
                branchCreateDTO.getCountry(),
                branchCreateDTO.getYearlyProfit(),
                branchCreateDTO.isWebStore(),
                employees
        ))).willReturn(new Branch(
                branchCreateDTO.getCountry(),
                branchCreateDTO.getYearlyProfit(),
                branchCreateDTO.isWebStore(),
                employees
        ));

        Assertions.assertEquals(branchDTO, branchService.create(branchCreateDTO));

        Mockito.verify(branchRepository, Mockito.atLeastOnce()).save(new Branch(
                branchCreateDTO.getCountry(),
                branchCreateDTO.getYearlyProfit(),
                branchCreateDTO.isWebStore(),
                employees
        ));
        Mockito.verify(employeeRepository, Mockito.atLeastOnce()).findAllById(branchCreateDTO.getEmployeeIds());
    }

    @Test
    void deleteById() throws Exception{
        BDDMockito.given(branchRepository.findById(branch.getId())).willReturn(Optional.of(branch));

        branchService.deleteById(branch.getId());

        Mockito.verify(branchRepository, Mockito.atLeastOnce()).deleteById(branch.getId());
    }

    @Test
    void findByIds() {
        List<Integer> branchIds = Arrays.asList(branch.getId());
        List<Branch> branches = Arrays.asList(branch);
        BDDMockito.given(branchRepository.findAllById(branchIds)).willReturn(branches);

        Assertions.assertEquals(branches, branchService.findByIds(branchIds));

        Mockito.verify(branchRepository, Mockito.atLeastOnce()).findAllById(branchIds);
    }

    @Test
    void findById() {
        BDDMockito.given(branchRepository.findById(branch.getId())).willReturn(Optional.of(branch));

        Assertions.assertEquals(Optional.of(branch), branchService.findById(branch.getId()));

        Mockito.verify(branchRepository, Mockito.atLeastOnce()).findById(branch.getId());
    }

    @Test
    void findByIdAsDTO() {
        BranchDTO branchDTO = new BranchDTO(branch.getId(), branch.getCountry(), branch.isWebStore(), branch.getYearlyProfit(), employeeIds);

        BDDMockito.given(branchRepository.findById(branch.getId())).willReturn(Optional.of(branch));

        Assertions.assertEquals(Optional.of(branchDTO), branchService.findByIdAsDTO(branch.getId()));

        Mockito.verify(branchRepository, Mockito.atLeastOnce()).findById(branch.getId());
    }

    @Test
    void findByCountry() {
        List<Branch> branches = Arrays.asList(branch);
        BranchDTO branchDTO = new BranchDTO(branch.getId(), branch.getCountry(), branch.isWebStore(), branch.getYearlyProfit(), employeeIds);
        List<BranchDTO> branchDTOs = Arrays.asList(branchDTO);

        BDDMockito.given(branchRepository.findByCountry(branch.getCountry())).willReturn(branches);

        Assertions.assertEquals(branchDTOs, branchService.findByCountry(branch.getCountry()));

        Mockito.verify(branchRepository, Mockito.atLeastOnce()).findByCountry(branch.getCountry());
    }
}