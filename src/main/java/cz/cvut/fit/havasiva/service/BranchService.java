package cz.cvut.fit.havasiva.service;

import cz.cvut.fit.havasiva.dto.BranchCreateDTO;
import cz.cvut.fit.havasiva.dto.BranchDTO;
import cz.cvut.fit.havasiva.dto.EmployeeDTO;
import cz.cvut.fit.havasiva.entity.Branch;
import cz.cvut.fit.havasiva.entity.Employee;
import cz.cvut.fit.havasiva.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BranchService {

    private final BranchRepository branchRepository;
    private final EmployeeService employeeService;

    @Autowired
    public BranchService(BranchRepository branchRepository, EmployeeService employeeService) {
        this.branchRepository = branchRepository;
        this.employeeService = employeeService;
    }

    public List<BranchDTO> findAll() {
        return branchRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<BranchDTO> findByEmployeeId(int id) {
        Optional<Employee> employee = employeeService.findById(id);

        return branchRepository.findBranchByEmployees(employee).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public BranchDTO update(int id, BranchCreateDTO branchCreateDTO) throws Exception {
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if(optionalBranch.isEmpty())
                throw new Exception("No branch found!");

        List<Employee> employees = employeeService.findByIds(branchCreateDTO.getEmployeeIds());
        if(employees.size() != branchCreateDTO.getEmployeeIds().size())
            throw new Exception("Some employees were not found");

        Branch branch = optionalBranch.get();
        branch.setCountry(branchCreateDTO.getCountry());
        branch.setYearlyProfit(branchCreateDTO.getYearlyProfit());
        branch.setWebStore(branchCreateDTO.isWebStore());
        branch.setEmployees(employees);

        return toDTO(branch);
    }

    public BranchDTO create(BranchCreateDTO branchCreateDTO) throws Exception {
        List<Employee> employees = employeeService.findByIds(branchCreateDTO.getEmployeeIds());
        if(employees.size() != branchCreateDTO.getEmployeeIds().size())
            throw new Exception("Some employees were not found");

        return toDTO(
            branchRepository.save(
                    new Branch(branchCreateDTO.getCountry(), branchCreateDTO.getYearlyProfit(), branchCreateDTO.isWebStore(), employees)
            )
        );
    }

    public void deleteById(int id) throws Exception {
        Optional<Branch> optionalBranch = branchRepository.findById(id);
        if(optionalBranch.isEmpty())
            throw new Exception("Branch not found!");
        branchRepository.deleteById(id);
    }

    public List<Branch> findByIds(List<Integer> ids) {
        return branchRepository.findAllById(ids);
    }

    public Optional<Branch> findById(int id) {
        return branchRepository.findById(id);
    }

    public Optional<BranchDTO> findByIdAsDTO(int id) {
        return toDTO(branchRepository.findById(id));
    }

    public List<BranchDTO> findByCountry(String country) {
        return branchRepository.findByCountry(country).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private BranchDTO toDTO(Branch branch) {
        return new BranchDTO(branch.getId(),
                branch.getCountry(),
                branch.isWebStore(),
                branch.getYearlyProfit(),
                branch.getEmployees()
                        .stream()
                        .map(Employee::getId)
                        .collect(Collectors.toList())
        );
    }

    private Optional<BranchDTO> toDTO(Optional<Branch> optionalBranch) {
        if(optionalBranch.isEmpty())
                return Optional.empty();
        return Optional.of(toDTO(optionalBranch.get()));
    }

}
