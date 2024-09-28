package com.malvin.EComm.DataInitializer;

import com.malvin.EComm.model.Role;
import com.malvin.EComm.model.User;
import com.malvin.EComm.repository.CategoryRepository;
import com.malvin.EComm.repository.RoleRepository;
import com.malvin.EComm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
       Set<String> defaultRoles = Set.of("ROLE_ADMIN","ROLE_USER");
        createDefaultUserIfNotExists();
        createDefaultRoleIfUserNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }
    private void createDefaultAdminIfNotExists(){
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for(int i=1; i<=2;i++){
            String defaultEmail ="admin"+i+"@email.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("admin"+ i);
            user.setLastName("surname"+i);
            user.setEmail(defaultEmail);
            user.setRoles(Set.of(adminRole));
            user.setPassword(passwordEncoder.encode("1234"));
            userRepository.save(user);
            System.out.println("default admin user "+ i + " created");
        }
    }
    private void createDefaultUserIfNotExists(){
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for(int i=1; i<=5;i++){
            String defaultEmail ="user"+i+"@email.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("name"+ i);
            user.setLastName("surname"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("1234"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("user "+ i + " created");
        }
    }
    private void createDefaultRoleIfUserNotExists(Set<String> roles){

        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
