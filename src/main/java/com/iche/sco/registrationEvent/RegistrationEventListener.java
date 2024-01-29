package com.iche.sco.registrationEvent;


import com.iche.sco.applicationConfig.MailConfig;
import com.iche.sco.model.Users;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationEventListener implements ApplicationListener<UserRegistrationEvent> {
    private final MailConfig mailConfig;

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        String otp = event.getOtp();
        Users user = event.getUser();
        String email = user.getEmail();
        try {
            otpGenerator(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void otpGenerator(String user, String otp) throws MessagingException {
        MimeMessage message = mailConfig.javaMailSender().createMimeMessage();
        messageDetails(user, otp, message);
        mailConfig.javaMailSender().send(message);
        log.info("OTP: " + otp);
        log.info("Sent OTP to: " + user);
    }

    private void messageDetails(String user, String otp, MimeMessage message) throws MessagingException {
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom("chukwu.iche@gmail.com");
        messageHelper.setSubject("OTP Verification");
        messageHelper.setTo(user);

        String mailContent =  "<div style='width:100%; background: #f8f8f8;' >"
                + "<p style='font-size: 18px;'>Hello, " + user + "</p>"
                + "<p style='font-size: 16px;'>" + "Welcome to SCO </p>"
                + "<p style='font-size: 16px;'>Thank you for registering with us.</p>"
                + "<p style='font-size: 16px;'>Please enter your OTP below to complete your registration:</p>"
                + "<h1 style='font-size: 24px; margin: 20px 0;'>" + otp + "</h1>"
                + "<p style='font-size: 16px;'>Thank you,</p>"
                + "<p style='font-size: 16px;'>SCO</p>"
                + "</div>";
        messageHelper.setText(mailContent, true);
    }
}
