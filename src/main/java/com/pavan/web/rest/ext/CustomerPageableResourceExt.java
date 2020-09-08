package com.pavan.web.rest.ext;

import com.pavan.domain.Customer;
import com.pavan.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerPageableResourceExt {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/customerpage/{page}/{size}")
    public Page<Customer> getCustomersByPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
       return  customerService.getCustomersPageById(page,size);

    }
    @GetMapping(path = "/customerpagebyName/{page}/{size}")
    public Page<Customer> getCustomersByPageName(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return  customerService.getCustomersPageByField(page,size);

    }

    @GetMapping(path = "/customerpagebyNameAndIdentificationType/{page}/{size}")
    public Page<Customer> getCustomersByPageNameAndIdentificationType(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return  customerService.getCustomersByPageNameAndIdentificationType(page,size);

    }
}
