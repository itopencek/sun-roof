package cz.cvut.fit.havasiva.controller;

import cz.cvut.fit.havasiva.dto.CustomerOrderCreateDTO;
import cz.cvut.fit.havasiva.dto.CustomerOrderDTO;
import cz.cvut.fit.havasiva.service.CustomerOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CustomerOrderController {
    private final CustomerOrderService orderService;

    public CustomerOrderController(CustomerOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/all")
    List<CustomerOrderDTO> all() {
        return orderService.findAll();
    }

    @GetMapping("/order/{id}")
    CustomerOrderDTO byId(@PathVariable int id) {
        return orderService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/order/all/{branchId}")
    List<CustomerOrderDTO> byBranchId(@PathVariable int branchId) {
        return orderService.findByOrderedFrom(branchId);
    }

    @GetMapping("/order/{madeBy}")
    CustomerOrderDTO byMadeBy(@PathVariable String madeBy) {
        return orderService.findByMadeBy(madeBy).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/order")
    CustomerOrderDTO save(@RequestBody CustomerOrderCreateDTO order) throws Exception {
        return orderService.create(order);
    }

    @PostMapping("/order/{id}")
    CustomerOrderDTO save(@PathVariable int id, @RequestBody CustomerOrderCreateDTO order) throws Exception {
        return orderService.update(id, order);
    }

    @DeleteMapping("/order/{id}")
    void delete(@PathVariable int id) {
        orderService.deleteById(id);
    }
}
