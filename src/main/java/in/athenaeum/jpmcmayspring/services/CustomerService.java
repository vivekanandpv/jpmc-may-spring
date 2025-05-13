package in.athenaeum.jpmcmayspring.services;

import in.athenaeum.jpmcmayspring.viewmodels.CustomerCreateViewModel;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerUpdateViewModel;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerViewModel;

import java.util.List;

public interface CustomerService {
    List<CustomerViewModel> getAll();
    CustomerViewModel getById(int customerId);
    CustomerViewModel create(CustomerCreateViewModel customer);
    CustomerViewModel update(int customerId, CustomerUpdateViewModel customer);
    void deleteById(int customerId);
}
