package com.pavan.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pavan.web.rest.TestUtil;

public class CustomerPhoneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerPhone.class);
        CustomerPhone customerPhone1 = new CustomerPhone();
        customerPhone1.setId(1L);
        CustomerPhone customerPhone2 = new CustomerPhone();
        customerPhone2.setId(customerPhone1.getId());
        assertThat(customerPhone1).isEqualTo(customerPhone2);
        customerPhone2.setId(2L);
        assertThat(customerPhone1).isNotEqualTo(customerPhone2);
        customerPhone1.setId(null);
        assertThat(customerPhone1).isNotEqualTo(customerPhone2);
    }
}
