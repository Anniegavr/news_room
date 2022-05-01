package backend.service.security.services;

import backend.repository.UserRepository;
import backend.service.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

//@RequiredArgsConstructor //Didn't use this, because it doesn't allow an empty constructor to be initialised, whilst I need it to
@NoArgsConstructor
@AllArgsConstructor
@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    @Qualifier("gmail")
    private JavaMailSender mailSender;

    public void sendMail(String subject, String toAddresses, String ccAddresses, String bccAddresses, String body) {
        String from = userRepository.findById(userDetailsService.getUserIdFromToken()).get().getUsername();
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(toAddresses.split("[,;]"));
            message.setFrom(from, "<From Name>");
            message.setSubject(subject);
            if (isNotBlank(ccAddresses))
                message.setCc(ccAddresses.split("[;,]"));
            if (isNotBlank(bccAddresses))
                message.setBcc(bccAddresses.split("[;,]"));
            message.setText(body, false); //or true, for multimedia emails
        };
        mailSender.send(preparator);
        logger.info("Email sent successfully To {},{} with Subject {}", toAddresses, ccAddresses, subject);
    }
}
