package by.mariayuran.my_project_boot.service;

import by.mariayuran.my_project_boot.dto.UserDto;
import by.mariayuran.my_project_boot.model.User;

import java.util.List;

public interface UserService {
    boolean save(UserDto userDto);

    void save(User user);

    User findByName(String name);

    List<UserDto> getAll();

    void updateProfile(UserDto dto);
}
