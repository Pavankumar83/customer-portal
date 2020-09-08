package com.pavan.repository.ext;

import com.pavan.domain.Person;
import com.pavan.dto.PersonDTO;
import com.pavan.repository.PersonRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepositoryExt extends PersonRepository {

    @Query("select p from Person p where p.id = :id")
   Optional<Person> findPersonById(@Param("id") Long id);

      /*@Query("SELECT " +
          "new com.pavan.dto.PersonDTO(per.id,bi.id,bi.accountHolder,bi.accountNumber,bi.branchCode,email.email,phone.extension,phone.phoneNumber)" +
          "FROM " +
          " Person as per " +
          "  JOIN BankInfo as bi " +
          "        ON per.id = bi.customer.id      " +
          "  JOIN CustomerEmail email  " +
          "        ON email.customer.id = bi.customer.id " +
          "  join CustomerPhone phone " +
          "       on phone.customer.id = bi.customer.id " +
          "where per.id=:id ")*/
    List<PersonDTO> getPersonDetailsById(@Param("id") Long id);


}
