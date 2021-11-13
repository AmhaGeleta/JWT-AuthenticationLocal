package JWT.demo.services;

import JWT.demo.Domain.Role;
import JWT.demo.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

}
