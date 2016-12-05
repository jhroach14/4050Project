package main.javaSrc.DBHelpers.CDFSTHelpers;

import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.Entities.*;
import main.javaSrc.Entities.EntityImpl.*;
import main.javaSrc.HttpClasses.DBExchange;
import main.javaSrc.helpers.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;

/**
 * Created by User on 10/30/2016.
 */
public class CreateHelper extends CDFSTHelper {
    private static Logger log = new Logger(CreateHelper.class);

    public CreateHelper(DBExchange dbExchange, DbConnHelper dbConnHelper) {
        super(dbExchange,dbConnHelper);
    }

    public List<Entity> execute(){

        String objectType = dbExchange.getDBRequestObjectType();
        String sourced = dbExchange.getParam("sourced");
        ObjectMapper mapper = new ObjectMapper();

        try{
            switch (objectType){

                case "Ballot":
                    log.out("Attempting to create Ballot");
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), BallotImpl.class);
                        objectLayer.createBallot(((Ballot)entity).getOpenDate(),((Ballot)entity).getCloseDate(),false);
                    } else {
                        entity = objectLayer.createBallot();
                    }
                    break;
                case "Candidate":
                    log.out("Attempting to create Candidate");
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), CandidateImpl.class);
                        objectLayer.createCandidate(((Candidate)entity).getName());
                    } else {
                        entity = objectLayer.createCandidate();
                    }
                    break;
                case "Election":
                    log.out("Attempting to create Election");
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectionImpl.class);
                        objectLayer.createElection(((Election)entity).getOffice(),((Election)entity).getIsPartisan());
                    } else {
                        entity = objectLayer.createElection();
                    }
                    break;
                case "ElectionOfficer":
                    log.out("Attempting to create ElectionOfficer");
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectionsOfficerImpl.class);
                        objectLayer.createElectionsOfficer(((ElectionsOfficer)entity).getFirstName(),((ElectionsOfficer)entity).getLastName(),((ElectionsOfficer)entity).getUserName(),((ElectionsOfficer)entity).getUserPassword(),((ElectionsOfficer)entity).getEmailAddress(),((ElectionsOfficer)entity).getAddress(),((ElectionsOfficer)entity).getState(),((ElectionsOfficer)entity).getZip(),((ElectionsOfficer)entity).getCity());
                    } else {
                        entity = objectLayer.createElectionsOfficer();
                    }
                    break;
                case "ElectoralDistrict":
                    log.out("Attempting to create ElectoralDistrict");
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectoralDistrictImpl.class);
                        objectLayer.createElectoralDistrict(((ElectoralDistrict)entity).getName(), ((ElectoralDistrict)entity).getZip());
                    } else {
                        entity = objectLayer.createElectoralDistrict();
                    }
                    break;
                case "Issue":
                    log.out("Attempting to create Issue");
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), IssueImpl.class);
                        objectLayer.createIssue(((Issue)entity).getQuestion());
                    } else {
                        entity = objectLayer.createIssue();
                    }
                    break;
                case "PoliticalParty":
                    log.out("Attempting to create PoliticalParty");
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), PoliticalPartyImpl.class);
                        objectLayer.createPoliticalParty(((PoliticalParty)entity).getName());
                    } else {
                        entity = objectLayer.createPoliticalParty();
                    }
                    break;
                case "Voter":
                    log.out("Attempting to create Voter");
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), VoterImpl.class);
                        objectLayer.createVoter(((Voter)entity).getFirstName(),((Voter)entity).getLastName(),((Voter)entity).getUserName(),((Voter)entity).getUserPassword(),((Voter)entity).getEmailAddress(),((Voter)entity).getAddress(),((Voter)entity).getAge(),((Voter)entity).getState(),((Voter)entity).getZip(),((Voter)entity).getCity());
                    } else {
                        entity = objectLayer.createVoter();
                    }
                    break;
                case "VoterRecord":
                    log.out("Attempting to create VoterRecord");
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), VoterRecordImpl.class);
                        objectLayer.createVoterRecord(((VoterRecordImpl)entity).getDate(),((VoterRecordImpl)entity).getVoter(),((VoterRecordImpl)entity).getBallot());
                    } else {
                        entity = objectLayer.createVoterRecord();
                    }
                    break;
                default:
                    log.error("Uknown object type "+objectType);
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        dbConnHelper.commit(connection);
        entities.add(entity);
        return entities;
    }


}
