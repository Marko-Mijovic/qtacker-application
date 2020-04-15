package com.firedata.qtacker.service.mapper;


import com.firedata.qtacker.domain.*;
import com.firedata.qtacker.service.dto.ServiceInterventionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceIntervention} and its DTO {@link ServiceInterventionDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceMapper.class, CompanyExternMapper.class})
public interface ServiceInterventionMapper extends EntityMapper<ServiceInterventionDTO, ServiceIntervention> {

    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "companyExtern.id", target = "companyExternId")
    ServiceInterventionDTO toDto(ServiceIntervention serviceIntervention);

    @Mapping(source = "deviceId", target = "device")
    @Mapping(source = "companyExternId", target = "companyExtern")
    ServiceIntervention toEntity(ServiceInterventionDTO serviceInterventionDTO);

    default ServiceIntervention fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceIntervention serviceIntervention = new ServiceIntervention();
        serviceIntervention.setId(id);
        return serviceIntervention;
    }
}
