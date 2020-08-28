package com.pavan.service;

import com.pavan.domain.BankInfo;
import com.pavan.repository.ext.BankInfoRepositoryExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    BankInfoRepositoryExt bankInfoRepository;

    public List<BankInfo> getBankInfoByPersionId(Long id){
        return null;
    }
}
