package com.pavan.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pavan.web.rest.TestUtil;

public class CustomerEmailTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerEmail.class);
        CustomerEmail customerEmail1 = new CustomerEmail();
        customerEmail1.setId(1L);
        CustomerEmail customerEmail2 = new CustomerEmail();
        customerEmail2.setId(customerEmail1.getId());
        assertThat(customerEmail1).isEqualTo(customerEmail2);
        customerEmail2.setId(2L);
        assertThat(customerEmail1).isNotEqualTo(customerEmail2);
        customerEmail1.setId(null);
        assertThat(customerEmail1).isNotEqualTo(customerEmail2);
    }
}
