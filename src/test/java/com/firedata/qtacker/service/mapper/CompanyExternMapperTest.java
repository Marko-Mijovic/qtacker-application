package com.firedata.qtacker.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyExternMapperTest {

    private CompanyExternMapper companyExternMapper;

    @BeforeEach
    public void setUp() {
        companyExternMapper = new CompanyExternMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(companyExternMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(companyExternMapper.fromId(null)).isNull();
    }
}
