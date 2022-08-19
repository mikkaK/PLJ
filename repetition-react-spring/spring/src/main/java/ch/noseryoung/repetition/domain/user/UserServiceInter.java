package ch.noseryoung.repetition.domain.user;

import ch.noseryoung.repetition.domain.role.Role;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface UserServiceInter {
    User create(User user);
    Role createRole(Role role);
    void addRoleToUser(String username, String roleName);
    User findById(Integer id) throws InstanceNotFoundException;
    List<User>findAll();
}
