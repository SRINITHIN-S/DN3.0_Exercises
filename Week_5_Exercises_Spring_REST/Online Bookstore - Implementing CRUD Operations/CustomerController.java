package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.CustomerDTO;
import com.example.bookstoreapi.mapper.CustomerMapper;
import com.example.bookstoreapi.model.Customer;
import com.example.bookstoreapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    // GET /api/customers - Get all customers
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers().stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    // GET /api/customers/{id} - Get a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        return ResponseEntity.ok(customerDTO);
    }

    // POST /api/customers - Create a new customer
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerService.saveCustomer(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        return ResponseEntity.ok(savedCustomerDTO);
    }

    // PUT /api/customers/{id} - Update an existing customer
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());

        Customer updatedCustomer = customerService.saveCustomer(customer);
        CustomerDTO updatedCustomerDTO = customerMapper.customerToCustomerDTO(updatedCustomer);

        return ResponseEntity.ok(updatedCustomerDTO);
    }

    // DELETE /api/customers/{id} - Delete a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
