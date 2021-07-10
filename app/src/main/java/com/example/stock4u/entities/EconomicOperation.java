package com.example.stock4u.entities;

import androidx.annotation.NonNull;

import com.example.stock4u.util.Base64Custom;
import com.example.stock4u.util.TypeOfProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import static com.example.stock4u.util.FireBaseConfig.firebaseAuth;
import static com.example.stock4u.util.FireBaseConfig.firebaseInstance;
import static com.example.stock4u.util.FireBaseConfig.getIdUser;

public class EconomicOperation {

     private String id;
     private String Name;
     private double SealValue;
     private double ExpenseValue;
     private String type;
     private String mensage;
     private int quantity;
     private String date;
     private double contributionValue;
     private String typeQuantity;

     public EconomicOperation(String name, double sealValue, double expenseValue, String type, String date, double contributionValue) {
          this.Name = name;
          this.SealValue = sealValue;
          this.ExpenseValue = expenseValue;
          this.type = type;
          this.date = date;
          this.contributionValue = contributionValue;
     }

     public EconomicOperation(String name, double sealValue, double expenseValue, String type, int quantity, String date, double contributionValue, String typeQuantity) {
          Name = name;
          SealValue = sealValue;
          ExpenseValue = expenseValue;
          this.type = type;
          this.quantity = quantity;
          this.date = date;
          this.contributionValue = contributionValue;
          this.typeQuantity = typeQuantity;
     }

     public EconomicOperation(){}

     public String save(){
          firebaseInstance.getReference()
                  .child(getIdUser())
                  .child("EconomicOperations")
                  .child(String.valueOf(this.getId()))
                  .setValue(this)
                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                            mensage= "Adicionado";
                       }
                  });
          return mensage;
     }

     public String delete(){
          firebaseInstance.getReference()
                  .child(getIdUser())
                  .child("EconomicOperations")
                  .child(String.valueOf(this.getId()))
                  .removeValue();
          return "Produto Removido";
     }

     public static List<EconomicOperation> findAll(){
          List<EconomicOperation> economicOperationList= new ArrayList<>();
          firebaseInstance.getReference()
                  .child(getIdUser())
                  .child("ProductsAndServices")
                  .child("PRODUCTS").addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                    economicOperationList.clear();
                    for (DataSnapshot ds: snapshot.getChildren()){
                         EconomicOperation economicOperationTemp = ds.getValue(EconomicOperation.class);
                         economicOperationList.add(economicOperationTemp);
                    }

               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                    String x = String.valueOf(error);
               }
          });
          return economicOperationList;
     }

     public String update(){
          firebaseInstance.getReference()
                  .child(getIdUser())
                  .child("ProductsAndServices")
                  .child("PRODUCTS")
                  .child(String.valueOf(this.getId())).setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                    mensage= "Produto Atualizado";
               }
          });
          return mensage;
     }

     public String getId() {
          return id;
     }

     public String getDate() {
          return date;
     }

     public void setDate(String date) {
          this.date = date;
     }

     public void setId(String id) {
          this.id = id;
     }

     public String getName() {
          return Name;
     }

     public void setName(String name) {
          Name = name;
     }

     public double getSealValue() {
          return SealValue;
     }

     public void setSealValue(double sealValue) {
          SealValue = sealValue;
     }

     public double getExpenseValue() {
          return ExpenseValue;
     }

     public void setExpenseValue(double expenseValue) {
          ExpenseValue = expenseValue;
     }

     public String getType() {
          return type;
     }

     public void setType(String type) {
          this.type = type;
     }

     public void setTypeWithProduct() {
          this.type = String.valueOf(TypeOfProduct.PRODUTO);
     }
     public void setTypeWithService() {
          this.type = String.valueOf(TypeOfProduct.SERVIÇO);
     }

     public int getQuantity() {
          return quantity;
     }

     public void setQuantity(int quantity) {
          this.quantity = quantity;
     }

     public double getContributionValue() {
          return contributionValue;
     }

     public void setContributionValue(double contributionValue) { this.contributionValue = contributionValue; }

     public String getTypeQuantity() { return typeQuantity; }

     public void setTypeQuantity(String typeQuantity) { this.typeQuantity = typeQuantity; }
}
