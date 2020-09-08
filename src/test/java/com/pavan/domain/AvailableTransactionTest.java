package com.pavan.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pavan.web.rest.TestUtil;

public class AvailableTransactionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvailableTransaction.class);
        AvailableTransaction availableTransaction1 = new AvailableTransaction();
        availableTransaction1.setId(1L);
        AvailableTransaction availableTransaction2 = new AvailableTransaction();
        availableTransaction2.setId(availableTransaction1.getId());
        assertThat(availableTransaction1).isEqualTo(availableTransaction2);
        availableTransaction2.setId(2L);
        assertThat(availableTransaction1).isNotEqualTo(availableTransaction2);
        availableTransaction1.setId(null);
        assertThat(availableTransaction1).isNotEqualTo(availableTransaction2);
    }
}
