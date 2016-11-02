package main.javaSrc.Entities;


public interface User extends Entity{


    String getFirstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    String getUserName();
    void   setUserName(String userName);

    String getUserPassword();
    abstract  public void   setUserPassword(String userPassword);

    String getEmailAddress();
    void   setEmailAddress(String emailAddress);

    String getAddress();
    void   setAddress(String address);

    String getState();
    void   setState(String state);

    int getZip();
    void   setZip(int zip);

    String getCity();
    void setCity(String city);


}
