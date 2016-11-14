package main.javaSrc.Entities;

import main.javaSrc.helpers.EVException;
import org.codehaus.jackson.map.annotate.JsonDeserialize;


public interface Issue extends BallotItem {
    
    String getQuestion();
    
    
    void setQuestion(String question);
    
    
    int getYesCount();
    
    
    void setYesCount(int yesCount) throws EVException;
    
    
    int getNoCount();
    

    void setNoCount(int noCount) throws EVException;

    
    void addYesVote();

    
    void addNoVote();
}
