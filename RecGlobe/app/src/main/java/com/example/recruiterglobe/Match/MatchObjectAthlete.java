package com.example.recruiterglobe.Match;

public class MatchObjectAthlete {
    private String userId;
    private String name;
    private String pic;

    public MatchObjectAthlete(String userId, String name, String pic) {
        this.userId = userId;
        this.name = name;
        this.pic = pic;
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

    public String getPic(){
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
}
