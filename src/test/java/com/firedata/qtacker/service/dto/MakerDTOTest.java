package com.firedata.qtacker.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.firedata.qtacker.web.rest.TestUtil;

public class MakerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MakerDTO.class);
        MakerDTO makerDTO1 = new MakerDTO();
        makerDTO1.setId(1L);
        MakerDTO makerDTO2 = new MakerDTO();
        assertThat(makerDTO1).isNotEqualTo(makerDTO2);
        makerDTO2.setId(makerDTO1.getId());
        assertThat(makerDTO1).isEqualTo(makerDTO2);
        makerDTO2.setId(2L);
        assertThat(makerDTO1).isNotEqualTo(makerDTO2);
        makerDTO1.setId(null);
        assertThat(makerDTO1).isNotEqualTo(makerDTO2);
    }
}
