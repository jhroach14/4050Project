package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.DBHelpers.ObjectLayerImpl;
import main.javaSrc.Entities.Election;
import main.javaSrc.Entities.ElectionsOfficer;
import main.javaSrc.Entities.Entity;
import main.javaSrc.handlers.AuthHandler;
import main.javaSrc.Entities.EntityImpl.*;

/**
 * Created by User on 10/31/2016.
 */
public class ElectionsOfficerImpl extends UserImpl implements ElectionsOfficer{
    public ElectionsOfficerImpl(){}

    public ElectionsOfficerImpl(String firstName, String lastName, String userName, String userPassword, String emailAddress, String address, String state, int zip, String city) {
        super(firstName, lastName, userName, userPassword, emailAddress, address, state, zip, city);
    }
}
