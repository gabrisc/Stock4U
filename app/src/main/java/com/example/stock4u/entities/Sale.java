package com.example.stock4u.entities;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static com.example.stock4u.util.FireBaseConfig.firebaseInstance;
import static com.example.stock4u.util.FireBaseConfig.getIdUser;

public class Sale {
    private List<EconomicOperationForSaleVo> economicOperationForSaleVoList;
    private String id,date,paymentType;
    private Client client;
    private Double totalValueFromProducts,totalValueFromExpenseValue,totalDiscountFromSeal,gain;


    public Sale(String id, String date, Client client) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.totalDiscountFromSeal = 0.0;
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

    public List<EconomicOperationForSaleVo> getEconomicOperationForSaleVoList() {
        return economicOperationForSaleVoList;
    }

    public void setEconomicOperationForSaleVoList(List<EconomicOperationForSaleVo> economicOperationForSaleVoList) {
        this.economicOperationForSaleVoList = economicOperationForSaleVoList;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getTotalDiscountFromSeal() {
        return totalDiscountFromSeal;
    }

    public void setTotalDiscountFromSeal(Double totalDiscountFromSeal) {
        this.totalDiscountFromSeal = totalDiscountFromSeal;
    }

    //valores calculados com base na lista de produtos da venda

    public Double getTotalValueFromProducts() {
        economicOperationForSaleVoList.forEach(economicOperationForSaleVo -> {
            totalValueFromProducts =(economicOperationForSaleVo.getEconomicOperation().getSealValue()*economicOperationForSaleVo.getQuantitySelect());
        });
        return totalValueFromProducts;
    }
    public Double getTotalValueFromProductsAndDiscount (){
        Double total= (getTotalValueFromProducts()+totalDiscountFromSeal);
    return total;
    }

    public Double getTotalValueFromExpenseValue() {
        economicOperationForSaleVoList.forEach(economicOperationForSaleVo -> {
            totalValueFromExpenseValue = (economicOperationForSaleVo.getEconomicOperation().getExpenseValue()*economicOperationForSaleVo.getQuantitySelect());
        });
        return totalValueFromExpenseValue;
    }

    public Double getGain() {
        gain=(getTotalValueFromProducts()+getTotalValueFromExpenseValue()) - getTotalDiscountFromSeal();
        return gain;
    }
}
