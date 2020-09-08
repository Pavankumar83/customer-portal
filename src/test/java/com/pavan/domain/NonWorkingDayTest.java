package com.pavan.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pavan.web.rest.TestUtil;

public class NonWorkingDayTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NonWorkingDay.class);
        NonWorkingDay nonWorkingDay1 = new NonWorkingDay();
        nonWorkingDay1.setId(1L);
        NonWorkingDay nonWorkingDay2 = new NonWorkingDay();
        nonWorkingDay2.setId(nonWorkingDay1.getId());
        assertThat(nonWorkingDay1).isEqualTo(nonWorkingDay2);
        nonWorkingDay2.setId(2L);
        assertThat(nonWorkingDay1).isNotEqualTo(nonWorkingDay2);
        nonWorkingDay1.setId(null);
        assertThat(nonWorkingDay1).isNotEqualTo(nonWorkingDay2);
    }
}
