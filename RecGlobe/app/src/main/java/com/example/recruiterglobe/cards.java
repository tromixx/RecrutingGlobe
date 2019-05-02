package com.example.recruiterglobe;

public class cards {
    private String userId;
    private String name;
    private String profileImagUrl;
    public cards (String userId, String name, String profileImagUrl){
        this.userId = userId;
        this.name = name;
        this.profileImageUrl=profileImageUrl;
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

    public String getProfileImagUrl() {
        return profileImagUrl;
    }

    public void setProfileImagUrl(String profileImagUrl) {
        this.profileImagUrl = profileImagUrl;
    }
}