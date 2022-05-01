package backend.service.security;


import backend.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}

