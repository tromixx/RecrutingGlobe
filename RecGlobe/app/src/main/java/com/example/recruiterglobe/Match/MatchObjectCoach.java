package com.example.recruiterglobe.Match;

public class MatchObjectCoach {
    private String userId;
    private String name;

    public MatchObjectCoach(String userId) {
        this.userId = userId;
        //this.name = name;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
