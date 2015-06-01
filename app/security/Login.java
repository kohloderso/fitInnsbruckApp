package security;

import models.Athlete;

public class Login {
    public String username;
    public String password;

    public String validate() {
        if (Athlete.authenticate(username, password) == null) {
            return "Invalid user or password";
        }
        return null;
    }

}