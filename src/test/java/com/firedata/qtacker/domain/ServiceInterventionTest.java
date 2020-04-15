package com.firedata.qtacker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.firedata.qtacker.web.rest.TestUtil;

public class ServiceInterventionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceIntervention.class);
        ServiceIntervention serviceIntervention1 = new ServiceIntervention();
        serviceIntervention1.setId(1L);
        ServiceIntervention serviceIntervention2 = new ServiceIntervention();
        serviceIntervention2.setId(serviceIntervention1.getId());
        assertThat(serviceIntervention1).isEqualTo(serviceIntervention2);
        serviceIntervention2.setId(2L);
        assertThat(serviceIntervention1).isNotEqualTo(serviceIntervention2);
        serviceIntervention1.setId(null);
        assertThat(serviceIntervention1).isNotEqualTo(serviceIntervention2);
    }
}
