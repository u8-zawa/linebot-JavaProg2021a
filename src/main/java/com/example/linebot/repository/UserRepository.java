package com.example.linebot.repository;

import com.example.linebot.value.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public void insert(User user) {
        // language=sql
        String sql = "insert into users "
                + "(user_id, timestamp, display_name) "
                + "values (?, ?, ?)";

        String userId = user.getUserId();
        Timestamp timestamp = user.getTimestamp();
        String displayName = user.getDisplayName();
        jdbc.update(sql, userId, timestamp, displayName);
    }

    public void update(User user) {
        // language=sql
        String sql = "update users set "
                + "timestamp = ?, display_name = ? "
                + "where user_id = ?";

        String userId = user.getUserId();
        Timestamp timestamp = user.getTimestamp();
        String displayName = user.getDisplayName();
        jdbc.update(sql, timestamp, displayName, userId);
    }

    public void delete(String userId) {
        // language=sql
        String sql = "delete from users "
                + "where user_id = ?";

        jdbc.update(sql, userId);
    }

    public User selectOne(String userId) {
        // language=sql
        String sql = "select * from users "
                + "where user_id = ?";

        return jdbc.queryForObject(sql, new DataClassRowMapper<>(User.class), userId);
    }

    public List<User> selectAll() {
        // language=sql
        String sql = "select * from users";

        return jdbc.query(sql, new DataClassRowMapper<>(User.class));
    }

    public Boolean exists(String userId) {
        // language=sql
        String sql = "select exists("
                + "select * from users "
                + "where user_id = ?)";

        return jdbc.queryForObject(sql, Boolean.class, userId);
    }

}
