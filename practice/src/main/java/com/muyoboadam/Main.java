package com.muyoboadam;

import com.muyoboadam.customer.Customer;
import com.muyoboadam.customer.CustomerRepository;
import jakarta.persistence.PostUpdate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(
            String name,
            String email,
            Integer age
    ){}

    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerID}")
    public void deleteCustomer(@PathVariable("customerID") Integer id){
        customerRepository.deleteById(id);
    }

    @PostMapping("{customerID}")
    public void updateCustomer(@PathVariable("customerID") Integer id, @RequestBody NewCustomerRequest request){
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            if(request.name != null) customer.setName(request.name);
            if(request.email != null) customer.setEmail(request.email);
            if(request.age != null) customer.setAge(request.age);
            customerRepository.save(customer);
        }
    }
}
