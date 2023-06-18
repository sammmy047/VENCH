package com.example.project;

public class UserDetails {
   String username="uname", email="email",phone,pass;
   public UserDetails()
   {}
public UserDetails(String users,String usr_email,String phno,String encpass)
{
    this.username= users;
    this.email = usr_email;
    this.phone = phno;
    this.pass= encpass;
}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return "email";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
