package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Entity;
import main.javaSrc.Entities.User;


/**
 * Created by User on 10/31/2016.
 */
public abstract class UserImpl extends EntityImpl implements User {
    protected String firstName = null;
    protected String lastName = null;
    protected String userName = null;
    protected String userPassword = null;
    protected String emailAddress = null;
    protected String address = null;
    protected String state = null;
    protected int zip = 0;
    protected String city = null;


    public UserImpl(String firstName, String lastName, String userName, String userPassword, String emailAddress, String address, String state, int zip, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.emailAddress = emailAddress;
        this.address = address;
        this.state = state;
        this.zip = zip;
        this.city = city;
    }

    public UserImpl() {
    }


    public String getFirstName() {
        return firstName;
    }
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Override
    public String getLastName() {
        return lastName;
    }
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public String getUserName() {
        return userName;
    }
    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Override
    public String getUserPassword() {
        return userPassword;
    }
    @Override
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    @Override
    public String getEmailAddress() {
        return emailAddress;
    }
    @Override
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    @Override
    public String getAddress() {
        return address;
    }
    @Override
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String getState() {
        return state;
    }
    @Override
    public void setState(String state) {
        this.state = state;
    }
    @Override
    public int getZip() {
        return zip;
    }
    @Override
    public void setZip(int zip) {
        this.zip = zip;
    }
    @Override
    public String getCity() {
        return city;
    }
    @Override
    public void setCity(String city) {
        this.city = city;
    }



}
