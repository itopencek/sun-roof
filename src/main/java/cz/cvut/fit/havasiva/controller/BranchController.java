package cz.cvut.fit.havasiva.controller;

import cz.cvut.fit.havasiva.dto.BranchCreateDTO;
import cz.cvut.fit.havasiva.dto.BranchDTO;
import cz.cvut.fit.havasiva.service.BranchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class BranchController {
    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/branch/all")
    List<BranchDTO> all() {
        return branchService.findAll();
    }

    @GetMapping("/branch/{id}")
    BranchDTO byId(@PathVariable int id) {
        return branchService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/branch/country/{country}")
    Optional<BranchDTO> byCountry(@PathVariable String country) {
        return branchService.findByCountry(country);
    }

    @PostMapping("/branch")
    BranchDTO save(@RequestBody BranchCreateDTO branch) throws Exception {
        return branchService.create(branch);
    }

    @PutMapping("/branch/{id}")
    BranchDTO save(@PathVariable int id, @RequestBody BranchCreateDTO branch) throws Exception {
        return branchService.update(id, branch);
    }

    @DeleteMapping("/branch/{id}")
    void delete(@PathVariable int id) {
        branchService.deleteById(id);
    }

}
