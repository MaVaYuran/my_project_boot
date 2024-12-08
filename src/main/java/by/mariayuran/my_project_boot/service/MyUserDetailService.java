package by.mariayuran.my_project_boot.service;

import by.mariayuran.my_project_boot.config.MyUserDetails;
import by.mariayuran.my_project_boot.dao.UserRepository;
import by.mariayuran.my_project_boot.dto.UserDto;
import by.mariayuran.my_project_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByName(username);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
