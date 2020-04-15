package com.firedata.qtacker.service.mapper;


import com.firedata.qtacker.domain.*;
import com.firedata.qtacker.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "company.id", target = "companyId")
    AddressDTO toDto(Address address);

    @Mapping(source = "companyId", target = "company")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
