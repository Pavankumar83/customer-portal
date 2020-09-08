package com.pavan.repository.ext;

import com.pavan.domain.NonWorkingDay;
import com.pavan.dto.PersonDTO;
import com.pavan.repository.NonWorkingDayRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface NonWorkingDayRepositoryExt extends NonWorkingDayRepository {

    @Override
    Page<NonWorkingDay> findAll(Pageable pageable);
}
