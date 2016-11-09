package main.javaSrc.Entities;

import main.javaSrc.helpers.EVException;

public interface Issue extends BallotItem {
    
    String getQuestion();
    
    
    void setQuestion(String question);
    
    
    int getYesCount();
    
    
    void setYesCount(int yesCount) throws EVException;
    
    
    int getNoCount();
    



    
    void addYesVote();

    
    void addNoVote();
}
