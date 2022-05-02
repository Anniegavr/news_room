package backend.service.security;


import backend.model.User;
import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;

//@Service
public interface UserService {
    void save(User user);

    User findByUsername(String username);
}

