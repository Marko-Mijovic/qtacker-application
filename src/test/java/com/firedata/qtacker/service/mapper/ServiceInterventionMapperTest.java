package com.firedata.qtacker.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServiceInterventionMapperTest {

    private ServiceInterventionMapper serviceInterventionMapper;

    @BeforeEach
    public void setUp() {
        serviceInterventionMapper = new ServiceInterventionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(serviceInterventionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serviceInterventionMapper.fromId(null)).isNull();
    }
}
