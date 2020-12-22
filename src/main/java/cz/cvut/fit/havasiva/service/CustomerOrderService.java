package cz.cvut.fit.havasiva.service;

import cz.cvut.fit.havasiva.dto.BranchDTO;
import cz.cvut.fit.havasiva.dto.CustomerOrderCreateDTO;
import cz.cvut.fit.havasiva.dto.CustomerOrderDTO;
import cz.cvut.fit.havasiva.entity.Branch;
import cz.cvut.fit.havasiva.entity.CustomerOrder;
import cz.cvut.fit.havasiva.repository.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerOrderService {

    private final CustomerOrderRepository orderRepository;
    private final BranchService branchService;

    @Autowired
    public CustomerOrderService(CustomerOrderRepository orderRepository, BranchService branchService) {
        this.orderRepository = orderRepository;
        this.branchService = branchService;
    }

    public List<CustomerOrderDTO> findAll() {
        return orderRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<CustomerOrder> findById(int id) {
        return orderRepository.findById(id);
    }

    public Optional<CustomerOrderDTO> findByIdAsDTO(int id) {
        return toDTO(findById(id));
    }

    public List<CustomerOrderDTO> findByMadeBy(String madeBy) {
        return orderRepository.findByMadeBy(madeBy).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<CustomerOrderDTO> findByOrderedFrom(int branchId) throws Exception {
        Optional<Branch> branch = branchService.findById(branchId);

        if(branch.isEmpty())
            throw new Exception("Branch was not found");

        return orderRepository.findByOrderedFrom(branch).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CustomerOrderDTO create(CustomerOrderCreateDTO orderDTO) throws Exception {
        Optional<Branch> optionalBranch = branchService.findById(orderDTO.getCustomerOrderedFromId());
        if(optionalBranch.isEmpty())
            throw new Exception("No branch found!");
        Branch branch = optionalBranch.get();

        CustomerOrder order = new CustomerOrder(orderDTO.getProductName(), orderDTO.getPrice(), orderDTO.getDate(), orderDTO.getMadeBy(), branch);

        return toDTO(orderRepository.save(order));
    }

    public CustomerOrderDTO update(int id, CustomerOrderCreateDTO orderDTO) throws Exception {
        Optional<CustomerOrder> optionalCustomerOrder = findById(id);
        if(optionalCustomerOrder.isEmpty())
            throw new Exception("CustomerOrder wasn't found");
        CustomerOrder order = optionalCustomerOrder.get();

        Optional<Branch> optionalBranch = branchService.findById(orderDTO.getCustomerOrderedFromId());
        if(optionalBranch.isEmpty())
            throw new Exception("No branch with given Id found! Id: " + orderDTO.getCustomerOrderedFromId());
        Branch branch = optionalBranch.get();

        order.setDate(orderDTO.getDate());
        order.setMadeBy(orderDTO.getMadeBy());
        order.setCustomerOrderedFrom(branch);
        order.setPrice(orderDTO.getPrice());
        order.setProductName(orderDTO.getProductName());

        return toDTO(order);
    }

    public List<Integer> updateAllByName(String name, double newPrice, String madeAfter) throws Exception {
        List<CustomerOrder> orders = orderRepository.findAllByProductName(name);//.stream().map(this::toDTO).collect(Collectors.toList());
        List<Integer> ids = new ArrayList<>();

        if(orders.isEmpty())
            throw new Exception("No order with name " + name + " found!");

        for (CustomerOrder order: orders) {
            // first date is greater than second
            if(order.getDate().compareTo(madeAfter) > 0)
            {
                // update price
                order.setPrice(newPrice);
                // add to List of integer to return
                ids.add(order.getId());
            }
        }

        // save orders
        orderRepository.saveAll(orders);

        // returning updated ids
        return ids;
    }

    public void deleteById(int id) throws Exception{
        Optional<CustomerOrder> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isEmpty())
                throw new Exception("Order not found!");
        orderRepository.deleteById(id);
    }

    private CustomerOrderDTO toDTO(CustomerOrder order) {
        return new CustomerOrderDTO(order.getId(), order.getProductName(), order.getPrice(), order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId());
    }

    private Optional<CustomerOrderDTO> toDTO(Optional<CustomerOrder> order) {
        if(order.isEmpty())
                return Optional.empty();
        return Optional.of(toDTO(order.get()));
    }
}
