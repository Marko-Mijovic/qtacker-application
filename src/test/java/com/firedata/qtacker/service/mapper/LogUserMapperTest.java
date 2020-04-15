package com.firedata.qtacker.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LogUserMapperTest {

    private LogUserMapper logUserMapper;

    @BeforeEach
    public void setUp() {
        logUserMapper = new LogUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(logUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(logUserMapper.fromId(null)).isNull();
    }
}
