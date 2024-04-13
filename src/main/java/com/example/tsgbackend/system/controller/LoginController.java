package com.example.tsgbackend.system.controller;

import com.example.tsgbackend.system.bean.User;
import com.example.tsgbackend.system.bean.utils.LoginForm;
import com.example.tsgbackend.system.bean.utils.RegisterForm;
import com.example.tsgbackend.system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> userLogin(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        System.out.println("login called");
        try {
            if(userExists(loginForm.getUser_name()) == false) {
                throw new Exception("user not exist");
            }
            List<User> users = userService.selectUserByName(loginForm.getUser_name());
            User user = users.get(0);

            if(passwordCheck(loginForm.getUser_pwd(), user) != true) {
                throw new Exception("incorrect password");
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);

            return ResponseEntity.status(HttpStatus.OK).headers(setHeaders()).body(setResponseBody("login success"));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(setHeaders()).body(setResponseBody(e.getMessage()));
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> userRegister(@RequestBody RegisterForm registerForm) {
        try {
            if(userExists(registerForm.getUser_name())) {
                throw new Exception("user name exist");
            }
            User newUser = new User();
            newUser.setUser_id(UUID.randomUUID().toString());
            newUser.setUser_name(registerForm.getUser_name());

            if(passwordValid(registerForm.getUser_pwd())) {
                newUser.setUser_pwd(registerForm.getUser_pwd());
            }
            newUser.setPhone(registerForm.getPhone());

            userService.insertUser(newUser);

            return ResponseEntity.status(HttpStatus.OK).headers(setHeaders()).body(setResponseBody("sign in success"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(setHeaders()).body(setResponseBody(e.getMessage()));
        }
    }

    private Map<String, Object> setResponseBody(String info, Object... args) {
        Map<String, Object> response = new HashMap<>();
        response.put("info", info);
        for(int i = 0; i < args.length; i++) {
            response.put("arg" + i, args[i]);
        }
        return response;
    }

    private boolean userExists(String user_name) throws Exception {
        List<User> fetchResult = userService.selectUserByName(user_name);
        if(fetchResult.isEmpty()) return false;
        else return true;
    }

    private boolean passwordCheck(String password, User user) {
        if(password.equals(user.getUser_pwd())) return true;
        else return false;
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }

    private boolean passwordValid(String password) {
        boolean hasUpperCase = false;
        boolean hasDigit = false;

        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Pattern digitPattern = Pattern.compile("[0-9]");

        Matcher upperCaseMatcher = upperCasePattern.matcher(password);
        Matcher digitMatcher = digitPattern.matcher(password);

        if(upperCaseMatcher.find()) {
            hasUpperCase = true;
        }
        if(digitMatcher.find()) {
            hasDigit = true;
        }
        return hasDigit && hasUpperCase;
    }

    private boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
