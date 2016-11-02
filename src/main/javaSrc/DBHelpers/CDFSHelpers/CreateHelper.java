package main.javaSrc.DBHelpers.CDFSHelpers;

import main.javaSrc.Entities.*;
import main.javaSrc.HttpClasses.DBExchange;
import main.javaSrc.helpers.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by User on 10/30/2016.
 */
public class CreateHelper extends CDFSHelper {
    private static Logger log = new Logger(CreateHelper.class);

    public CreateHelper(DBExchange dbExchange) {
        super(dbExchange);
    }

    public Entity execute(){

        String objectType = dbExchange.getDBRequestObjectType();
        String sourced = dbExchange.getParam("sourced");
        ObjectMapper mapper = new ObjectMapper();

        try{
            switch (objectType){

                case "Ballot":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), Ballot.class);
                    } else {
                        entity = objectLayer.createBallot();
                    }
                    break;
                case "Candidate":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), Candidate.class);
                    } else {
                        entity = objectLayer.createCandidate();
                    }
                    break;
                case "Election":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), Election.class);
                    } else {
                        entity = objectLayer.createElection();
                    }
                    break;
                case "ElectionOfficer":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectionsOfficer.class);
                    } else {
                        entity = objectLayer.createElectionsOfficer();
                    }
                    break;
                case "ElectoralDistrict":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), ElectoralDistrict.class);
                    } else {
                        entity = objectLayer.createElectoralDistrict();
                    }
                    break;
                case "Issue":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), Issue.class);
                    } else {
                        entity = objectLayer.createIssue();
                    }
                    break;
                case "PoliticalParty":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), PoliticalParty.class);
                    } else {
                        entity = objectLayer.createPoliticalParty();
                    }
                    break;
                case "Voter":
                    if (sourced.equals("true")) {
                        entity = mapper.readValue(dbExchange.getRequestBody(), Voter.class);
                    } else {
                        entity = objectLayer.createVoter();
                    }
                    break;
                default:
                    break;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return entity;
    }
}
