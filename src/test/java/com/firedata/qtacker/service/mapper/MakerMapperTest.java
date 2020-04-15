package com.firedata.qtacker.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MakerMapperTest {

    private MakerMapper makerMapper;

    @BeforeEach
    public void setUp() {
        makerMapper = new MakerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(makerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(makerMapper.fromId(null)).isNull();
    }
}
