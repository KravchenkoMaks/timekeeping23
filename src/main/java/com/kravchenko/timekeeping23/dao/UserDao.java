package com.kravchenko.timekeeping23.dao;

import com.kravchenko.timekeeping23.entity.Role;
import com.kravchenko.timekeeping23.entity.User;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserDao implements Dao<Integer, User> {
    private static final UserDao INSTANCE = new UserDao();
    private static final String FIND_ALL_SQL = """
            SELECT u.id,
            u.first_name,
            u.last_name,
            u.email,
            u.password,
            r.role_name,
            u.image
            FROM users u
            JOIN role r on r.id = u.role_id
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + "WHERE u.id = ?";
    private static final String SAVE_SQL = "INSERT INTO users(first_name, last_name, email, password,role_id, image) VALUES (?,?,?,?,?,?)";
    private static final String GET_BY_EMAIL_SQL = FIND_ALL_SQL + "WHERE email = ?";
    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL = FIND_ALL_SQL + "WHERE email = ? AND password = ?";
    private static final String UPDATE_SQL = "UPDATE users SET first_name = ?, last_name = ?, role_id = ? WHERE id = ?";

    private UserDao() {
    }


    @Override
    @SneakyThrows
    public List<User> findAll(Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> findById(Connection connection, Integer id) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUser(resultSet));
            }
            return Optional.empty();
        }
    }

    @SneakyThrows
    public Optional<User> findByEmail(Connection connection, String email) {
        try (var preparedStatement = connection.prepareStatement(GET_BY_EMAIL_SQL)) {
            preparedStatement.setObject(1, email);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUser(resultSet));
            }
            return Optional.empty();
        }
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(Connection connection, String email, String password) {
        try (var preparedStatement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            preparedStatement.setObject(1, email);
            preparedStatement.setObject(2, password);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUser(resultSet));
            }
            return Optional.empty();
        }
    }

    @Override
    @SneakyThrows
    public User save(Connection connection, User entity) {
        try (var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            int k = 1;
            preparedStatement.setObject(k++, entity.getFirstName());
            preparedStatement.setObject(k++, entity.getLastName());
            preparedStatement.setObject(k++, entity.getEmail());
            preparedStatement.setObject(k++, entity.getPassword());
            preparedStatement.setObject(k++, Integer.valueOf(entity.getRole().getValue()));
            preparedStatement.setObject(k, entity.getImage());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Integer.class));

            return entity;
        }
    }

    @SneakyThrows
    @Override
    public void update(Connection connection, User entity) {
        try (var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            int k = 1;
            preparedStatement.setObject(k++,entity.getFirstName());
            preparedStatement.setObject(k++,entity.getLastName());
            preparedStatement.setObject(k++,Integer.valueOf(entity.getRole().getValue()));
            preparedStatement.setObject(k,entity.getId());

            preparedStatement.executeUpdate();
        }
    }


    @Override
    public boolean delete(Connection connection, Integer id) {
        return false;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("first_name", String.class),
                resultSet.getObject("last_name", String.class),
                resultSet.getObject("email", String.class),
                resultSet.getObject("password", String.class),
                Role.valueOf(resultSet.getObject("role_name", String.class).toUpperCase()),
                resultSet.getObject("image", String.class)
        );
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
