package backend.service.security;


import com.newsroom.lab5_3.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}

