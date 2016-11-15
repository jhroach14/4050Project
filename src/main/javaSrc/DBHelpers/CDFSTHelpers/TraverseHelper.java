package main.javaSrc.DBHelpers.CDFSTHelpers;

import main.javaSrc.DBHelpers.DbConnHelper;
import main.javaSrc.Entities.Ballot;
import main.javaSrc.Entities.Entity;
import main.javaSrc.Entities.EntityImpl.*;
import main.javaSrc.HttpClasses.DBExchange;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;

/**
 * Created by User on 11/10/2016.
 */
public class TraverseHelper extends CDFSTHelper{
    public TraverseHelper(DBExchange dbExchange, DbConnHelper dbConnHelper) {
        super(dbExchange, dbConnHelper);
    }

    @Override
    public List<Entity> execute() {

        String objectType = dbExchange.getDBRequestObjectType();
        String sourced = dbExchange.getParam("sourced");
        ObjectMapper mapper = new ObjectMapper();
        Entity model;

        try{

            switch (objectType){

                case"getBallotGivenBallotItem":
                    if(sourced.equals("true")){
                        model = mapper.readValue(dbExchange.getRequestBody(), BallotItemImpl.class);
                        entities.add(objectLayer.getBallot((BallotItemImpl)model));
                    }
                    break;
                case"getBallotItemsGivenBallot":
                    if(sourced.equals("true")){
                        model = mapper.readValue(dbExchange.getRequestBody(), BallotImpl.class);
                        entities.addAll(objectLayer.getBallotItems((BallotImpl)model));
                    }
                    break;
                case"getCandidatesGivenElection":
                    if(sourced.equals("true")){
                        model = mapper.readValue(dbExchange.getRequestBody(), ElectionImpl.class);
                        entities.addAll(objectLayer.getCandidates((ElectionImpl)model));
                    }
                    break;
                case"getElectionGivenCandidate":
                    if(sourced.equals("true")){
                        model = mapper.readValue(dbExchange.getRequestBody(), CandidateImpl.class);
                        entities.add(objectLayer.getElection((CandidateImpl)model));
                    }
                    break;
                case"getBallotsGivenDistrict":
                    if (sourced.equals("true")){
                        model = mapper.readValue(dbExchange.getRequestBody(), ElectoralDistrictImpl.class);
                        entities.addAll(objectLayer.getBallots((ElectoralDistrictImpl)model));
                    }
                    break;
                case"getDistrictGivenBallot":
                    if (sourced.equals("true")){
                        model = mapper.readValue(dbExchange.getRequestBody(), BallotImpl.class);
                        entities.add(objectLayer.getDistrict((BallotImpl)model));
                    }
                    break;
                case"getVotersGivenDistrict":
                    if (sourced.equals("true")){
                        model = mapper.readValue(dbExchange.getRequestBody(), ElectoralDistrictImpl.class);
                        entities.addAll(objectLayer.getVoters((ElectoralDistrictImpl)model));
                    }
                    break;
                case"getDistrictGivenVoter":
                    if (sourced.equals("true")){
                        model = mapper.readValue(dbExchange.getRequestBody(), VoterImpl.class);
                        entities.add(objectLayer.getDistrict((VoterImpl)model));
                    }
                    break;
                case"getCandidatesGivenParty":
                    if (sourced.equals("true")){
                        model = mapper.readValue(dbExchange.getRequestBody(), PoliticalPartyImpl.class);
                        entities.addAll(objectLayer.getCandidates((PoliticalPartyImpl)model));
                    }
                    break;
                case"getPartyGivenCandidate":
                    if (sourced.equals("true")){
                        model = mapper.readValue(dbExchange.getRequestBody(), CandidateImpl.class);
                        entities.add(objectLayer.getParty((CandidateImpl)model));
                    }
                    break;
                default:
                    break;
            }

        }catch (Exception e){

        }
        dbConnHelper.commit(connection);
        return entities;
    }
}
