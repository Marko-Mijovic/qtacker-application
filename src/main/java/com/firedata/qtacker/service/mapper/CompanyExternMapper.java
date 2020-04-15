package com.firedata.qtacker.service.mapper;


import com.firedata.qtacker.domain.*;
import com.firedata.qtacker.service.dto.CompanyExternDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyExtern} and its DTO {@link CompanyExternDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyExternMapper extends EntityMapper<CompanyExternDTO, CompanyExtern> {



    default CompanyExtern fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyExtern companyExtern = new CompanyExtern();
        companyExtern.setId(id);
        return companyExtern;
    }
}
