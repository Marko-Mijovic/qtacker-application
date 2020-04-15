package com.firedata.qtacker.service.mapper;


import com.firedata.qtacker.domain.*;
import com.firedata.qtacker.service.dto.MakerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Maker} and its DTO {@link MakerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MakerMapper extends EntityMapper<MakerDTO, Maker> {


    @Mapping(target = "models", ignore = true)
    @Mapping(target = "removeModels", ignore = true)
    Maker toEntity(MakerDTO makerDTO);

    default Maker fromId(Long id) {
        if (id == null) {
            return null;
        }
        Maker maker = new Maker();
        maker.setId(id);
        return maker;
    }
}
