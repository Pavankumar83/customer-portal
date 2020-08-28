package com.pavan.service;

import com.pavan.domain.BankInfo;
import com.pavan.domain.CustomerEmail;
import com.pavan.domain.CustomerPhone;
import com.pavan.dto.PersonDTO;
import com.pavan.dto.PersonDTO2;
import com.pavan.repository.ext.BankInfoRepositoryExt;
import com.pavan.repository.ext.CustomerEmailRepositoryExt;
import com.pavan.repository.ext.CustomerPhoneRepositoryExt;
import com.pavan.repository.ext.PersonRepositoryExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PersonService {

    @Autowired
    PersonRepositoryExt personRepository;

    @Autowired
    CustomerEmailRepositoryExt customerEmailRepository;

    @Autowired
    CustomerPhoneRepositoryExt customerPhoneRepository;

    @Autowired
    BankInfoRepositoryExt bankInfoRepository;
  /*  public List<Person> findPersonById(Long id){
       return personRepository.findPersonById(id);}*/

    public List<PersonDTO> findPersonById(Long id) {
        return personRepository.getPersonDetailsById(id);
    }

    public PersonDTO2 findPersonInfoById(Long id) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        final PersonDTO2 personDTO = new PersonDTO2();

        AtomicReference<Long> customerId = new AtomicReference<>();
        personRepository.findPersonById(id).ifPresent(person -> {
            if (person.getCustomer() != null && person.getCustomer().getId() != null) {
                customerId.set(person.getCustomer().getId());
            }
            personDTO.setPerson(person);
        });

        CompletableFuture<Void> personFuture = CompletableFuture.supplyAsync(() -> {
            List<CustomerEmail> emails = customerEmailRepository.findCustomerEmailsById(customerId.get());
            personDTO.setEmails(emails);
            return null;
        }, executorService).thenApplyAsync(o -> {
            List<BankInfo> bankInfo = bankInfoRepository.getBankInfoById(customerId.get());
            personDTO.setBankAccounts(bankInfo);
            return null;
        }, executorService).thenAcceptAsync(u -> {
            List<CustomerPhone> phones = customerPhoneRepository.findPhoneById(customerId.get());
            personDTO.setPhones(phones);
        });

        try {
            personFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return personDTO;
    }
}
