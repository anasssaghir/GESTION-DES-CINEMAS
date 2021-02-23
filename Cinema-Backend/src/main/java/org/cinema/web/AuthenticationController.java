package org.cinema.web;

import org.cinema.dao.UserRepository;
import org.cinema.entities.AuthRequest;
import org.cinema.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/authenticate")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HashMap<String, Object> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        //Creating the ObjectMapper object
        HashMap<String, Object> map = new HashMap<>();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            map.put("auth", true);
            map.put("token", jwtUtils.generateToken(authRequest.getUsername()));
        } catch (Exception e) {
            map.put("auth", false);
        }
        return map;
    }
}
