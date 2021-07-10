package com.example.stock4u.entities;

import androidx.annotation.NonNull;

import com.example.stock4u.util.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import static com.example.stock4u.util.FireBaseConfig.firebaseAuth;
import static com.example.stock4u.util.FireBaseConfig.firebaseInstance;
import static com.example.stock4u.util.FireBaseConfig.getIdUser;

public class Sale {
    private List<EconomicOperationForSaleVo> economicOperationForSaleVoList;
    private String id,date,paymentType;
    private Client client;
    private Double totalValue,discount,gain;

    public Sale(String id, String date, Client client, Double totalValue, Double discount) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.totalValue = totalValue;
    }

    public void save(){
        firebaseInstance.getReference()
                .child(getIdUser())
                .child("Sales")
                .child(this.getId())
                .setValue(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }});
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getGain() {
        return gain;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }

    public List<EconomicOperationForSaleVo> getEconomicOperationForSaleVoList() {
        return economicOperationForSaleVoList;
    }

    public void setEconomicOperationForSaleVoList(List<EconomicOperationForSaleVo> economicOperationForSaleVoList) {
        this.economicOperationForSaleVoList = economicOperationForSaleVoList;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
