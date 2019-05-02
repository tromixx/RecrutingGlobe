package com.example.recruiterglobe;

public class cards {
    private String userId;
    private String name;
    private String profileImageUrl;
    private String bio;
    private String uni;
    public cards (String userId, String name, String profileImageUrl, String bio, String uni){
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.uni = uni;
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

    public String getProfileImageUrl(){
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getBio(){
        return bio;
    }

    public void setBio(String bio) {
        this.bio = name;
    }

    public String getUni(){
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }
}