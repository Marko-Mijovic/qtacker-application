package com.firedata.qtacker.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.firedata.qtacker.web.rest.TestUtil;

public class ServiceInterventionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceInterventionDTO.class);
        ServiceInterventionDTO serviceInterventionDTO1 = new ServiceInterventionDTO();
        serviceInterventionDTO1.setId(1L);
        ServiceInterventionDTO serviceInterventionDTO2 = new ServiceInterventionDTO();
        assertThat(serviceInterventionDTO1).isNotEqualTo(serviceInterventionDTO2);
        serviceInterventionDTO2.setId(serviceInterventionDTO1.getId());
        assertThat(serviceInterventionDTO1).isEqualTo(serviceInterventionDTO2);
        serviceInterventionDTO2.setId(2L);
        assertThat(serviceInterventionDTO1).isNotEqualTo(serviceInterventionDTO2);
        serviceInterventionDTO1.setId(null);
        assertThat(serviceInterventionDTO1).isNotEqualTo(serviceInterventionDTO2);
    }
}
