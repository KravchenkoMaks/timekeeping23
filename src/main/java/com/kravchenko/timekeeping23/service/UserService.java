package com.kravchenko.timekeeping23.service;

import com.kravchenko.timekeeping23.dao.UserDao;
import com.kravchenko.timekeeping23.dto.CreateUserDto;
import com.kravchenko.timekeeping23.dto.ReadUserDto;
import com.kravchenko.timekeeping23.dto.UpdateUserDto;
import com.kravchenko.timekeeping23.exception.DBException;
import com.kravchenko.timekeeping23.exception.ValidationException;
import com.kravchenko.timekeeping23.mapper.CreateReadUserDtoMapper;
import com.kravchenko.timekeeping23.mapper.CreateUpdateUserMapper;
import com.kravchenko.timekeeping23.mapper.CreateUserMapper;
import com.kravchenko.timekeeping23.util.ConnectionManager;
import com.kravchenko.timekeeping23.validator.CreateUserValidator;
import com.kravchenko.timekeeping23.validator.UpdateUserValidator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UpdateUserValidator updateUserValidator = UpdateUserValidator.getInstance();
    private final ImageService imageService = ImageService.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final CreateReadUserDtoMapper readUserDtoMapper = CreateReadUserDtoMapper.getInstance();
    private final CreateUpdateUserMapper updateUserMapper = CreateUpdateUserMapper.getInstance();

    private UserService() {
    }

    public static UserService getInstance(){
        return INSTANCE;
    }

    public List<ReadUserDto> findAll() throws DBException {
        try (var connection = connectionManager.getConnection()) {
            return userDao.findAll(connection).stream()
                    .map(readUserDtoMapper::mapFrom)
                    .collect(toList());
        } catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Cannot find all users", ex);
        }
    }
    public Optional<ReadUserDto> findById(Integer id) throws DBException {
        try (var connection = connectionManager.getConnection()) {
            return userDao.findById(connection, id)
                    .map(readUserDtoMapper::mapFrom);
        }catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Can't find user", ex);
        }
    }

    public Optional<ReadUserDto> findByEmail(String email) throws DBException {
        try (var connection = connectionManager.getConnection()) {
            return userDao.findByEmail(connection, email)
                    .map(readUserDtoMapper::mapFrom);
        }catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Can't find user", ex);
        }
    }

    public Optional<ReadUserDto> login(String email, String password) throws DBException {
        try (var connection = connectionManager.getConnection()) {
            return userDao.findByEmailAndPassword(connection, email, password)
                    .map(readUserDtoMapper::mapFrom);
        } catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Cannot login user", ex);
        }
    }

    public Integer create(CreateUserDto createUserDto) throws DBException, IOException {
        //  validator
        //  map
        //  userDao.save
        //  return id
        var validationResult = createUserValidator.isValid(createUserDto);
        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }

        String image = createUserDto.getImage().getSubmittedFileName();
        var userEntity = createUserMapper.mapFrom(createUserDto);

        if(!"".equals(image)){
            imageService.upload(userEntity.getImage(), createUserDto.getImage().getInputStream());
        }

        try (var connection = connectionManager.getConnection()) {
            userDao.save(connection, userEntity);
            return userEntity.getId();
        }catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Cannot create user", ex);
        }
    }

    public void update(UpdateUserDto updateUserDto) throws DBException {
        var validationResult = updateUserValidator.isValid(updateUserDto);
        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }

        var userEntity = updateUserMapper.mapFrom(updateUserDto);

        try (var connection = connectionManager.getConnection()) {
            userDao.update(connection, userEntity);
        }catch (SQLException ex) {
            // (1) write to log: log.error(..., ex);
            ex.printStackTrace();
            // (2) throw your own exception
            throw new DBException("Can't update user", ex);
        }
    }
}
