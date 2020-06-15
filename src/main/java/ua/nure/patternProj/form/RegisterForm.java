package ua.nure.patternProj.form;


public class RegisterForm {
    private String login;
    private String password;
    private String email;
    private String name;
    private String telephone;

    public String getLogin() {
        return login;
    }

    public RegisterForm setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterForm setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterForm setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public RegisterForm setName(String name) {
        this.name = name;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public RegisterForm setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }
}
