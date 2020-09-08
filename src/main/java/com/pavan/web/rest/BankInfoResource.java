package com.pavan.web.rest;

import com.pavan.domain.BankInfo;
import com.pavan.repository.BankInfoRepository;
import com.pavan.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pavan.domain.BankInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BankInfoResource {

    private final Logger log = LoggerFactory.getLogger(BankInfoResource.class);

    private static final String ENTITY_NAME = "bankInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankInfoRepository bankInfoRepository;

    public BankInfoResource(BankInfoRepository bankInfoRepository) {
        this.bankInfoRepository = bankInfoRepository;
    }

    /**
     * {@code POST  /bank-infos} : Create a new bankInfo.
     *
     * @param bankInfo the bankInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankInfo, or with status {@code 400 (Bad Request)} if the bankInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-infos")
    public ResponseEntity<BankInfo> createBankInfo(@Valid @RequestBody BankInfo bankInfo) throws URISyntaxException {
        log.debug("REST request to save BankInfo : {}", bankInfo);
        if (bankInfo.getId() != null) {
            throw new BadRequestAlertException("A new bankInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankInfo result = bankInfoRepository.save(bankInfo);
        return ResponseEntity.created(new URI("/api/bank-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-infos} : Updates an existing bankInfo.
     *
     * @param bankInfo the bankInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankInfo,
     * or with status {@code 400 (Bad Request)} if the bankInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-infos")
    public ResponseEntity<BankInfo> updateBankInfo(@Valid @RequestBody BankInfo bankInfo) throws URISyntaxException {
        log.debug("REST request to update BankInfo : {}", bankInfo);
        if (bankInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankInfo result = bankInfoRepository.save(bankInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bank-infos} : get all the bankInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankInfos in body.
     */
    @GetMapping("/bank-infos")
    public List<BankInfo> getAllBankInfos() {
        log.debug("REST request to get all BankInfos");
        return bankInfoRepository.findAll();
    }

    /**
     * {@code GET  /bank-infos/:id} : get the "id" bankInfo.
     *
     * @param id the id of the bankInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-infos/{id}")
    public ResponseEntity<BankInfo> getBankInfo(@PathVariable Long id) {
        log.debug("REST request to get BankInfo : {}", id);
        Optional<BankInfo> bankInfo = bankInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankInfo);
    }

    /**
     * {@code DELETE  /bank-infos/:id} : delete the "id" bankInfo.
     *
     * @param id the id of the bankInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-infos/{id}")
    public ResponseEntity<Void> deleteBankInfo(@PathVariable Long id) {
        log.debug("REST request to delete BankInfo : {}", id);
        bankInfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
