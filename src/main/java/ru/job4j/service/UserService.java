package ru.job4j.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.store.UserDbStore;

import java.util.Optional;

@ThreadSafe
@Service
public class UserService {

    private final UserDbStore store;

    public UserService(UserDbStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        store.add(user);
        return Optional.empty();
    }

}
