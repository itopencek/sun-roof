package cz.cvut.fit.havasiva.service;

import cz.cvut.fit.havasiva.dto.BranchCreateDTO;
import cz.cvut.fit.havasiva.dto.BranchDTO;
import cz.cvut.fit.havasiva.dto.CustomerOrderCreateDTO;
import cz.cvut.fit.havasiva.dto.CustomerOrderDTO;
import cz.cvut.fit.havasiva.entity.Branch;
import cz.cvut.fit.havasiva.entity.CustomerOrder;
import cz.cvut.fit.havasiva.entity.Employee;
import cz.cvut.fit.havasiva.repository.BranchRepository;
import cz.cvut.fit.havasiva.repository.CustomerOrderRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerOrderServiceTest {

    @Autowired
    private CustomerOrderService orderService;

    @MockBean
    private CustomerOrderRepository orderRepository;
    @MockBean
    private BranchRepository branchRepository;

    Employee employee4 = new Employee("Saul", "Goodman", "lawyer@saulgoodman.com");
    Employee employee5 = new Employee("Josh", "Nice", "josh.nice96@gmail.com");
    List<Employee> employees = Arrays.asList(employee4, employee5);
    Branch branch = new Branch("SVK", 200, true, employees);
    CustomerOrder order = new CustomerOrder("Ultra Panel v3", (float)199.9 ,"2020-09-09", "Vladimir Putout", branch);
    CustomerOrder order2 = new CustomerOrder("Mega Ultra Panel v1", (float)1252.12 ,"2020-08-09", "Vladimir Putout Sr.", branch);
    List<CustomerOrder> orders = Arrays.asList(order, order2);

    @Test
    void findAll() {
        List<CustomerOrderDTO> customerOrderDTOs = Arrays.asList(
          new CustomerOrderDTO(order.getId(), order.getProductName(), order.getPrice(),order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId()),
          new CustomerOrderDTO(order2.getId(), order2.getProductName(), order2.getPrice(),order2.getDate(), order2.getMadeBy(), order2.getCustomerOrderedFrom().getId())
        );

        BDDMockito.given(orderRepository.findAll()).willReturn(orders);

        Assertions.assertEquals(customerOrderDTOs, orderService.findAll());

        Mockito.verify(orderRepository, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findById() {
        BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

        Assertions.assertEquals(Optional.of(order), orderService.findById(order.getId()));

        Mockito.verify(orderRepository, Mockito.atLeastOnce()).findById(order.getId());
    }

    @Test
    void findByIdAsDTO() {
        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO(order.getId(), order.getProductName(), order.getPrice(), order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId());
        BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

        Assertions.assertEquals(Optional.of(customerOrderDTO), orderService.findByIdAsDTO(order.getId()));

        Mockito.verify(orderRepository, Mockito.atLeastOnce()).findById(order.getId());
    }

    @Test
    void findByMadeBy() {
        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO(order.getId(), order.getProductName(), order.getPrice(), order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId());

        BDDMockito.given(orderRepository.findByMadeBy(order.getMadeBy())).willReturn(Optional.of(order));

        Assertions.assertEquals(Optional.of(customerOrderDTO), orderService.findByMadeBy(order.getMadeBy()));

        Mockito.verify(orderRepository, Mockito.atLeastOnce()).findByMadeBy(order.getMadeBy());
    }

    @Test
    void findByOrderedFrom() {
        List<CustomerOrderDTO> customerOrderDTOs = Arrays.asList(
                new CustomerOrderDTO(order.getId(), order.getProductName(), order.getPrice(),order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId()),
                new CustomerOrderDTO(order2.getId(), order2.getProductName(), order2.getPrice(),order2.getDate(), order2.getMadeBy(), order2.getCustomerOrderedFrom().getId())
        );

        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO(order.getId(), order.getProductName(), order.getPrice(), order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId());

        BDDMockito.given(orderRepository.findByOrderedFrom(order.getCustomerOrderedFrom().getId())).willReturn(orders);

        Assertions.assertEquals(customerOrderDTOs, orderService.findByOrderedFrom(order.getCustomerOrderedFrom().getId()));

        Mockito.verify(orderRepository, Mockito.atLeastOnce()).findByOrderedFrom(order.getCustomerOrderedFrom().getId());
    }

    @Test
    void create() throws Exception {
        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO(order.getId(), order.getProductName(), order.getPrice(), order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId());
        CustomerOrderCreateDTO customerOrderCreateDTO = new CustomerOrderCreateDTO(order.getProductName(), order.getPrice(), order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId());

        BDDMockito.given(branchRepository.findById(customerOrderCreateDTO.getCustomerOrderedFromId())).willReturn(Optional.of(branch));
        BDDMockito.given(orderRepository.save(order)).willReturn(order);

        Assertions.assertEquals(customerOrderDTO, orderService.create(customerOrderCreateDTO));

        Mockito.verify(branchRepository, Mockito.atLeastOnce()).findById(customerOrderCreateDTO.getCustomerOrderedFromId());
        Mockito.verify(orderRepository, Mockito.atLeastOnce()).save(order);
    }

    @Test
    void update() throws Exception {
        CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO(order.getId(), order.getProductName(), order.getPrice(), order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId());
        CustomerOrderCreateDTO customerOrderCreateDTO = new CustomerOrderCreateDTO(order.getProductName(), order.getPrice(), order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId());

        BDDMockito.given(branchRepository.findById(customerOrderCreateDTO.getCustomerOrderedFromId())).willReturn(Optional.of(branch));
        BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

        Assertions.assertEquals(customerOrderDTO, orderService.update(order.getId(), customerOrderCreateDTO));

        Mockito.verify(orderRepository, Mockito.atLeastOnce()).findById(order.getId());
        Mockito.verify(branchRepository, Mockito.atLeastOnce()).findById(customerOrderCreateDTO.getCustomerOrderedFromId());
    }

    @Test
    void deleteById() throws Exception{
        BDDMockito.given(orderRepository.findById(order.getId())).willReturn(Optional.of(order));

        orderService.deleteById(order.getId());

        Mockito.verify(orderRepository, Mockito.atLeastOnce()).deleteById(order.getId());
    }
}