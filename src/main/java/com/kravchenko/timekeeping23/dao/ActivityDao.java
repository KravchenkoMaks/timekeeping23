package com.kravchenko.timekeeping23.dao;

import com.kravchenko.timekeeping23.dto.ReadUserDto;
import com.kravchenko.timekeeping23.entity.Activity;
import com.kravchenko.timekeeping23.entity.ActivityCategory;
import com.kravchenko.timekeeping23.entity.ActivityState;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ActivityDao implements Dao<Integer, Activity> {
    private static final ActivityDao INSTANCE = new ActivityDao();
    private static final String FIND_ALL_SQL = """
            select act.id,
            act.activity_name,
            c.category_name,
            act.create_date,
            act.done_date,
            act.effort_hrs,
            s.state_name,
            act.user_id,
            u.email,
            act.description
            FROM activity act
            join category c on c.id = act.category_id
            join state s on s.id = act.state_id
            join users u on u.id = act.user_id
            """;
    private static final String FIND_ALL_USER_ACTIVITY_SQL = FIND_ALL_SQL + "WHERE user_id = ?";
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + "WHERE act.id = ?";
    private static final String SAVE_ACTIVITY_SQL = """
            INSERT INTO activity
            (
             activity_name,
             category_id,
             create_date,
             done_date,
             effort_hrs,
             user_id,
             state_id,
             description
             )
             VALUES
             (
             ?,
             (SELECT c.id FROM category c WHERE c.category_name = ?),
             ?,
             ?,
             ?,
             (SELECT u.id FROM users u WHERE u.email = ?),
             (SELECT s.id FROM state s WHERE s.state_name = ?),
             ?
             )
            """;
    private static final String UPDATE_ACTIVITY_SQL = """
            UPDATE activity
            SET activity_name = ?,
                description   = ?,
                category_id   = (SELECT id FROM category WHERE category_name = ?),
                state_id      = (SELECT id FROM state WHERE state_name = ?),
                done_date     = ?,
                effort_hrs    = ?
            WHERE id = ?
            """;
    private static final String DELETE_ACTIVITY_SQL = "DELETE FROM activity WHERE id = ?";

    private ActivityDao() {

    }

    @Override
    @SneakyThrows
    public List<Activity> findAll(Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Activity> activities = new ArrayList<>();
            while (resultSet.next()) {
                activities.add(buildActivity(resultSet));
            }
            return activities;
        }
    }

    @SneakyThrows
    public List<Activity> findAllUserActivity(Connection connection, Integer id) {
        try (var preparedStatement = connection.prepareStatement(FIND_ALL_USER_ACTIVITY_SQL)) {
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();
            List<Activity> userActivities = new ArrayList<>();
            while (resultSet.next()) {
                userActivities.add(buildActivity(resultSet));
            }
            return userActivities;
        }
    }

    @SneakyThrows
    @Override
    public Optional<Activity> findById(Connection connection, Integer id) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildActivity(resultSet));
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public boolean delete(Connection connection, Integer id) {
        try (var preparedStatement = connection.prepareStatement(DELETE_ACTIVITY_SQL)) {
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    @Override
    public Activity save(Connection connection, Activity activity) {
        try (var preparedStatement =
                     connection.prepareStatement(SAVE_ACTIVITY_SQL, RETURN_GENERATED_KEYS)) {
            int k = 1;
            preparedStatement.setObject(k++, activity.getActivityName());
            preparedStatement.setObject(k++, activity.getCategory().name().toLowerCase());
            preparedStatement.setObject(k++, activity.getCreateDate());
            preparedStatement.setObject(k++, activity.getDoneDate());
            preparedStatement.setObject(k++, activity.getEffortHrs());
            preparedStatement.setObject(k++, activity.getUser().getEmail());
            preparedStatement.setObject(k++, activity.getState().name().toLowerCase());
            preparedStatement.setObject(k, activity.getDescription());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            activity.setId(generatedKeys.getObject("id", Integer.class));

            return activity;
        }
    }

    @SneakyThrows
    @Override
    public void update(Connection connection, Activity activity) {
        try (var preparedStatement = connection.prepareStatement(UPDATE_ACTIVITY_SQL)) {
            int k = 1;
            preparedStatement.setObject(k++, activity.getActivityName());
            preparedStatement.setObject(k++, activity.getDescription());
            preparedStatement.setObject(k++, activity.getCategory().name().toLowerCase());
            preparedStatement.setObject(k++, activity.getState().name().toLowerCase());
            preparedStatement.setObject(k++, activity.getDoneDate());
            preparedStatement.setObject(k++, activity.getEffortHrs());
            preparedStatement.setObject(k++, activity.getId());

            preparedStatement.executeUpdate();
        }

    }

    private Activity buildActivity(ResultSet resultSet) throws SQLException {
        return new Activity(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("activity_name", String.class),
                ActivityCategory.valueOf(resultSet.getObject("category_name", String.class).toUpperCase()),
                resultSet.getObject("create_date", Date.class).toLocalDate(),
                resultSet.getObject("done_date", Date.class).toLocalDate(),
                resultSet.getObject("effort_hrs", Integer.class),
                buildUserDto(resultSet),
                ActivityState.valueOf(resultSet.getObject("state_name", String.class).toUpperCase()),
                resultSet.getObject("description", String.class)
        );
    }

    private ReadUserDto buildUserDto(ResultSet resultSet) throws SQLException {
        return ReadUserDto.builder()
                .id(String.valueOf(resultSet.getObject("user_id", Integer.class)))
                .email(resultSet.getObject("email", String.class))
                .build();
    }


    public static ActivityDao getInstance() {
        return INSTANCE;
    }
}
