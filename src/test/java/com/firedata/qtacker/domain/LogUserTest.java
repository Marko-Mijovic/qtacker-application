package com.firedata.qtacker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.firedata.qtacker.web.rest.TestUtil;

public class LogUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogUser.class);
        LogUser logUser1 = new LogUser();
        logUser1.setId(1L);
        LogUser logUser2 = new LogUser();
        logUser2.setId(logUser1.getId());
        assertThat(logUser1).isEqualTo(logUser2);
        logUser2.setId(2L);
        assertThat(logUser1).isNotEqualTo(logUser2);
        logUser1.setId(null);
        assertThat(logUser1).isNotEqualTo(logUser2);
    }
}
