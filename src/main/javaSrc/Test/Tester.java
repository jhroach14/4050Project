package main.javaSrc.Test;

import main.javaSrc.Entities.*;
import main.javaSrc.Entities.EntityImpl.*;
import org.codehaus.jackson.map.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;

/**
 * Created by User on 11/9/2016.
 */
public class Tester {
    
    static ObjectMapper mapper = new ObjectMapper();


    public static void main(String[] args) {

        try {

            ObjectMapper mapper = new ObjectMapper();

            /*ElectionsOfficer officer1 = new ElectionsOfficerImpl("john","smith","jsmith","1234","jsmith@com.com","123 street","GA",3000,"athens");
            officer1=(ElectionsOfficer)stringToEntity(writeToServer("store","ElectionOfficer",entityToString(officer1)),ElectionsOfficerImpl.class);
            ElectionsOfficer officer2 = new ElectionsOfficerImpl("john2","smith2","jsmith2","12342","jsmith@com.com2","123 street2","GA",30002,"athens2");
            officer2= (ElectionsOfficer) stringToEntity(writeToServer("store","ElectionOfficer",entityToString(officer2)),ElectionsOfficerImpl.class);

            ElectoralDistrictImpl electoralDistrict = new ElectoralDistrictImpl("district1");
            electoralDistrict = (ElectoralDistrictImpl) stringToEntity(writeToServer("store","ElectoralDistrict",entityToString(electoralDistrict)),ElectoralDistrictImpl.class);
            */
            Voter voter1 = new VoterImpl("john3","smith3","jsmith3","12343","jsmith@com.com3","123 street3",21,"GA",30003,"athens3");
            voter1= (Voter) stringToEntity(writeToServer("store","Voter",entityToString(voter1)),VoterImpl.class);
            Voter voter2 = new VoterImpl("john4","smith4","jsmith4","12344","jsmith@com.com4","123 street4",21,"GA",30004,"athens4");
            voter2= (Voter) stringToEntity(writeToServer("store","Voter",entityToString(voter2)),VoterImpl.class);

            //writeToServer("store","District_Voter",entityToString(electoralDistrict)+"**|**"+entityToString(voter1));
            //writeToServer("store","District_Voter",entityToString(electoralDistrict)+"**|**"+entityToString(voter2));

            //System.out.println(writeToServer("traverse","getDistrictGivenVoter",entityToString(voter1)));
            //System.out.println(writeToServer("traverse","getVotersGivenDistrict",entityToString(electoralDistrict)));

            PoliticalParty politicalParty1 = new PoliticalPartyImpl("part1");
            politicalParty1 = (PoliticalParty) stringToEntity(writeToServer("store","PoliticalParty",entityToString(politicalParty1)),PoliticalPartyImpl.class);
            PoliticalParty politicalParty2 = new PoliticalPartyImpl("part2");
            politicalParty2 = (PoliticalParty) stringToEntity(writeToServer("store","PoliticalParty",entityToString(politicalParty2)),PoliticalPartyImpl.class);
            /*
            Issue issue1 = new IssueImpl("q1");
            issue1 = (Issue) stringToEntity(writeToServer("store","Issue",entityToString(issue1)),IssueImpl.class);
            Issue issue2 = new IssueImpl("q2");
            issue2 = (Issue) stringToEntity(writeToServer("store","Issue",entityToString(issue2)),IssueImpl.class);
            Issue issue3 = new IssueImpl("q3");
            issue3 = (Issue) stringToEntity(writeToServer("store","Issue",entityToString(issue3)),IssueImpl.class);
        */
            Election election1 = new ElectionImpl("office1",true);
            election1 = (Election) stringToEntity(writeToServer("store","Election",entityToString(election1)),ElectionImpl.class);
            Election election2 = new ElectionImpl("office2",false);
            election2 = (Election) stringToEntity(writeToServer("store","Election",entityToString(election2)),ElectionImpl.class);
            Election election3 = new ElectionImpl("office3",false);
            election3 = (Election) stringToEntity(writeToServer("store","Election",entityToString(election3)),ElectionImpl.class);

            Candidate candidate1 = new CandidateImpl("candidate1");
            candidate1 = (Candidate) stringToEntity(writeToServer("store","Candidate",entityToString(candidate1)),CandidateImpl.class);
            Candidate candidate2 = new CandidateImpl("candidate2");
            candidate2 = (Candidate) stringToEntity(writeToServer("store","Candidate",entityToString(candidate2)),CandidateImpl.class);
            Candidate candidate3 = new CandidateImpl("candidate3");
            candidate3 = (Candidate) stringToEntity(writeToServer("store","Candidate",entityToString(candidate3)),CandidateImpl.class);

            writeToServer("store","Party_Candidate",entityToString(politicalParty1)+"**|**"+entityToString(candidate1));
            writeToServer("store","Party_Candidate",entityToString(politicalParty2)+"**|**"+entityToString(candidate2));

            System.out.println(writeToServer("traverse","getCandidatesGivenParty",entityToString(politicalParty1)));
            System.out.println(writeToServer("traverse","getPartyGivenCandidate",entityToString(candidate2)));

            writeToServer("store", "Election_Candidate",entityToString(election1)+"**|**"+entityToString(candidate1));
            writeToServer("store", "Election_Candidate",entityToString(election1)+"**|**"+entityToString(candidate2));
            writeToServer("store", "Election_Candidate",entityToString(election1)+"**|**"+entityToString(candidate3));
            writeToServer("store", "Election_Candidate",entityToString(election2)+"**|**"+entityToString(candidate1));
            writeToServer("store", "Election_Candidate",entityToString(election2)+"**|**"+entityToString(candidate2));
            writeToServer("store", "Election_Candidate",entityToString(election2)+"**|**"+entityToString(candidate3));
            writeToServer("store", "Election_Candidate",entityToString(election3)+"**|**"+entityToString(candidate1));
            writeToServer("store", "Election_Candidate",entityToString(election3)+"**|**"+entityToString(candidate2));
            writeToServer("store", "Election_Candidate",entityToString(election3)+"**|**"+entityToString(candidate3));

            System.out.println(writeToServer("traverse","getElectionGivenCandidate",entityToString(candidate3)));
            System.out.println(writeToServer("traverse","getCandidatesGivenElection",entityToString(election1)));

            long num = 123123;
            java.sql.Date date = new Date(num);
            Ballot ballot1 = new BallotImpl(date,date,false);
            ballot1 = (Ballot) stringToEntity(writeToServer("store","Ballot",entityToString(ballot1)),BallotImpl.class);
            Ballot ballot2 = new BallotImpl(date,date,false);
            ballot2 = (Ballot) stringToEntity(writeToServer("store","Ballot",entityToString(ballot2)),BallotImpl.class);

            writeToServer("store","Ballot_Election",entityToString(ballot1)+"**|**"+entityToString(election1));
            writeToServer("store","Ballot_Election",entityToString(ballot1)+"**|**"+entityToString(election2));
            writeToServer("store","Ballot_Election",entityToString(ballot1)+"**|**"+entityToString(election3));
        /*
            System.out.println(writeToServer("traverse","getBallotGivenBallotItem",entityToString(election2)));

            writeToServer("store","Ballot_Issue",entityToString(ballot1)+"**|**"+entityToString(issue1));
            writeToServer("store","Ballot_Issue",entityToString(ballot1)+"**|**"+entityToString(issue2));
            writeToServer("store","Ballot_Issue",entityToString(ballot1)+"**|**"+entityToString(issue3));

            System.out.println(writeToServer("traverse","getBallotGivenBallotItem",entityToString(issue1)));
            System.out.println(writeToServer("traverse","getBallotItemsGivenBallot",entityToString(ballot2)));
        */
            VoteRecord voteRecord1 = new VoterRecordImpl(date,voter1,ballot1);
            voteRecord1 = (VoteRecord) stringToEntity(writeToServer("store","VoterRecord",entityToString(voteRecord1)),VoterRecordImpl.class);
            VoteRecord voteRecord2 = new VoterRecordImpl(date,voter2,ballot2);
            voteRecord2 = (VoteRecord) stringToEntity(writeToServer("store","VoterRecord",entityToString(voteRecord2)),VoterRecordImpl.class);

            //writeToServer("delete","VoterRecord",entityToString(voteRecord1));
            //writeToServer("delete","VoterRecord",entityToString(voteRecord2));

/*
            writeToServer("delete","Ballot_Election",entityToString(ballot1)+"**|**"+entityToString(election1));
            writeToServer("delete","Ballot_Election",entityToString(ballot1)+"**|**"+entityToString(election2));
            writeToServer("delete","Ballot_Election",entityToString(ballot1)+"**|**"+entityToString(election3));
*/
            /*writeToServer("delete","Ballot_Issue",entityToString(ballot1)+"**|**"+entityToString(issue1));
            writeToServer("delete","Ballot_Issue",entityToString(ballot1)+"**|**"+entityToString(issue2));
            writeToServer("delete","Ballot_Issue",entityToString(ballot1)+"**|**"+entityToString(issue3));
        */
            /*
            writeToServer("delete","Ballot",entityToString(ballot2));
            writeToServer("delete","Ballot",entityToString(ballot1));

            writeToServer("delete","Candidate",entityToString(candidate3));
            writeToServer("delete","Candidate",entityToString(candidate2));
            writeToServer("delete","Candidate",entityToString(candidate1));
*/
            //writeToServer("delete","Party_Candidate",entityToString(politicalParty1)+"**|**"+entityToString(candidate1));
            //writeToServer("delete","Party_Candidate",entityToString(politicalParty2)+"**|**"+entityToString(candidate2));
/*
            writeToServer("delete", "Election_Candidate",entityToString(election1)+"**|**"+entityToString(candidate1));
            writeToServer("delete", "Election_Candidate",entityToString(election1)+"**|**"+entityToString(candidate2));
            writeToServer("delete", "Election_Candidate",entityToString(election1)+"**|**"+entityToString(candidate3));
            writeToServer("delete", "Election_Candidate",entityToString(election2)+"**|**"+entityToString(candidate1));
            writeToServer("delete", "Election_Candidate",entityToString(election2)+"**|**"+entityToString(candidate2));
            writeToServer("delete", "Election_Candidate",entityToString(election2)+"**|**"+entityToString(candidate3));
            writeToServer("delete", "Election_Candidate",entityToString(election3)+"**|**"+entityToString(candidate1));
            writeToServer("delete", "Election_Candidate",entityToString(election3)+"**|**"+entityToString(candidate2));
            writeToServer("delete", "Election_Candidate",entityToString(election3)+"**|**"+entityToString(candidate3));

            writeToServer("delete","Candidate",entityToString(candidate1));

            writeToServer("delete","Election",entityToString(election1));
            writeToServer("delete","Election",entityToString(election2));
            writeToServer("delete","Election",entityToString(election3));
       */ /*
            writeToServer("delete","Issue",entityToString(issue1));
            writeToServer("delete","Issue",entityToString(issue2));
            writeToServer("delete","Issue",entityToString(issue3));
         */
            //writeToServer("delete","PoliticalParty",entityToString(politicalParty1));
            //writeToServer("delete","PoliticalParty",entityToString(politicalParty2));
        /*
            writeToServer("delete","District_Voter",entityToString(electoralDistrict)+"**|**"+entityToString(voter1));
            writeToServer("delete","District_Voter",entityToString(electoralDistrict)+"**|**"+entityToString(voter2));
        */
            //writeToServer("delete","Voter",entityToString(voter2));
            //writeToServer("delete","Voter",entityToString(voter1));
            /*
            writeToServer("delete","ElectoralDistrict",entityToString(electoralDistrict));

            writeToServer("delete","ElectionOfficer",entityToString(officer2));
            writeToServer("delete","ElectionOfficer",entityToString(officer1));
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Entity stringToEntity(String entityStr,Class cls){
        try {
            return (Entity) mapper.readValue(entityStr,cls);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String entityToString(Entity entity){
        try {
            return mapper.writeValueAsString(entity);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String writeToServer(String action, String objectType, String content){
        try {

            String httpURL = "http://localhost:9001/data/" + action + "/" + objectType+"?token=testToken123&sourced=true";
            URL myUrl = new URL(httpURL);

            HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);

            DataOutputStream output = new DataOutputStream(con.getOutputStream());


            output.writeBytes(content);

            output.close();

            DataInputStream input = new DataInputStream(con.getInputStream());

            String object="";
            for (int c = input.read(); c != -1; c = input.read()) {
                object += ((char) c);
            }
            input.close();

            System.out.println(object);
            System.out.println("Resp Code:" + con.getResponseCode());
            System.out.println("Resp Message:" + con.getResponseMessage());
            return object.substring(1,object.length()-1);
        }catch (Exception e){
            System.out.println(e.toString());
            return e.getMessage();
        }
    }
    /*static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier(){

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        if (hostname.equals("localhost")) {
                            return true;
                        }
                        return false;
                    }
                });
    }*/
}
