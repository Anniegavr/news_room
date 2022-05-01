package backend.controllers.smtp.email;

import backend.controllers.FriendshipController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import backend.general.payload.request.EmailRequest;
import backend.service.security.services.MailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController {
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
    private final MailService mailService;

    @RequestMapping(value = "/sendemail")
    public String sendMail(@RequestBody EmailRequest emailRequest)  {
        mailService.sendMail(
                emailRequest.getSubject(),
                emailRequest.getToAddresses(),
                emailRequest.getCcAddresses(),
                emailRequest.getBccAddresses(),
                emailRequest.getBody());
        logger.info("Email with subject [",emailRequest.getSubject(), "] sent to [",
                emailRequest.getToAddresses(),"]");
        return "Email sent successfully";
    }
}
