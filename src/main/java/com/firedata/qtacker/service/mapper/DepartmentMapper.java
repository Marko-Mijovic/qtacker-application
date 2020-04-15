package com.firedata.qtacker.service.mapper;


import com.firedata.qtacker.domain.*;
import com.firedata.qtacker.service.dto.DepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {

    @Mapping(source = "company.id", target = "companyId")
    DepartmentDTO toDto(Department department);

    @Mapping(source = "companyId", target = "company")
    Department toEntity(DepartmentDTO departmentDTO);

    default Department fromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
