package com.pavan.web.rest.ext;

import com.pavan.domain.BankInfo;
import com.pavan.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Resource
@RequestMapping("/bankinfo")
public class BankInfoResourceExt {
    @Autowired
    BankService bankService;

    @GetMapping(path = "/getBankInfo/{id}")
    public List<BankInfo> getBankInfoByPersonId(@PathVariable("id") Long id) {
        return bankService.getBankInfoByPersionId(id);
    }
}
