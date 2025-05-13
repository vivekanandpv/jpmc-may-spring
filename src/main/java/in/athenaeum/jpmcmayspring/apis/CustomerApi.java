package in.athenaeum.jpmcmayspring.apis;

import in.athenaeum.jpmcmayspring.exceptions.RecordNotFoundException;
import in.athenaeum.jpmcmayspring.services.CustomerService;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerCreateViewModel;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerUpdateViewModel;
import in.athenaeum.jpmcmayspring.viewmodels.CustomerViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerApi {
    private final CustomerService customerService;

    public CustomerApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerViewModel>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }
    
    @GetMapping("{customerId}")
    public ResponseEntity<CustomerViewModel> getById(@PathVariable int customerId) {
        return ResponseEntity.ok(customerService.getById(customerId));
    }
    
    @PostMapping
    public ResponseEntity<CustomerViewModel> create(@RequestBody CustomerCreateViewModel viewModel) {
        return ResponseEntity.ok(customerService.create(viewModel));
    }
    
    @PutMapping("{customerId}")
    public ResponseEntity<CustomerViewModel> update(@PathVariable int customerId, @RequestBody CustomerUpdateViewModel viewModel) {
        return ResponseEntity.ok(customerService.update(customerId, viewModel));
    }
    
    @DeleteMapping("{customerId}")
    public ResponseEntity<?> deleteById(@PathVariable int customerId) {
        customerService.deleteById(customerId);
        return ResponseEntity.noContent().build();
        // return ResponseEntity.status(204).build();
    }
    
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRecordNotFoundException(RecordNotFoundException exception) {
        return ResponseEntity.status(404).body(Map.of("error", exception.getMessage()));
    }
}
