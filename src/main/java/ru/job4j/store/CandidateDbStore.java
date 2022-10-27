package ru.job4j.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDbStore {

    private final static String FIND_ALL_C = "SELECT * FROM candidates ORDER BY id";

    private final static String ADD_C = "INSERT INTO candidates(name, description, created, photo) VALUES (?, ?, ?, ?)";

    private final static String UPDATE_C = "UPDATE candidates SET name = ?, description = ?, created = ?, photo = ? WHERE id = ?";

    private final static String FIND_BY_ID_C = "SELECT * FROM candidates WHERE id = ?";

    private final static String DELETE_C = "DELETE FROM candidates WHERE id = ?";

    private static final Logger LOG_C = LoggerFactory.getLogger(CandidateDbStore.class.getName());

    private final BasicDataSource pool;

    public CandidateDbStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_ALL_C)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(addCandidate(it));
                }
            }
        } catch (Exception e) {
            LOG_C.error("Exception in  findAll() method", e);
        }
        return candidates;
    }


    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(ADD_C,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDesc());
            ps.setTimestamp(3,  Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, (candidate.getPhoto()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG_C.error("Exception in  add() method", e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(UPDATE_C)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDesc());
            ps.setTimestamp(3,  Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG_C.error("Exception in  update() method", e);
        }
    }

    public Candidate findById(int id) {
        Candidate result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID_C)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = addCandidate(it);
                }
            }
        } catch (Exception e) {
            LOG_C.error("Exception in  findById() method", e);
        }
        return result;
    }

    public void delete(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(DELETE_C)) {
            ps.setInt(1, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG_C.error("Exception in  delete() method", e);
        }
    }

    private Candidate addCandidate(ResultSet resultset) throws SQLException {
        return new Candidate(
                resultset.getInt("id"),
                resultset.getString("name"),
                resultset.getString("description"),
                resultset.getTimestamp("created").toLocalDateTime(),
                resultset.getBytes("photo"));
    }
}
