package net.smallacademy.fragmentexample;

public class UserHelperClass {
    String name, email, mobile_number, password;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String email, String mobile_number, String password) {
        this.name = name;
        this.email = email;
        this.mobile_number = mobile_number;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
