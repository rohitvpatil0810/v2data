package com.rohitvpatil0810.v2data.modules.users.mapper;

import com.rohitvpatil0810.v2data.modules.auth.dto.RegistrationRequest;
import com.rohitvpatil0810.v2data.modules.auth.dto.RegistrationResponse;
import com.rohitvpatil0810.v2data.modules.users.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUserFromRegistrationRequest(RegistrationRequest registrationRequest);

    RegistrationResponse toRegistrationResponseFromUser(User user);
}
