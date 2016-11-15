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


    public static void main(String[] args) {

        try {

            ObjectMapper mapper = new ObjectMapper();

            ElectionsOfficer officer1 = new ElectionsOfficerImpl("john","smith","jsmith","1234","jsmith@com.com","123 street","GA",3000,"athens");
            officer1.setId(2);
            String officer1Str = mapper.writeValueAsString(officer1);
            writeToServer("store","ElectionOfficer",officer1Str);
            ElectionsOfficer officer2 = new ElectionsOfficerImpl("john2","smith2","jsmith2","12342","jsmith@com.com2","123 street2","GA",30002,"athens2");
            officer2.setId(3);
            String officer2Str = mapper.writeValueAsString(officer2);
            writeToServer("store","ElectionOfficer",officer2Str);

            ElectoralDistrictImpl electoralDistrict = new ElectoralDistrictImpl("district1");
            electoralDistrict.setId(1);
            String districtStr = mapper.writeValueAsString(electoralDistrict);
            writeToServer("store","ElectoralDistrict",districtStr);

            Voter voter1 = new VoterImpl("john3","smith3","jsmith3","12343","jsmith@com.com3","123 street3",21,"GA",30003,"athens3");
            voter1.setId(1);
            String voter1Str = mapper.writeValueAsString(voter1);
            writeToServer("store","Voter",voter1Str);
            Voter voter2 = new VoterImpl("john4","smith4","jsmith4","12344","jsmith@com.com4","123 street4",21,"GA",30004,"athens4");
            voter2.setId(2);
            String voter2Str = mapper.writeValueAsString(voter2);
            writeToServer("store","Voter",voter2Str);



            writeToServer("store","District_Voter",districtStr+"**|**"+voter1Str);
            writeToServer("store","District_Voter",districtStr+"**|**"+voter2Str);

            PoliticalParty politicalParty1 = new PoliticalPartyImpl("part1");
            politicalParty1.setId(1);
            String party1Str = mapper.writeValueAsString(politicalParty1);
            writeToServer("store","PoliticalParty",party1Str);
            PoliticalParty politicalParty2 = new PoliticalPartyImpl("part2");
            politicalParty2.setId(2);
            String party2Str = mapper.writeValueAsString(politicalParty2);
            writeToServer("store","PoliticalParty",party2Str);

            Issue issue1 = new IssueImpl("q1");
            issue1.setId(1);
            String issue1Str = mapper.writeValueAsString(issue1);
            writeToServer("store","Issue",issue1Str);
            Issue issue2 = new IssueImpl("q2");
            issue2.setId(2);
            String issue2Str = mapper.writeValueAsString(issue2);
            writeToServer("store","Issue",issue2Str);
            Issue issue3 = new IssueImpl("q3");
            issue3.setId(3);
            String issue3Str = mapper.writeValueAsString(issue3);
            writeToServer("store","Issue",issue3Str);

            Election election1 = new ElectionImpl("office1",true,null);
            election1.setId(1);
            String election1Str = mapper.writeValueAsString(election1);
            writeToServer("store","Election",election1Str);
            Election election2 = new ElectionImpl("office2",false,null);
            election2.setId(2);
            String election2Str = mapper.writeValueAsString(election2);
            writeToServer("store","Election",election2Str);
            Election election3 = new ElectionImpl("office3",false,null);
            election3.setId(3);
            String election3Str = mapper.writeValueAsString(election3);
            writeToServer("store","Election",election3Str);

            Candidate candidate1 = new CandidateImpl("cantidate1",null,null);
            candidate1.setId(1);
            String candidate1Str =mapper.writeValueAsString(candidate1);
            writeToServer("store","Candidate",candidate1Str);
            Candidate candidate2 = new CandidateImpl("cantidate2",null,null);
            candidate2.setId(2);
            String candidate2Str =mapper.writeValueAsString(candidate2);
            writeToServer("store","Candidate",candidate2Str);
            Candidate candidate3 = new CandidateImpl("cantidat3",null,null);
            candidate3.setId(3);
            String candidate3Str =mapper.writeValueAsString(candidate3);
            writeToServer("store","Candidate",candidate3Str);

            writeToServer("store","Party_Candidate",party1Str+"**|**"+candidate1Str);
            writeToServer("store","Party_Candidate",party2Str+"**|**"+candidate2Str);

            writeToServer("store", "Election_Candidate",election1Str+"**|**"+candidate1Str);
            writeToServer("store", "Election_Candidate",election1Str+"**|**"+candidate2Str);
            writeToServer("store", "Election_Candidate",election1Str+"**|**"+candidate3Str);
            writeToServer("store", "Election_Candidate",election2Str+"**|**"+candidate1Str);
            writeToServer("store", "Election_Candidate",election2Str+"**|**"+candidate2Str);
            writeToServer("store", "Election_Candidate",election2Str+"**|**"+candidate3Str);
            writeToServer("store", "Election_Candidate",election3Str+"**|**"+candidate1Str);
            writeToServer("store", "Election_Candidate",election3Str+"**|**"+candidate2Str);
            writeToServer("store", "Election_Candidate",election3Str+"**|**"+candidate3Str);

            long num = 123123;
            java.sql.Date date = new Date(num);
            Ballot ballot1 = new BallotImpl(date,date,false,null);
            ballot1.setId(1);
            String ballot1Str = mapper.writeValueAsString(ballot1);
            writeToServer("store","Ballot",ballot1Str);
            Ballot ballot2 = new BallotImpl(date,date,false,null);
            ballot2.setId(2);
            String ballot2Str = mapper.writeValueAsString(ballot1);
            writeToServer("store","Ballot",ballot2Str);

            writeToServer("store","Ballot_Election",ballot1Str+"**|**"+election1Str);
            writeToServer("store","Ballot_Election",ballot1Str+"**|**"+election2Str);
            writeToServer("store","Ballot_Election",ballot1Str+"**|**"+election3Str);

            writeToServer("store","Ballot_Issue",ballot1Str+"**|**"+issue1Str);
            writeToServer("store","Ballot_Issue",ballot1Str+"**|**"+issue2Str);
            writeToServer("store","Ballot_Issue",ballot1Str+"**|**"+issue3Str);

            VoterRecordImpl voteRecord1 = new VoterRecordImpl(date,voter1,ballot1);
            String voteRecord1Str = mapper.writeValueAsString(voteRecord1);
            VoterRecordImpl voteRecord2 = new VoterRecordImpl(date,voter2,ballot2);
            String voteRecord2Str = mapper.writeValueAsString(voteRecord2);
            writeToServer("store","VoterRecord",voteRecord1Str);
            writeToServer("store","VoterRecord",voteRecord2Str);

            writeToServer("delete","VoterRecord",voteRecord1Str);
            writeToServer("delete","VoterRecord",voteRecord2Str);


            writeToServer("delete","Ballot_Election",ballot1Str+"**|**"+election1Str);
            writeToServer("delete","Ballot_Election",ballot1Str+"**|**"+election2Str);
            writeToServer("delete","Ballot_Election",ballot1Str+"**|**"+election3Str);

            writeToServer("delete","Ballot_Issue",ballot1Str+"**|**"+issue1Str);
            writeToServer("delete","Ballot_Issue",ballot1Str+"**|**"+issue2Str);
            writeToServer("delete","Ballot_Issue",ballot1Str+"**|**"+issue3Str);

            writeToServer("delete","Ballot",ballot2Str);
            writeToServer("delete","Ballot",ballot1Str);

            writeToServer("delete","Candidate",candidate3Str);

            writeToServer("delete","Party_Candidate",party1Str+"**|**"+candidate1Str);
            writeToServer("delete","Party_Candidate",party2Str+"**|**"+candidate2Str);

            writeToServer("delete", "Election_Candidate",election1Str+"**|**"+candidate1Str);
            writeToServer("delete", "Election_Candidate",election1Str+"**|**"+candidate2Str);
            writeToServer("delete", "Election_Candidate",election1Str+"**|**"+candidate3Str);
            writeToServer("delete", "Election_Candidate",election2Str+"**|**"+candidate1Str);
            writeToServer("delete", "Election_Candidate",election2Str+"**|**"+candidate2Str);
            writeToServer("delete", "Election_Candidate",election2Str+"**|**"+candidate3Str);
            writeToServer("delete", "Election_Candidate",election3Str+"**|**"+candidate1Str);
            writeToServer("delete", "Election_Candidate",election3Str+"**|**"+candidate2Str);
            writeToServer("delete", "Election_Candidate",election3Str+"**|**"+candidate3Str);

            writeToServer("delete","Candidate",candidate1Str);

            writeToServer("delete","Election",election1Str);
            writeToServer("delete","Election",election2Str);
            writeToServer("delete","Election",election3Str);

            writeToServer("delete","Issue",issue1Str);
            writeToServer("delete","Issue",issue2Str);
            writeToServer("delete","Issue",issue3Str);

            writeToServer("delete","PoliticalParty",party1Str);
            writeToServer("delete","PoliticalParty",party2Str);

            writeToServer("delete","District_Voter",districtStr+"**|**"+voter1Str);
            writeToServer("delete","District_Voter",districtStr+"**|**"+voter2Str);

            writeToServer("delete","Voter",voter2Str);
            writeToServer("delete","Voter",voter1Str);

            writeToServer("delete","ElectoralDistrict",districtStr);

            writeToServer("store","ElectionOfficer",officer1Str);
            writeToServer("store","ElectionOfficer",officer2Str);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeToServer(String action, String objectType, String content){
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


            for (int c = input.read(); c != -1; c = input.read())
                System.out.print((char) c);
            input.close();

            System.out.println("Resp Code:" + con.getResponseCode());
            System.out.println("Resp Message:" + con.getResponseMessage());
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    static {
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
    }
}
