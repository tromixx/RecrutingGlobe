package com.example.recruiterglobe.Swipe;

public class cards {
    private String userId;
    private String name;
    private String utr;
    private String natrank;
    private String profileImageUrl;
    private String bio;
    public cards (String userId, String name, String profileImageUrl, String bio, String utr, String natrank){
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.utr = utr;
        this.natrank = natrank;
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

    public String getUtr(){
        return utr;
    }

    public void setUTR() {
        this.utr = utr;
    }

    public String getNatrank(){
        return natrank;
    }

    public void setNatrank() {
        this.natrank = natrank;
    }
}