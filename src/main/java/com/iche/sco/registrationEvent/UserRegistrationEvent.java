package com.iche.sco.registrationEvent;


import com.iche.sco.model.BaseUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class UserRegistrationEvent extends ApplicationEvent {
    private BaseUser user;
    private String otp;

    public UserRegistrationEvent(BaseUser user, String otp) {
        super(user);
        this.user = user;
        this.otp = otp;
    }
}
