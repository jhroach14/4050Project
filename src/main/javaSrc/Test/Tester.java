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

/**
 * Created by User on 11/9/2016.
 */
public class Tester {


    public static void main(String[] args) {

        try {

            ObjectMapper mapper = new ObjectMapper();

            ElectionsOfficer officer1 = new ElectionsOfficerImpl("john","smith","jsmith","1234","jsmith@com.com","123 street","GA",3000,"athens");
            String officer1Str = mapper.writeValueAsString(officer1);
            writeToServer("store","ElectionOfficer",officer1Str);

            ElectionsOfficer officer2 = new ElectionsOfficerImpl("john2","smith2","jsmith2","12342","jsmith@com.com2","123 street2","GA",30002,"athens2");
            String officer2Str = mapper.writeValueAsString(officer1);
            writeToServer("store","ElectionOfficer",officer2Str);


            ElectoralDistrict electoralDistrict = new ElectoralDistrictImpl("district1");
            String districtStr = mapper.writeValueAsString(electoralDistrict);
            writeToServer("store","ElectoralDistrict",districtStr);


            Voter voter1 = new VoterImpl("john3","smith3","jsmith3","12343","jsmith@com.com3","123 street3",21,"GA",30003,"athens3");
            String voter1Str = mapper.writeValueAsString(voter1);
            writeToServer("store","Voter",voter1Str);

            Voter voter2 = new VoterImpl("john4","smith4","jsmith4","12344","jsmith@com.com4","123 street4",21,"GA",30004,"athens4");
            String voter2Str = mapper.writeValueAsString(voter2);
            writeToServer("store","Voter",voter2Str);


            PoliticalParty politicalParty1 = new PoliticalPartyImpl("part1");
            String party1Str = mapper.writeValueAsString(politicalParty1);
            writeToServer("store","PoliticalParty",party1Str);

            PoliticalParty politicalParty2 = new PoliticalPartyImpl("part2");
            String party2Str = mapper.writeValueAsString(politicalParty2);
            writeToServer("store","PoliticalParty",party2Str);


            Issue issue1 = new IssueImpl("q1");
            String issue1Str = mapper.writeValueAsString(issue1);
            writeToServer("store","Issue",issue1Str);

            Issue issue2 = new IssueImpl("q2");
            String issue2Str = mapper.writeValueAsString(issue2);
            writeToServer("store","Issue",issue2Str);

            Issue issue3 = new IssueImpl("q3");
            String issue3Str = mapper.writeValueAsString(issue3);
            writeToServer("store","Issue",issue3Str);

            Election election1 = new ElectionImpl("office1",true,null);
            String election1Str = mapper.writeValueAsString(election1);
            writeToServer("store","Election",election1Str);

            Election election2 = new ElectionImpl("office2",false,null);
            String election2Str = mapper.writeValueAsString(election2);
            writeToServer("store","Election",election2Str);

            Election election3 = new ElectionImpl("office3",false,null);
            String election3Str = mapper.writeValueAsString(election3);
            writeToServer("store","Election",election3Str);

            Candidate candidate1 = new CandidateImpl("cantidate1",null,null);
            String candidate1Str =mapper.writeValueAsString(candidate1);
            writeToServer("store","Candidate",candidate1Str);

            Candidate candidate2 = new CandidateImpl("cantidate2",null,null);
            String candidate2Str =mapper.writeValueAsString(candidate2);
            writeToServer("store","Candidate",candidate2Str);

            Candidate candidate3 = new CandidateImpl("cantidat3",null,null);
            String candidate3Str =mapper.writeValueAsString(candidate3);
            writeToServer("store","Candidate",candidate3Str);


        } catch (IOException e) {
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
