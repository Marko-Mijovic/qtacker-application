package com.firedata.qtacker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.firedata.qtacker.web.rest.TestUtil;

public class MakerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Maker.class);
        Maker maker1 = new Maker();
        maker1.setId(1L);
        Maker maker2 = new Maker();
        maker2.setId(maker1.getId());
        assertThat(maker1).isEqualTo(maker2);
        maker2.setId(2L);
        assertThat(maker1).isNotEqualTo(maker2);
        maker1.setId(null);
        assertThat(maker1).isNotEqualTo(maker2);
    }
}
