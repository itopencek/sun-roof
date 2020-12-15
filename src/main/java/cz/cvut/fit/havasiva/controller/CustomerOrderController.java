package cz.cvut.fit.havasiva.controller;

import cz.cvut.fit.havasiva.dto.CustomerOrderCreateDTO;
import cz.cvut.fit.havasiva.dto.CustomerOrderDTO;
import cz.cvut.fit.havasiva.service.CustomerOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/order")
public class CustomerOrderController {
    private final CustomerOrderService orderService;

    public CustomerOrderController(CustomerOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    List<CustomerOrderDTO> all() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    CustomerOrderDTO byId(@PathVariable int id) {
        return orderService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all/{branchId}")
    List<CustomerOrderDTO> byBranchId(@PathVariable int branchId) {
         try{
            return orderService.findByOrderedFrom(branchId);
        } catch(Exception e) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping("/customer/{madeBy}")
    List<CustomerOrderDTO> byMadeBy(@PathVariable String madeBy) {
        try {
            return orderService.findByMadeBy(madeBy);
        } catch(Exception e) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CustomerOrderDTO save(@RequestBody CustomerOrderCreateDTO order) {
        try {
            return orderService.create(order);
        } catch(Exception e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
    }

    @PutMapping("/{id}")
    CustomerOrderDTO update(@PathVariable int id, @RequestBody CustomerOrderCreateDTO order) {
        try {
            return orderService.update(id, order);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        try {
            orderService.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
