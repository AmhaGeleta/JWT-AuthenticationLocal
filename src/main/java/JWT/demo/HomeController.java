package JWT.demo;

import JWT.demo.Domain.Role;
import JWT.demo.Domain.User;
import JWT.demo.Repository.UserRepository;
import JWT.demo.model.JwtRequest;
import JWT.demo.model.JwtResponse;
import JWT.demo.services.MyUserDetailsService;
import JWT.demo.services.RoleService;
import JWT.demo.services.UserService;
import JWT.demo.utils.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @GetMapping("/")
    public String home() {
        return "Welcome to JWT Authentication!";
    }

    @PostMapping("/login")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }

        System.out.println(" It comes here");
        final UserDetails userDetails =
                myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token =
                jwtUtility.generateToken(userDetails);
      //  if(passwordEncoder.matches(jwtRequest.getPassword(), userDetails.getPassword())){
         //   System.out.println("The Received Token " + token );
            return new JwtResponse(token);


    }
    @PostMapping("/register")
    public User addUser(@RequestBody User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userService.saveUser(user);
    }
    @PostMapping("/roles")
    public Role addRole(@RequestBody Role role){
        return roleService.saveRole(role);

    }
//    @PostMapping("login")
//    public User login(@RequestBody User user){
//        String username = user.getUsername();
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//
//    }


}




