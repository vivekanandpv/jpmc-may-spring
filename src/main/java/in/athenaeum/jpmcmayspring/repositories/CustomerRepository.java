package in.athenaeum.jpmcmayspring.repositories;

import in.athenaeum.jpmcmayspring.models.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getAll();
    Customer getById(int customerId);
    Customer create(Customer customer);
    Customer update(int customerId, Customer customer);
    void deleteById(int customerId);
}
