package com.assigned.printart.Model;

public class EndUsers
{
private String Name,Password,PhoneNumber;

public EndUsers()
{

}

    public EndUsers(String name, String password, String phoneNumber) {
        Name = name;
        Password = password;
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
