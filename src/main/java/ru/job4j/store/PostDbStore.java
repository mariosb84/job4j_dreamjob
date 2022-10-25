package ru.job4j.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Post;
import ru.job4j.service.CityService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDbStore {

    private final static String FIND_ALL = "SELECT * FROM post ORDER BY id";

    private final static String ADD = "INSERT INTO post(name, description, city_id) VALUES (?, ?, ?)";

    private final static String UPDATE = "UPDATE post SET name = ?, description = ?, city_id = ? WHERE id = ?";

    private final static String FIND_BY_ID = "SELECT * FROM post WHERE id = ?";

    private final static String DELETE = "DELETE FROM post WHERE id = ?";

    private static final Logger LOG = LoggerFactory.getLogger(PostDbStore.class.getName());

    private final BasicDataSource pool;

    private final CityService cityService;

    public PostDbStore(BasicDataSource pool, CityService cityService) {
        this.pool = pool;
        this.cityService = cityService;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(addPost(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in  findAll() method", e);
        }
        return posts;
    }


    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in  add() method", e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(UPDATE)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getCity().getId());
            ps.setInt(4, post.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("Exception in  update() method", e);
           }
        }

    public Post findById(int id) {
        Post result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = addPost(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in  findById() method", e);
        }
        return result;
    }

    public void delete(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(DELETE)) {
            ps.setInt(1, post.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("Exception in  delete() method", e);
        }
    }

    private Post addPost(ResultSet resultset) throws SQLException {
        return new Post(
                resultset.getInt("id"),
                resultset.getString("name"),
                resultset.getString("description"),
                LocalDateTime.now(),
                cityService.findById(resultset.getInt("city_id")));
    }

}
