package com.firedata.qtacker.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.firedata.qtacker.web.rest.TestUtil;

public class CompanyExternDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyExternDTO.class);
        CompanyExternDTO companyExternDTO1 = new CompanyExternDTO();
        companyExternDTO1.setId(1L);
        CompanyExternDTO companyExternDTO2 = new CompanyExternDTO();
        assertThat(companyExternDTO1).isNotEqualTo(companyExternDTO2);
        companyExternDTO2.setId(companyExternDTO1.getId());
        assertThat(companyExternDTO1).isEqualTo(companyExternDTO2);
        companyExternDTO2.setId(2L);
        assertThat(companyExternDTO1).isNotEqualTo(companyExternDTO2);
        companyExternDTO1.setId(null);
        assertThat(companyExternDTO1).isNotEqualTo(companyExternDTO2);
    }
}
