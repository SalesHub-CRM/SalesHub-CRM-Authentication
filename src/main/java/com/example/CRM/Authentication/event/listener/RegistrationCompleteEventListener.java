package com.example.CRM.Authentication.event.listener;


import com.example.CRM.Authentication.entities.User;
import com.example.CRM.Authentication.event.RegistrationCompleteEvent;
import com.example.CRM.Authentication.services.UserServiceImp;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserServiceImp userServiceImp;
    private final JavaMailSender javaMailSender;
    private User user;


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        user=event.getUser();
        String verificationToken = UUID.randomUUID().toString();
        userServiceImp.saveUserVerificationToken(user,verificationToken);
        String url = event.getApplicationUrl()+"/auth/verifyEmail?token="+verificationToken;

        try{
            sendVerificationEmail(url);
        }
        catch(MessagingException | UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration :  {}", url);
    }

    private void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {

        String subject = "Email Verification";
        String senderName = "SalesHub Registration Portal Service";
        String mailContent = "<h2> Hello" +" " + user.getFirstname() + " " + user.getLastname() +",</h2>"
                + "<h3>Thank you for choosing to work with SalesHub !</h3>"
                + "<p>In order to complete your registration, please follow this link : </p>"
                +"<a href=\""+url+"\">Click here to verify your email</a>"
                +"<p>We hope to see you soon !</p>"
                +"<br/>"
                +"<p>The salesHub Team</p>";


        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("saiftf@gmail.com",senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent,true);
        javaMailSender.send(message);

    }
}
