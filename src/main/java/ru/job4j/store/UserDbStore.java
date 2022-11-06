package ru.job4j.store;


import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDbStore {

    private final static String ADD_USER = "INSERT INTO users(email, password, name) VALUES (?, ?, ?)";

    private final static String FIND_BY_EMAIL_AND_PASSWORD_USER  = "SELECT * FROM users WHERE email = ? AND password = ?";

    private static final Logger LOG_USER = LoggerFactory.getLogger(CandidateDbStore.class.getName());

    private final BasicDataSource pool;

    public UserDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(ADD_USER,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            result = Optional.of(user);
        } catch (Exception e) {
            LOG_USER.error("Exception in  add() method", e);
        }
        return result;
    }

    public Optional<User>  findUserByEmailAndPassword(String email, String password) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD_USER)) {
            ps.setString(1, email);
                    ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(addUser(it));
                }
            }
        } catch (Exception e) {
            LOG_USER.error("Exception in  findUserByEmailAndPassword() method", e);
        }
        return result;
    }

    private User addUser(ResultSet resultset) throws SQLException {
        return new User(
                resultset.getInt("id"),
                resultset.getString("email"),
                resultset.getString("password"),
                resultset.getString("name")
        );
    }

}
