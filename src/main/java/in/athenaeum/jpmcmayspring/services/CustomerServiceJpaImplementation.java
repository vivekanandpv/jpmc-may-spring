package in.athenaeum.jpmcmayspring.services;

import in.athenaeum.jpmcmayspring.exceptions.RecordNotFoundException;
import in.athenaeum.jpmcmayspring.models.Customer;
import in.athenaeum.jpmcmayspring.repositories.CustomerJpaRepository;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerCreateViewModel;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerUpdateViewModel;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerViewModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceJpaImplementation implements CustomerService {
    
    private final CustomerJpaRepository customerRepository;

    public CustomerServiceJpaImplementation(CustomerJpaRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerViewModel> getAll() {
//        return customerRepository
//                .findAll()
//                .stream()
//                .map(customer -> {
//                    CustomerViewModel viewModel = new CustomerViewModel();
//                    BeanUtils.copyProperties(customer, viewModel);
//                    return viewModel;
//                })
//                .toList();
        // .collect(Collectors.toList());

        return customerRepository
                .myCustomers("John")
                .stream()
                .map(customer -> {
                    CustomerViewModel viewModel = new CustomerViewModel();
                    BeanUtils.copyProperties(customer, viewModel);
                    return viewModel;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerViewModel getById(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RecordNotFoundException("Customer not found: " + customerId));
        CustomerViewModel viewModel = new CustomerViewModel();
        BeanUtils.copyProperties(customer, viewModel);
        return viewModel;
    }

    @Transactional
    @Override
    public CustomerViewModel create(CustomerCreateViewModel viewModel) {
        //  converting CreateViewModel to domain
        Customer customerD = new Customer();
        BeanUtils.copyProperties(viewModel, customerD);

        //  executing repository method
        Customer customerInserted = customerRepository.saveAndFlush(customerD);

        //  converting domain to ViewModel
        CustomerViewModel vm = new CustomerViewModel();
        BeanUtils.copyProperties(customerInserted, vm);
        return vm;
    }

    @Transactional
    @Override
    public CustomerViewModel update(int customerId, CustomerUpdateViewModel updateViewModel) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RecordNotFoundException("Customer not found: " + customerId));
        BeanUtils.copyProperties(updateViewModel, customer);

        customerRepository.saveAndFlush(customer);

        CustomerViewModel vm = new CustomerViewModel();
        BeanUtils.copyProperties(customer, vm);
        return vm;
    }

    @Transactional
    @Override
    public void deleteById(int customerId) {
        if (customerRepository.existsByCustomerId(customerId)) {
            customerRepository.deleteById(customerId);
        } else {
            throw new RecordNotFoundException("Customer is not found: " + customerId);
        }
    }
}
