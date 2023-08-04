package com.kravchenko.timekeeping23.servlet;

import com.kravchenko.timekeeping23.dto.ReadUserDto;
import com.kravchenko.timekeeping23.dto.UpdateUserDto;
import com.kravchenko.timekeeping23.entity.Role;
import com.kravchenko.timekeeping23.exception.DBException;
import com.kravchenko.timekeeping23.exception.ValidationException;
import com.kravchenko.timekeeping23.service.UserService;
import com.kravchenko.timekeeping23.util.IdHelper;
import com.kravchenko.timekeeping23.util.JspHelper;
import com.kravchenko.timekeeping23.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet(UrlPath.USER_UPDATE)
public class UserUpdateServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page", "UpdateUser");
        try {
            userService.findById(IdHelper.createId(req))
                    .ifPresent(user -> onUserSuccess(user, req, resp));
        } catch (Exception ex) {
            req.setAttribute("ex", ex);
        }
        req.getRequestDispatcher(JspHelper.ERROR).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var address = JspHelper.getPath("error");
        var id = req.getParameter("id");

        var updateUserDto = UpdateUserDto.builder()
                .id(id)
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .email(req.getParameter("email"))
                .role(Role.valueOf(req.getParameter("role")))
                .build();
        try {
            userService.update(updateUserDto);
            resp.sendRedirect(UrlPath.USERS);
        } catch (ValidationException ex) {
            req.setAttribute("errors", ex.getErrors());
            req.setAttribute("id", id);
            doGet(req, resp);
        } catch (DBException ex) {
            req.setAttribute("ex", ex);
            req.getRequestDispatcher(JspHelper.ERROR).forward(req, resp);
        }
    }

    @SneakyThrows
    private void onUserSuccess(ReadUserDto user, HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("user", user);
        req.getRequestDispatcher(JspHelper.getPath("user_update")).forward(req, resp);
    }
}
