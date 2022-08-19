package ch.noseryoung.repetition.domain.user;

import ch.noseryoung.repetition.domain.role.Role;
import ch.noseryoung.repetition.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.management.InstanceNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Log4j2
@Service @Transactional
public class UserService implements UserServiceInter, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository usersRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        log.info("Create repository for communication");
        this.userRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            log.error("user not found in the database");
            throw new UsernameNotFoundException("user not found in the database");
        }
        else{
            log.info("User found in the databse: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public void addRoleToUser(String username, String roleName){
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public List<User> findAll() {
        log.info("Get everything in User table");
        List<User> userList = new ArrayList<>();
        Iterable<User> authors = userRepository.findAll();
        authors.forEach(userList::add);
        return userList;
    }
    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("creating new user");
        log.info("created with ID: {}", user.getUserId());
        return userRepository.save(user);
    }
    @Override
    public Role createRole(Role role) {
        log.info("creating new role");
        return roleRepository.save(role);
    }

    @Override
    public User findById(Integer id) throws InstanceNotFoundException {
        log.info("Searching for User with ID {}", id);
        return userRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException("There is no user with id: {}" + id));
    }

    public User getUser(String username){
        log.info("Searching for User with username {}", username);
        return userRepository.findByUsername(username);
    }

    public void delete(Integer id) throws InstanceNotFoundException {
        log.info("Deleting entry");
        userRepository.findById(id).orElseThrow(() -> new InstanceNotFoundException("There is no user with id: " + id));
        userRepository.deleteById(id);
        log.info("Deleting user with ID {}",id);

    }

    public User update(Integer id, User newUser) throws InstanceNotFoundException {
        log.info("Updating data");
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).map(depUser -> {
                depUser.setUsername(newUser.getUsername());
                depUser.setPassword(newUser.getPassword());
                log.info("{} was updated", depUser);
                return userRepository.save(depUser);
            }).orElseThrow(() -> new InstanceNotFoundException("There is no user with id: " + id));
        } else {
            newUser.setUsername(newUser.getUsername());
            newUser.setPassword(newUser.getPassword());
            log.warn("Creating new entry as it doesnt exist");
            log.info("Created new user with the ID {}", newUser.getUserId());
            return userRepository.save(newUser);
        }
    }


}
