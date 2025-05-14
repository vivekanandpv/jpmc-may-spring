package in.athenaeum.jpmcmayspring.repositories;

import in.athenaeum.jpmcmayspring.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, Integer> {
    //  Automatic method generation
    boolean existsByCustomerId(int customerId);
    
    //  JPQL Query
    @Query("SELECT c FROM Customer c WHERE c.firstName = ?1")
    Set<Customer> myCustomers(String firstName);
}
