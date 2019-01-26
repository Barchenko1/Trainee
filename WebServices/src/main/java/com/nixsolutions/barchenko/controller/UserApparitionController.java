package com.nixsolutions.barchenko.controller;

import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.entity.User;
import com.nixsolutions.barchenko.service.RoleService;
import com.nixsolutions.barchenko.service.UserService;
import com.nixsolutions.barchenko.utils.Fields;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserApparitionController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/user")
    protected ModelAndView userGet() {
        return new ModelAndView(Fields.USER_PAGE);
    }

    @GetMapping(value = "/create")
    public String createGet(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return Fields.CREATE_PAGE;
    }

    @GetMapping(value = "/admin")
    public String adminGet(Model model) {
        model.addAttribute("users", userService.findAll());
        return Fields.ADMIN_PAGE;
    }

    @PostMapping("/create")
    protected String createPost(@Valid User user,
            BindingResult bindingResult, Model model,
            @RequestParam("passwordAgain") String passwordAgain,
            HttpServletRequest request) {

        if (!checkUser(user)) {
            FieldError loginAlreadyUse = new FieldError(
                    "login", "login",
                    "login is already taken");
            bindingResult.addError(loginAlreadyUse);
        }


        if (!passwordAgain.equals(user.getPassword())) {
            FieldError checkPassword = new FieldError("password",
                    "passwordAgain",
                    "Passwords are not the same");
            bindingResult.addError(checkPassword);
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error",
                    bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("user",user);
            return Fields.CREATE_PAGE;
        }
        try {
            Role role = roleService.findById(Long.valueOf(
                    request.getParameter("role")));
            user.setRole(role);
            userService.create(user);
            model.addAttribute("error",
                    user.getLogin() + " has been created");
            model.addAttribute("users", userService.findAll());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getCause());
        }
        return Fields.ADMIN_PAGE;
    }

    @GetMapping(value = "/edit")
    public String editGet(Model model,
            @ModelAttribute("editLogin") String login) {
        User user = userService.findByLogin(login);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());
        return Fields.EDIT_PAGE;
    }

    @PostMapping("/edit")
    protected String editPost(@Valid User user,
            BindingResult bindingResult, Model model,
            @RequestParam("passwordAgain") String passwordAgain,
            HttpServletRequest request) {

        String login = user.getLogin();
        User u = userService.findByLogin(login);
        user.setUserId(u.getUserId());

        if (!passwordAgain.equals(user.getPassword())) {
            FieldError checkPassword = new FieldError("password",
                    "passwordAgian",
                    "Passwords are not the same");
            bindingResult.addError(checkPassword);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("error",
                    bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("editLogin", login);
            return Fields.EDIT_PAGE;
        }
        try {
            Role role = roleService.findById(
                    Long.valueOf(request.getParameter("role")));
            user.setRole(role);
            userService.update(user);
            model.addAttribute("users", userService.findAll());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getCause());
        }
        model.addAttribute("error", user.getLogin() + " was update");
        return Fields.ADMIN_PAGE;
    }

    @GetMapping(value = "/delete")
    public String delete(@RequestParam("deleteLogin") String deleteLogin) {
        User user = userService.findByLogin(deleteLogin);
        userService.remove(user);
        return "redirect:/" + Fields.ADMIN_PAGE;
    }

    @GetMapping(value = "/registration")
    public ModelAndView registrationGet() {
        return new ModelAndView(Fields.REGISTRATION_PAGE);
    }

    @PostMapping("/registration")
    protected String registrationPost(
            @Valid User user, BindingResult bindingResult, Model model,
            @RequestParam("passwordAgain") String passwordAgain,
            @RequestParam("g-recaptcha-response") String response) {
        if (!checkUser(user)) {
            FieldError loginAlreadyUse = new FieldError(
                    "login", "login",
                    "login is already taken");
            bindingResult.addError(loginAlreadyUse);
        }

        if (!passwordAgain.equals(user.getPassword())) {
            FieldError checkPassword = new FieldError("password",
                    "passwordAgain",
                    "Passwords are not the same");
            bindingResult.addError(checkPassword);
        }

        if (!chkCaptch(response)) {
            FieldError loginAlreadyUse = new FieldError("login",
                    "login", "Captcha is not success");
            bindingResult.addError(loginAlreadyUse);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("error",
                    bindingResult.getFieldError().getDefaultMessage());
            return Fields.REGISTRATION_PAGE;
        }


        try {
            user.setRole(roleService.findById(2L));
            userService.create(user);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getCause());
        }
        model.addAttribute("error",
                "Well come! Enter to your account");
        return Fields.LOGIN_PAGE;
    }

    private boolean chkCaptch(String responce) {

        final String url = "https://www.google.com/recaptcha/api/siteverify";
        final String secret = "6LdBXn8UAAAAAO3R5XDGoBiR4vSPvPgSwCWxDWXl";
        final String userAgent = "Mozilla/5.0";

        if (responce == null || "".equals(responce)) {
            return false;
        }

        try {
            URL obj = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", userAgent);
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String postParams = "secret=" + secret + "&response="
                    + responce;
            conn.setDoOutput(true);

            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());

            outStream.flush();
            outStream.close();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode=" + responseCode);

            InputStream is = conn.getInputStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();


            System.out.println("Response: " + jsonObject);

            boolean success = jsonObject.getBoolean("success");
            return success;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkUser(User user) {
        for (User u : userService.findAll()) {
            if (u.getLogin().equals(user.getLogin())) {
                return false;
            }
        }
        return true;
    }


}
