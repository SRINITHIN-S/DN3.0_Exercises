package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.CustomerDTO;
import com.example.bookstoreapi.mapper.CustomerMapper;
import com.example.bookstoreapi.model.Customer;
import com.example.bookstoreapi.model.CustomerModel;
import com.example.bookstoreapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    // GET /api/customers - Get all customers
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<CollectionModel<CustomerModel>> getAllCustomers() {
        List<CustomerModel> customers = customerService.getAllCustomers().stream()
                .map(customerMapper::customerToCustomerDTO)
                .map(CustomerModel::new)
                .map(this::addCustomerLinks)
                .collect(Collectors.toList());

        CollectionModel<CustomerModel> collectionModel = CollectionModel.of(customers);
        collectionModel.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    // GET /api/customers/{id} - Get a customer by ID
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<EntityModel<CustomerModel>> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        CustomerModel customerModel = new CustomerModel(customerDTO);
        addCustomerLinks(customerModel);
        return ResponseEntity.ok(EntityModel.of(customerModel));
    }

    // POST /api/customers - Create a new customer
    @PostMapping(
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<CustomerModel> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerService.saveCustomer(customer);
        CustomerDTO savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        CustomerModel savedCustomerModel = new CustomerModel(savedCustomerDTO);

        // Set Location header if needed
        // HttpHeaders headers = new HttpHeaders();
        // headers.setLocation(URI.create("/api/customers/" + savedCustomerModel.getId()));

        addCustomerLinks(savedCustomerModel);
        return new ResponseEntity<>(savedCustomerModel, HttpStatus.CREATED);
    }

    // PUT /api/customers/{id} - Update an existing customer
    @PutMapping(
        value = "/{id}",
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<CustomerModel> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());

        Customer updatedCustomer = customerService.saveCustomer(customer);
        CustomerDTO updatedCustomerDTO = customerMapper.customerToCustomerDTO(updatedCustomer);
        CustomerModel updatedCustomerModel = new CustomerModel(updatedCustomerDTO);

        addCustomerLinks(updatedCustomerModel);
        return ResponseEntity.ok(updatedCustomerModel);
    }

    // DELETE /api/customers/{id} - Delete a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Helper method to add HATEOAS links
    private CustomerModel addCustomerLinks(CustomerModel customerModel) {
        customerModel.add(linkTo(methodOn(CustomerController.class).getCustomerById(customerModel.getId())).withSelfRel());
        customerModel.add(linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
        return customerModel;
    }
}
