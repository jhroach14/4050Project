package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.BallotItem;
import main.javaSrc.Entities.Issue;
import main.javaSrc.helpers.EVException;

/**
 * Created by User on 11/8/2016.
 */
public class IssueImpl extends BallotItemImpl implements Issue {

    private String question;
    private int yesCount;
    private int noCount;

    public IssueImpl(String question) {
        this.question = question;
    }

    public IssueImpl() {

    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(String question) {
        this.question=question;
    }

    @Override
    public int getYesCount() {
        return yesCount;
    }

    @Override
    public void setYesCount(int yesCount) throws EVException {
        this.yesCount=yesCount;
    }

    @Override
    public int getNoCount() {
        return noCount;
    }

    @Override
    public void setNoCount(int noCount){
        this.noCount=noCount;
    }

    @Override
    public void addYesVote() {
        yesCount++;
    }

    @Override
    public void addNoVote() {
        noCount++;
    }
}
