package com.kravchenko.timekeeping23.service;

import com.kravchenko.timekeeping23.dao.ActivityDao;
import com.kravchenko.timekeeping23.dto.CreateActivityDto;
import com.kravchenko.timekeeping23.dto.ReadActivityDto;
import com.kravchenko.timekeeping23.dto.UpdateActivityDto;
import com.kravchenko.timekeeping23.exception.DBException;
import com.kravchenko.timekeeping23.exception.ValidationException;
import com.kravchenko.timekeeping23.mapper.CreateActivityMapper;
import com.kravchenko.timekeeping23.mapper.CreateReadActivityDtoMapper;
import com.kravchenko.timekeeping23.mapper.CreateUpdateActivityMapper;
import com.kravchenko.timekeeping23.util.ConnectionManager;
import com.kravchenko.timekeeping23.validator.CreateActivityValidator;
import com.kravchenko.timekeeping23.validator.UpdateActivityValidator;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ActivityService {
    private static final ActivityService INSTANCE = new ActivityService();
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final ActivityDao activityDao = ActivityDao.getInstance();
    private final CreateReadActivityDtoMapper createReadActivityDtoMapper = CreateReadActivityDtoMapper.getInstance();
    private final CreateActivityMapper createActivityMapper = CreateActivityMapper.getInstance();

    private final CreateUpdateActivityMapper updateActivityMapper = CreateUpdateActivityMapper.getInstance();
    private final CreateActivityValidator createActivityValidator = CreateActivityValidator.getInstance();
    private final UpdateActivityValidator updateActivityValidator = UpdateActivityValidator.getInstance();

    public List<ReadActivityDto> findAll() throws DBException {
        try (var connection = connectionManager.getConnection()) {
            return activityDao.findAll(connection).stream()
                    .map(createReadActivityDtoMapper::mapFrom)
                    .collect(toList());
        } catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Can't find activities", ex);
        }
    }


    public List<ReadActivityDto> findAllUserActivity(Integer id) throws DBException {
        try (var connection = connectionManager.getConnection()) {
            return activityDao.findAllUserActivity(connection, id
                    ).stream()
                    .map(createReadActivityDtoMapper::mapFrom)
                    .collect(toList());
        } catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Can't find user's activities", ex);
        }
    }

    public Optional<ReadActivityDto> findById(Integer id) throws DBException {
        try (var connection = connectionManager.getConnection()) {
            return activityDao.findById(connection, id)
                    .map(createReadActivityDtoMapper::mapFrom);
        } catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Cannot find activity", ex);
        }
    }

    public Integer create(CreateActivityDto createActivityDto) throws DBException {
        var validationResult = createActivityValidator.isValid(createActivityDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var activity = createActivityMapper.mapFrom(createActivityDto);
        try (var connection = connectionManager.getConnection()) {
            activityDao.save(connection, activity);
            return activity.getId();
        } catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Cannot create activity", ex);
        }

    }

    public void update(UpdateActivityDto updateActivityDto) throws DBException {
        var validationResult = updateActivityValidator.isValid(updateActivityDto);
        if(!validationResult.isValid()){
            throw  new ValidationException(validationResult.getErrors());
        }

        var activityEntity = updateActivityMapper.mapFrom(updateActivityDto);

        try (var connection = connectionManager.getConnection()) {
            activityDao.update(connection, activityEntity);
        }catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Can't update activity", ex);
        }


    }

    public boolean delete(Integer id) throws DBException {
        try (var connection = connectionManager.getConnection()) {
            return  activityDao.delete(connection, id);
        }catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Can't delete activity", ex);
        }
    }

    public static ActivityService getInstance() {
        return INSTANCE;
    }
}
