package cz.cvut.fit.havasiva.controller;

import cz.cvut.fit.havasiva.dto.CustomerOrderCreateDTO;
import cz.cvut.fit.havasiva.dto.CustomerOrderDTO;
import cz.cvut.fit.havasiva.dto.EmployeeCreateDTO;
import cz.cvut.fit.havasiva.entity.Branch;
import cz.cvut.fit.havasiva.entity.CustomerOrder;
import cz.cvut.fit.havasiva.entity.Employee;
import cz.cvut.fit.havasiva.service.CustomerOrderService;
import cz.cvut.fit.havasiva.service.EmployeeService;
import org.checkerframework.checker.units.qual.C;
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
class CustomerOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerOrderService customerOrderService;

    Employee employee4 = new Employee("Saul", "Goodman", "lawyer@saulgoodman.com");
    Employee employee5 = new Employee("Josh", "Nice", "josh.nice96@gmail.com");
    List<Employee> employees = Arrays.asList(employee4, employee5);
    Branch branch = new Branch("SVK", 200, true, employees);
    CustomerOrder order = new CustomerOrder("Ultra Panel v3", (float)199.9 ,"2020-09-09", "Vladimir Putout", branch);
    CustomerOrder order2 = new CustomerOrder("Mega Ultra Panel v1", (float)1252.12 ,"2020-08-09", "Vladimir Putout Sr.", branch);
    List<CustomerOrder> orders = Arrays.asList(order, order2);
    CustomerOrderDTO customerOrderDTO = new CustomerOrderDTO(order.getId(), order.getProductName(), order.getPrice(),order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId());
    CustomerOrderDTO customerOrderDTO2 = new CustomerOrderDTO(order2.getId(), order2.getProductName(), order2.getPrice(),order2.getDate(), order2.getMadeBy(), order2.getCustomerOrderedFrom().getId());

    @Test
    void all() throws Exception {
        List<CustomerOrderDTO> customerOrderDTOs = Arrays.asList(
                new CustomerOrderDTO(order.getId(), order.getProductName(), order.getPrice(),order.getDate(), order.getMadeBy(), order.getCustomerOrderedFrom().getId()),
                new CustomerOrderDTO(order2.getId(), order2.getProductName(), order2.getPrice(),order2.getDate(), order2.getMadeBy(), order2.getCustomerOrderedFrom().getId())
        );

        BDDMockito.given(customerOrderService.findAll()).willReturn(customerOrderDTOs);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/order/all")
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(customerOrderDTO.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName", CoreMatchers.is(customerOrderDTO.getProductName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", CoreMatchers.is(customerOrderDTO.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date", CoreMatchers.is(customerOrderDTO.getDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].madeBy", CoreMatchers.is(customerOrderDTO.getMadeBy())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderedFromId", CoreMatchers.is(customerOrderDTO.getOrderedFromId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", CoreMatchers.is(customerOrderDTO2.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName", CoreMatchers.is(customerOrderDTO2.getProductName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price", CoreMatchers.is(customerOrderDTO2.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date", CoreMatchers.is(customerOrderDTO2.getDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].madeBy", CoreMatchers.is(customerOrderDTO2.getMadeBy())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].orderedFromId", CoreMatchers.is(customerOrderDTO2.getOrderedFromId())));

        BDDMockito.verify(customerOrderService, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void byId() throws Exception {
        BDDMockito.given(customerOrderService.findByIdAsDTO(customerOrderDTO.getId())).willReturn(Optional.of(customerOrderDTO));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/order/{id}", customerOrderDTO.getId())
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(customerOrderDTO.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName", CoreMatchers.is(customerOrderDTO.getProductName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(customerOrderDTO.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", CoreMatchers.is(customerOrderDTO.getDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.madeBy", CoreMatchers.is(customerOrderDTO.getMadeBy())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderedFromId", CoreMatchers.is(customerOrderDTO.getOrderedFromId())));


        BDDMockito.verify(customerOrderService, Mockito.atLeastOnce()).findByIdAsDTO(customerOrderDTO.getId());
    }

    @Test
    void byBranchId() throws Exception {
        List<CustomerOrderDTO> orders = Arrays.asList(customerOrderDTO);
        BDDMockito.given(customerOrderService.findByOrderedFrom(customerOrderDTO.getOrderedFromId())).willReturn(orders);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/order/all/{branchId}", customerOrderDTO.getOrderedFromId())
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(customerOrderDTO.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName", CoreMatchers.is(customerOrderDTO.getProductName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", CoreMatchers.is(customerOrderDTO.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date", CoreMatchers.is(customerOrderDTO.getDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].madeBy", CoreMatchers.is(customerOrderDTO.getMadeBy())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderedFromId", CoreMatchers.is(customerOrderDTO.getOrderedFromId())));


        BDDMockito.verify(customerOrderService, Mockito.atLeastOnce()).findByOrderedFrom(customerOrderDTO.getOrderedFromId());
    }

    @Test
    void byMadeBy() throws Exception {
        BDDMockito.given(customerOrderService.findByMadeBy(customerOrderDTO.getMadeBy())).willReturn(Optional.of(customerOrderDTO));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/order/employee/{madeBy}", customerOrderDTO.getMadeBy())
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(customerOrderDTO.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName", CoreMatchers.is(customerOrderDTO.getProductName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(customerOrderDTO.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", CoreMatchers.is(customerOrderDTO.getDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.madeBy", CoreMatchers.is(customerOrderDTO.getMadeBy())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderedFromId", CoreMatchers.is(customerOrderDTO.getOrderedFromId())));


        BDDMockito.verify(customerOrderService, Mockito.atLeastOnce()).findByMadeBy(customerOrderDTO.getMadeBy());
    }

    @Test
    void save() throws Exception {
        CustomerOrderCreateDTO customerOrderCreateDTO = new CustomerOrderCreateDTO(customerOrderDTO.getProductName(), customerOrderDTO.getPrice(), customerOrderDTO.getDate(), customerOrderDTO.getMadeBy(), customerOrderDTO.getOrderedFromId());
        BDDMockito.given(customerOrderService.create(customerOrderCreateDTO)).willReturn(customerOrderDTO);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/order")
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
                        .content("{\"productName\": \"" + customerOrderDTO.getProductName() + "\", \"price\": " + customerOrderDTO.getPrice() + ", \"date\": \"" + customerOrderDTO.getDate() + "\", \"madeBy\": \""+ customerOrderDTO.getMadeBy() +"\", \"orderedFromId\": \""+ customerOrderDTO.getOrderedFromId() +"\"}")
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        BDDMockito.verify(customerOrderService, Mockito.atLeastOnce()).create(customerOrderCreateDTO);
    }

    @Test
    void saveException() throws Exception {
        CustomerOrderCreateDTO customerOrderCreateDTO = new CustomerOrderCreateDTO(customerOrderDTO.getProductName(), customerOrderDTO.getPrice(), customerOrderDTO.getDate(), customerOrderDTO.getMadeBy(), customerOrderDTO.getOrderedFromId());
        BDDMockito.given(customerOrderService.create(customerOrderCreateDTO)).willThrow(new Exception("No branch found!"));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/order")
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
                        .content("{\"productName\": \"" + customerOrderDTO.getProductName() + "\", \"price\": " + customerOrderDTO.getPrice() + ", \"date\": \"" + customerOrderDTO.getDate() + "\", \"madeBy\": \""+ customerOrderDTO.getMadeBy() +"\", \"orderedFromId\": \""+ customerOrderDTO.getOrderedFromId() +"\"}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason(CoreMatchers.is("No branch found!")));

        BDDMockito.verify(customerOrderService, Mockito.atLeastOnce()).create(customerOrderCreateDTO);
    }

    @Test
    void update() throws Exception {
        CustomerOrderCreateDTO customerOrderCreateDTO = new CustomerOrderCreateDTO(customerOrderDTO.getProductName(), customerOrderDTO.getPrice(), customerOrderDTO.getDate(), customerOrderDTO.getMadeBy(), customerOrderDTO.getOrderedFromId());
        BDDMockito.given(customerOrderService.update(customerOrderDTO.getId(), customerOrderCreateDTO)).willReturn(customerOrderDTO);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/order/{id}", customerOrderDTO.getId())
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
                        .content("{\"productName\": \"" + customerOrderDTO.getProductName() + "\", \"price\": " + customerOrderDTO.getPrice() + ", \"date\": \"" + customerOrderDTO.getDate() + "\", \"madeBy\": \""+ customerOrderDTO.getMadeBy() +"\", \"orderedFromId\": \""+ customerOrderDTO.getOrderedFromId() +"\"}")
        ).andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.verify(customerOrderService, Mockito.atLeastOnce()).update(customerOrderDTO.getId(), customerOrderCreateDTO);
    }

    @Test
    void updateException() throws Exception {
        CustomerOrderCreateDTO customerOrderCreateDTO = new CustomerOrderCreateDTO(customerOrderDTO.getProductName(), customerOrderDTO.getPrice(), customerOrderDTO.getDate(), customerOrderDTO.getMadeBy(), customerOrderDTO.getOrderedFromId());
        BDDMockito.given(customerOrderService.update(customerOrderDTO.getId(), customerOrderCreateDTO)).willThrow(new Exception("CustomerOrder wasn't found"));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/order/{id}", customerOrderDTO.getId())
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
                        .content("{\"productName\": \"" + customerOrderDTO.getProductName() + "\", \"price\": " + customerOrderDTO.getPrice() + ", \"date\": \"" + customerOrderDTO.getDate() + "\", \"madeBy\": \""+ customerOrderDTO.getMadeBy() +"\", \"orderedFromId\": \""+ customerOrderDTO.getOrderedFromId() +"\"}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason(CoreMatchers.is("CustomerOrder wasn't found")));

        BDDMockito.verify(customerOrderService, Mockito.atLeastOnce()).update(customerOrderDTO.getId(), customerOrderCreateDTO);
    }

    @Test
    void delete() throws Exception {
        BDDMockito.doNothing().when(customerOrderService).deleteById(customerOrderDTO.getId());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/order/{id}", customerOrderDTO.getId())
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.verify(customerOrderService, Mockito.atLeastOnce()).deleteById(customerOrderDTO.getId());
    }

    @Test
    void deleteException() throws Exception {
        BDDMockito.doThrow(new Exception("Order not found!")).when(customerOrderService).deleteById(customerOrderDTO.getId());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/order/{id}", customerOrderDTO.getId())
                        .contentType("application/json;charset=UTF-8")
                        .accept("application/json;charset=UTF-8")
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason(CoreMatchers.is("Order not found!")));

        BDDMockito.verify(customerOrderService, Mockito.atLeastOnce()).deleteById(customerOrderDTO.getId());
    }
}