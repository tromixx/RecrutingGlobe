package com.example.recruiterglobe;

public class cards2 {
    private String userId;
    private String name;
    public cards2 (String userId, String name){
        this.userId = userId;
        this.name = name;
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

    public String setName(String name){
        this.name = name;
    }
}
