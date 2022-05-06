package backend.web;

import backend.model.User;
import backend.service.security.SecurityService;
import backend.service.security.UserService;
import backend.service.security.UserServiceImpl;
import backend.service.security.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/newsroom";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/newsroom"})
    public String welcome(Model model) {
        return "newsroom";
    }

    @PostMapping("/subscribe")
    public String subscribe(@ModelAttribute("subscribeForm") User subscribeForm, BindingResult bindingResult) {
        userValidator.validate(subscribeForm, bindingResult);

        userServiceImpl.setUserSubscribedById(subscribeForm.getId());

        mailService.sendMail("Subscription to News Room", subscribeForm.getEmail(), "", "", "Thank you for subscribing to News Room!");

        return "redirect:/newsroom";
    }
    
        //Admins part///////////////////////

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("users/user/roles/{id}")
    public String listURoles(@PathVariable("id") Long id, Model model) {
        User user = userService.get(id);
        Set<Role> listURoles = user.getRoles();
        model.addAttribute("listURoles", listURoles);

        return "roles";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.get(id);
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_form";
    }


    @PostMapping("/users/save")
    public String saveUser(User user) {
        userService.save(user);

        return "redirect:/users";
    }

}
