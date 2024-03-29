package backend.controllers;

import backend.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class NewsPostController {
    private static final Logger logger = LoggerFactory.getLogger(NewsPostController.class);

    @Autowired
    private SecurityService securityService;

    @GetMapping("/see_protected")
    public String seeProtectedNews(Model model, String error, String news) {
        if (securityService.isAuthenticated()) {
            logger.info("User trying to see protected news;");
            model.addAttribute("message", "\n" +
                    "Would you like to ask for password from administrators?\n" +
                    "If you already know the password, enter it below.");

        }
        if (error != null){
            model.addAttribute("error", "Your don't have access to this page.");

        }
        return "news";
    }

}
