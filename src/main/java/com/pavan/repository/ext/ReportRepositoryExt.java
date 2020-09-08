package com.pavan.repository.ext;

import com.pavan.domain.AvailableTransaction;
import com.pavan.domain.enumeration.ReportType;
import com.pavan.dto.ReportSummaryDTO;
import com.pavan.repository.ReportRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ReportRepositoryExt extends ReportRepository {


    /*  @Query("Select AT.id,rep.id, AT.transactionId ,AT.transactionType,AT.transactionMode," +
            "AT.transAmount,AT.dateOfTransaction, rep.type " +
            "from AvailableTransaction AT join Report rep on rep.id=AT.id where AT.id=:id " +
            "and AT.dateOfTransaction between :startDate and :endDate and rep.type=:reportType")
        public void getReportDataByCustomerIdAndReportType(@Param("id") Long id,
                                                           @Param("startDate") Instant startDate,
                                                           @Param("endDate") Instant endDate,
                                                           @Param("reportType") ReportType reportType);*/
//new com.pavan.dto.PersonDTO(per.id,bi.id,bi.accountHolder,bi.accountNumber,bi.branchCode,email.email,phone.extension,phone.phoneNumber)
  @Query("Select new com.pavan.dto.ReportSummaryDTO(AT.transactionType,AT.transactionMode,sum(transAmount) as Summary) from " +
      "AvailableTransaction as AT where AT.customer.id=:id and AT.dateOfTransaction between :startDate and :endDate GROUP BY AT.transactionType,AT.transactionMode")
    public List<ReportSummaryDTO> getReportDataByCustomerIdAndDateOfTransaction(@Param("id") Long id,
                                                                                @Param("startDate") Instant startDate,
                                                                                @Param("endDate") Instant endDate);


  @Query("Select AT from AvailableTransaction as AT where AT.customer.id=:id and AT.dateOfTransaction between :startDate and :endDate")
    public List<AvailableTransaction> getAdditionalReportData(@Param("id") Long id, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);






}
