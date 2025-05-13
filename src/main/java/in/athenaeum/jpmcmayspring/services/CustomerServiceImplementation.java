package in.athenaeum.jpmcmayspring.services;

import in.athenaeum.jpmcmayspring.models.Customer;
import in.athenaeum.jpmcmayspring.repositories.CustomerRepository;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerCreateViewModel;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerUpdateViewModel;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerViewModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerViewModel> getAll() {
        return customerRepository
                .getAll()
                .stream()
                .map(customer -> {
                    CustomerViewModel viewModel = new CustomerViewModel();
                    BeanUtils.copyProperties(customer, viewModel);
                    return viewModel;
                })
                .toList();
                // .collect(Collectors.toList());
    }

    @Override
    public CustomerViewModel getById(int customerId) {
        Customer customer = customerRepository.getById(customerId);
        CustomerViewModel viewModel = new CustomerViewModel();
        BeanUtils.copyProperties(customer, viewModel);
        return viewModel;
    }

    @Override
    public CustomerViewModel create(CustomerCreateViewModel viewModel) {
        //  converting CreateViewModel to domain
        Customer customerD = new Customer();
        BeanUtils.copyProperties(viewModel, customerD);
        
        //  executing repository method
        Customer customerInserted = customerRepository.create(customerD);
        
        //  converting domain to ViewModel
        CustomerViewModel vm = new CustomerViewModel();
        BeanUtils.copyProperties(customerInserted, vm);
        return vm;
    }

    @Override
    public CustomerViewModel update(int customerId, CustomerUpdateViewModel updateViewModel) {
        Customer customer = customerRepository.getById(customerId);
        BeanUtils.copyProperties(updateViewModel, customer);
        
        customerRepository.update(customerId, customer);

        CustomerViewModel vm = new CustomerViewModel();
        BeanUtils.copyProperties(customer, vm);
        return vm;
    }

    @Override
    public void deleteById(int customerId) {
        customerRepository.deleteById(customerId);
    }
}
