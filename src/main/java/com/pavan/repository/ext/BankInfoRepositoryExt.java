package com.pavan.repository.ext;

import com.pavan.domain.BankInfo;
import com.pavan.repository.BankInfoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankInfoRepositoryExt extends BankInfoRepository {

    @Query("select info from BankInfo info where info.customer.id=:id")
    List<BankInfo> getBankInfoById(@Param("id") Long id);


}
