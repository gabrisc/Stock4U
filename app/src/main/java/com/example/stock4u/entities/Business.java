package com.example.stock4u.entities;


import static com.example.stock4u.util.FireBaseConfig.firebaseDbReference;

public class Business {
    private String id;
    private String email;
    private String password;
    private String FantasyName;
    private String CNPJ;
    private String adress;
    private String phoneNumber;
    private String businessBranch;
    private String uId;

    public Business(String id, String email, String password, String fantasyName, String CNPJ, String adress, String phoneNumber, String businessBranch) {
        this.id = id;
        this.email = email;
        this.password = password;
        FantasyName = fantasyName;
        this.CNPJ = CNPJ;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.businessBranch = businessBranch;
    }

    public Business() {}

    public void save(){
        firebaseDbReference.child("Users")
                .child(this.uId)
                .child(this.id)
                .setValue(this);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFantasyName() {
        return FantasyName;
    }

    public void setFantasyName(String fantasyName) {
        FantasyName = fantasyName;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBusinessBranch() {
        return businessBranch;
    }

    public void setBusinessBranch(String businessBranch) {
        this.businessBranch = businessBranch;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}
