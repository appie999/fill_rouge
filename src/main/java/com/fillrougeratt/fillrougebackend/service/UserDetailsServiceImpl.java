package com.fillrougeratt.fillrougebackend.service;

import com.fillrougeratt.fillrougebackend.model.User;
import com.fillrougeratt.fillrougebackend.model.UserPrincipal;
import com.fillrougeratt.fillrougebackend.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepo userRepository;
    public UserDetailsServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        if ( user == null ) {
            logger.info("user not found");
            throw new UsernameNotFoundException("this user is not found");
        }


        return new UserPrincipal(user);
    }

}
