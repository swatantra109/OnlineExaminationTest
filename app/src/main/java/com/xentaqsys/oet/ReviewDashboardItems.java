package com.xentaqsys.oet;

/**
 * Created by rahul on 10/11/15.
 */
public class ReviewDashboardItems {
    public ReviewDashboardItems() {
    }

    private String attempt,right,wrong,marks,completed;

public ReviewDashboardItems(String attempt, String right, String wrong, String marks, String completed) {
        this.attempt = attempt;
        this.right = right;
        this.wrong = wrong;
        this.marks = marks;
        this.completed = completed;

    }


    public String getAttempt() {
        return attempt;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
