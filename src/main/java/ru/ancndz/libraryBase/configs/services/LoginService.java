package ru.ancndz.libraryBase.configs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ancndz.libraryBase.configs.repos.UserRepository;
import ru.ancndz.libraryBase.content.entity.LibraryUser;

@Service
public class LoginService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** by email actualy **/
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        /*if (libraryUser == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return libraryUser;
        }*/
        return this.userRepository.findByEmail(s);
    }

    public LibraryUser loadByAuth(Authentication authentication) throws UsernameNotFoundException {
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = loadUserByUsername(authentication.getName());
                if (userDetails instanceof LibraryUser) {
                    return (LibraryUser) userDetails;
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
