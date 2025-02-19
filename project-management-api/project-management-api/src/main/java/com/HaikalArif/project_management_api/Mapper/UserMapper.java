package com.HaikalArif.project_management_api.Mapper;

import com.HaikalArif.project_management_api.Dto.UserDto;
import com.HaikalArif.project_management_api.Model.User;

public class UserMapper {
    // Convert Model to DTO
    public static UserDto toUserDto (User user){
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRole(user.getRole());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    // Convert the DTO to Model
    public static User toUserModel (UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setName(userDto.getName());
        newUser.setRole(userDto.getRole());
        newUser.setPassword(userDto.getPassword());
        return newUser;
    }

}
