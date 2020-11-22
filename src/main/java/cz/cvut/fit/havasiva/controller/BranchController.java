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
@RequestMapping("/branch")
public class BranchController {
    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/all")
    List<BranchDTO> all() {
        return branchService.findAll();
    }

    @GetMapping("/{id}")
    BranchDTO byId(@PathVariable int id) {
        return branchService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/country/{country}")
    List<BranchDTO> byCountry(@PathVariable String country) {
        return branchService.findByCountry(country);
    }

    @PostMapping
    BranchDTO save(@RequestBody BranchCreateDTO branch) throws Exception {
        return branchService.create(branch);
    }

    @PutMapping("/{id}")
    BranchDTO save(@PathVariable int id, @RequestBody BranchCreateDTO branch) throws Exception {
        return branchService.update(id, branch);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        branchService.deleteById(id);
    }

}
