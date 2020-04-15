package com.firedata.qtacker.service.mapper;


import com.firedata.qtacker.domain.*;
import com.firedata.qtacker.service.dto.ModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Model} and its DTO {@link ModelDTO}.
 */
@Mapper(componentModel = "spring", uses = {MakerMapper.class})
public interface ModelMapper extends EntityMapper<ModelDTO, Model> {

    @Mapping(source = "maker.id", target = "makerId")
    ModelDTO toDto(Model model);

    @Mapping(source = "makerId", target = "maker")
    Model toEntity(ModelDTO modelDTO);

    default Model fromId(Long id) {
        if (id == null) {
            return null;
        }
        Model model = new Model();
        model.setId(id);
        return model;
    }
}
