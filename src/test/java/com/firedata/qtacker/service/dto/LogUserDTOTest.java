package com.firedata.qtacker.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.firedata.qtacker.web.rest.TestUtil;

public class LogUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogUserDTO.class);
        LogUserDTO logUserDTO1 = new LogUserDTO();
        logUserDTO1.setId(1L);
        LogUserDTO logUserDTO2 = new LogUserDTO();
        assertThat(logUserDTO1).isNotEqualTo(logUserDTO2);
        logUserDTO2.setId(logUserDTO1.getId());
        assertThat(logUserDTO1).isEqualTo(logUserDTO2);
        logUserDTO2.setId(2L);
        assertThat(logUserDTO1).isNotEqualTo(logUserDTO2);
        logUserDTO1.setId(null);
        assertThat(logUserDTO1).isNotEqualTo(logUserDTO2);
    }
}
