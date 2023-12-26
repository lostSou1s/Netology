package ru.netology.Eugeniy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netology.Eugeniy.OperationHistoryApiApplicationTest;
import ru.netology.Eugeniy.controller.CustomerController;
import ru.netology.Eugeniy.controller.DTO.CustomerDTO;
import ru.netology.Eugeniy.controller.DTO.CustomerGetResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CustomerControllerTest extends OperationHistoryApiApplicationTest {

    @Autowired
    CustomerController customerController;

    @Test
    void getNonExistentCustomer() {
        CustomerDTO customer = customerController.getCustomer(100);
        assertEquals(null, customer);
    }

    @Test
    void addAndRemoveCustomer() {
        CustomerGetResponse initialCustomers = customerController.getClients();
        int initialSize = initialCustomers.getClients().size();

        customerController.addCustomer(3, "Eugene");
        CustomerGetResponse updatedCustomers = customerController.getClients();
        int newSize = updatedCustomers.getClients().size();

        assertEquals(1, newSize - initialSize);
        CustomerDTO newCustomer = updatedCustomers.getClients().get(newSize - 1);
        assertEquals(3, newCustomer.getId());
        assertEquals("Eugene", newCustomer.getName());

        customerController.removeCustomer(3);
        CustomerGetResponse finalCustomers = customerController.getClients();

        assertEquals(initialSize, finalCustomers.getClients().size());
        assertTrue(finalCustomers.getClients().stream().noneMatch(c -> c.getId() == 3));
    }
    @Test
    void getClientsTest() {
        CustomerGetResponse customers = customerController.getClients();
        CustomerDTO customer1 = customers.getClients().get(0);
        CustomerDTO customer2 = customers.getClients().get(1);

        assertEquals(1, customer1.getId());
        assertEquals("Spring", customer1.getName());
        assertEquals(2, customer2.getId());
        assertEquals("Boot", customer2.getName());

        assertEquals(2, customers.getClients().size());
    }

    @Test
    void getCustomer() {
        CustomerDTO customer = customerController.getCustomer(1);

        assertEquals(1, customer.getId());
        assertEquals("Spring", customer.getName());
    }



}
