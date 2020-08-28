package com.pavan.repository.ext;

import com.pavan.domain.Institution;
import com.pavan.repository.InstitutionRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepositoryExt extends InstitutionRepository {

    /*@Query("select inst from Institution inst where inst.customer.id=:id")
    public List<Institution> findInstitutionById(Long id);*/
}
