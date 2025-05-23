package in.athenaeum.jpmcmayspring.repositories;

import in.athenaeum.jpmcmayspring.dataaccess.CustomerRowMapper;
import in.athenaeum.jpmcmayspring.exceptions.RecordNotFoundException;
import in.athenaeum.jpmcmayspring.models.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

//@Repository
public class CustomerRepositoryImplementation implements CustomerRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> customerRowMapper;

    public CustomerRepositoryImplementation(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> getAll() {
        String sql = "SELECT customer_id, first_name, last_name, email FROM customer";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Customer getById(int customerId) {
        String sql = "SELECT customer_id, first_name, last_name, email FROM customer WHERE customer_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, customerRowMapper, customerId);
        } catch (EmptyResultDataAccessException exception) {
            System.out.println(exception.getMessage());
            throw new RecordNotFoundException(String.format("Customer with id %d is not found", customerId));
        }
        
    }

    @Override
    public Customer create(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, email) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            return ps;
        }, keyHolder);

        // Retrieve the generated customer ID
        Number key = keyHolder.getKey();
        if (key != null) {
            customer.setCustomerId(key.intValue());
        }

        return customer;
    }

    @Override
    public Customer update(int customerId, Customer customer) {
        String sql = "UPDATE customer SET email=? WHERE customer_id=?";
        jdbcTemplate.update(sql, customer.getEmail(), customerId);
        return customer;
    }

    @Override
    public void deleteById(int customerId) {
        String sql = "DELETE FROM customer WHERE customer_id=?";
        int rows = jdbcTemplate.update(sql, customerId);
        
        if (rows == 0) {
            throw new RecordNotFoundException(String.format("Customer with id %d is not found", customerId));
        }
    }
}
