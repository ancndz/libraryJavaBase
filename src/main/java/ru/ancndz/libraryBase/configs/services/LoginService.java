package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.StaffRepository;
import ru.ancndz.libraryBase.configs.repos.UserRepository;
import ru.ancndz.libraryBase.content.entity.User;
import ru.ancndz.libraryBase.content.entity.UserExtras;
import ru.ancndz.libraryBase.content.libraryEnvironment.Staff;

@Service
public class LoginService implements UserDetailsService {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoginService(StaffRepository staffRepository, UserRepository userRepository) {
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
    }

    /** by email actualy **/
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(s);
        if (user == null) {
            Staff staff = this.staffRepository.findByEmail(s);
            if (staff == null) {
                throw new UsernameNotFoundException("User not found");
            } else {
                return staff;
            }
        } else {
            return user;
        }
    }

    public <T extends UserDetails> T loadByAuth(Authentication authentication) throws UsernameNotFoundException{
        String username = "Stranger";
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = loadUserByUsername(authentication.getName());
                if (userDetails instanceof User) {
                    return (T) userDetails;
                } else if (userDetails instanceof Staff) {
                    return (T) userDetails;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
