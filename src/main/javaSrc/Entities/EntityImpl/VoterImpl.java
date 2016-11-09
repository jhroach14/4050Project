package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.ElectoralDistrict;
import main.javaSrc.Entities.VoteRecord;
import main.javaSrc.Entities.Voter;
import main.javaSrc.helpers.EVException;

import java.util.List;

/**
 * Created by User on 10/31/2016.
 */
public class VoterImpl extends UserImpl implements Voter{
    private int voterId;
    private int age;
    private ElectoralDistrict electoralDistrict;

    public VoterImpl(String firstName, String lastName, String userName, String password, String emailAddress, String address, int age,String state, int zip, String city) {
       super(firstName,lastName,userName,password,emailAddress,address,state,zip,city);
    }
    public VoterImpl(){

    }
    @Override
    public int getVoterId() {
        return voterId;
    }

    @Override
    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public ElectoralDistrict getElectoralDistrict() throws EVException {
        return electoralDistrict;
    }

    @Override
    public void setElectoralDistrict(ElectoralDistrict electoralDistrict) throws EVException {
        this.electoralDistrict = electoralDistrict;
    }

    @Override
    public List<VoteRecord> getBallotVoteRecords() throws EVException {
        return null;
    }
}
