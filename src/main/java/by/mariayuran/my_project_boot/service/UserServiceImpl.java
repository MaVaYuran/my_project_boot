package by.mariayuran.my_project_boot.service;

import by.mariayuran.my_project_boot.dao.UserRepository;
import by.mariayuran.my_project_boot.dto.UserDto;
import by.mariayuran.my_project_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {

        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    @Override
    public boolean save(UserDto userDto) {
        if (!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())) {
            throw new RuntimeException("Passwords are not equals");
        }
        User user = User.builder()
                .name(userDto.getName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role("ROLE_CLIENT").build();

        userRepository.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);

    }

    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public User findByName(String username) {
        return userRepository.findFirstByName(username);
    }

    @Override
    @Transactional
    public void updateProfile(UserDto dto) {
        User savedUser = userRepository.findFirstByName(dto.getName());
        if (savedUser == null) {
            throw new RuntimeException("User not found by name " + dto.getName());
        }
        boolean isChanged = false;
        if(dto.getPassword()!= null && !dto.getPassword().isEmpty()) {
            savedUser.setPassword(encoder.encode(dto.getPassword()));
            isChanged=true;
        }
        if(!Objects.equals(dto.getEmail(), savedUser.getEmail())){
            savedUser.setEmail(dto.getEmail());
            isChanged=true;
        }
        if(isChanged) {
            userRepository.save(savedUser);
        }
    }


    private UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
