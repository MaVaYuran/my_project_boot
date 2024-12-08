package by.mariayuran.my_project_boot.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionObjectHolder {
    private int amountClicks=0;

    public SessionObjectHolder() {
        System.out.println("Session object created");
    }

    public int getAmountClicks() {
        return amountClicks;
    }
    public void addClick() {
        amountClicks++;
    }
}
