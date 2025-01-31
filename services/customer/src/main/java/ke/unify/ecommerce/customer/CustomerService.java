package ke.unify.ecommerce.customer;

import jakarta.validation.Valid;
import ke.unify.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(@Valid CustomerRequest request) {

        Customer customer = customerRepository.save(customerMapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = customerRepository.findById(request.id()).orElseThrow(()-> new CustomerNotFoundException(
                format("Cannot update customer:: No customer with the provided id '%s' ", request.id())
        ));

        mergeCustomer(customer, request);

        customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, @Valid CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }

        if (StringUtils.isNotBlank(request.lastname())) {
            customer.setLastname(request.lastname());
        }

        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }

        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream().map(customerMapper::fromCustomer).collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return customerRepository.existsById(customerId);
    }

    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(()-> new CustomerNotFoundException(format("No customer found with the provided ID:: %s", customerId)));
    }
    public void deleteById(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
