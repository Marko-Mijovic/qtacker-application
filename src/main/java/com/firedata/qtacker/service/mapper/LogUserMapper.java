package com.firedata.qtacker.service.mapper;


import com.firedata.qtacker.domain.*;
import com.firedata.qtacker.service.dto.LogUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LogUser} and its DTO {@link LogUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LogUserMapper extends EntityMapper<LogUserDTO, LogUser> {



    default LogUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        LogUser logUser = new LogUser();
        logUser.setId(id);
        return logUser;
    }
}
