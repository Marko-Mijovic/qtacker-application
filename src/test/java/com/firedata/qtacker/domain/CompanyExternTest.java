package com.firedata.qtacker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.firedata.qtacker.web.rest.TestUtil;

public class CompanyExternTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyExtern.class);
        CompanyExtern companyExtern1 = new CompanyExtern();
        companyExtern1.setId(1L);
        CompanyExtern companyExtern2 = new CompanyExtern();
        companyExtern2.setId(companyExtern1.getId());
        assertThat(companyExtern1).isEqualTo(companyExtern2);
        companyExtern2.setId(2L);
        assertThat(companyExtern1).isNotEqualTo(companyExtern2);
        companyExtern1.setId(null);
        assertThat(companyExtern1).isNotEqualTo(companyExtern2);
    }
}
