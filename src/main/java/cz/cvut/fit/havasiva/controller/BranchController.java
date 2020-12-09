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
    @ResponseStatus(HttpStatus.CREATED)
    BranchDTO save(@RequestBody BranchCreateDTO branch) {
        try{
            return branchService.create(branch);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    BranchDTO update(@PathVariable int id, @RequestBody BranchCreateDTO branch) {
        try{
            return branchService.update(id, branch);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        try {
            branchService.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
