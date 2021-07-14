package com.example.stock4u.addScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stock4u.R;
import com.example.stock4u.adapters.AdapterClient;
import com.example.stock4u.adapters.AdapterEconomicOperationForSales;
import com.example.stock4u.entities.Client;
import com.example.stock4u.entities.EconomicOperation;
import com.example.stock4u.entities.EconomicOperationForSaleVo;
import com.example.stock4u.util.TypeOfProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.example.stock4u.util.FireBaseConfig.firebaseInstance;
import static com.example.stock4u.util.FireBaseConfig.getIdUser;
import static java.lang.Integer.parseInt;

public class AddSealsActivity extends AppCompatActivity implements AdapterEconomicOperationForSales.OnEconomicOperationForSaleListener, AdapterClient.OnClientListener{

    private AdapterEconomicOperationForSales adapterProduct;
    private AdapterClient adapterClient;
    private List<EconomicOperation> listProduct= new ArrayList<>();
    private List<Client> clientList= new ArrayList<>();
    public final static Set<EconomicOperationForSaleVo> economicOperationForSaleVoArrayList = new HashSet<>();
    public static Client clientSelected;
    private RecyclerView recyclerView;
    private AlertDialog alertDialog;
    private TextView textViewOrder;
    private  ImageButton buttonConclusionSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seals);
        buttonConclusionSelect = findViewById(R.id.imageButtonConclusionSelect);
        textViewOrder= findViewById(R.id.textViewOrder);
        findAllClients();
        reloadRecyclerClient();
        buttonConclusionSelect.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
        economicOperationForSaleVoArrayList.clear();
        //clientSelected=null;
    }



    private void callDialogForProduct(EconomicOperation economicOperationSelect,int position,Boolean isService){
        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_item,null);

        TextView textViewMensage = mDialogView.findViewById(R.id.TextViewTitle);
        TextView counter = mDialogView.findViewById(R.id.textViewCounterAddQuantity);

        ImageButton conclusionButton = mDialogView.findViewById(R.id.imageButtonConclusionAddQuantity);
        ImageButton cancelButton = mDialogView.findViewById(R.id.imageButtonCancelAddQuantity);

        SeekBar seekBar = mDialogView.findViewById(R.id.seekBarQuantityForAddInSale);

        counter.setText("0");
        seekBar.setMax(economicOperationSelect.getQuantity());
        seekBar.setMin(0);

        if (isService){
            textViewMensage.setText("Deseja adicionar esse serviço?");
            conclusionButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
            counter.setVisibility(View.INVISIBLE);
            seekBar.setVisibility(View.INVISIBLE);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(mDialogView).setTitle("Quantidade");
        alertDialog=builder.create();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                counter.setText(String.format("%d", i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        conclusionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter.getText().toString().equals("0")) {
                    Toast.makeText(AddSealsActivity.this, "Selecione uma quantidade", Toast.LENGTH_SHORT).show();
                } else {
                if (Integer.parseInt(counter.getText().toString()) == economicOperationSelect.getQuantity()) {
                    addEconomicOperation(new EconomicOperationForSaleVo(economicOperationSelect, economicOperationSelect.getQuantity()));
                    listProduct.remove(position);
                    reloadRecyclerEconomicOperation();
                    alertDialog.dismiss();
                    buttonVisibilityEnable(true);
                } else {
                    int quantityResult = economicOperationSelect.getQuantity() - Integer.parseInt(counter.getText().toString());
                    listProduct.get(position).setQuantity(quantityResult);
                    addEconomicOperation(new EconomicOperationForSaleVo(economicOperationSelect, Integer.parseInt(counter.getText().toString())));
                    reloadRecyclerEconomicOperation();
                    alertDialog.dismiss();
                    buttonVisibilityEnable(true);
                }
            }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        alertDialog.show();
    }

    private void addEconomicOperation(EconomicOperationForSaleVo economicOperationForSaleVo){
        boolean find =false;
        for(Iterator<EconomicOperationForSaleVo> it = economicOperationForSaleVoArrayList.iterator(); it.hasNext();){
            EconomicOperationForSaleVo e= it.next();
            if (e.getEconomicOperation().getId().equals(economicOperationForSaleVo.getEconomicOperation().getId())){
                e.setQuantitySelect(economicOperationForSaleVo.getQuantitySelect() + e.getQuantitySelect());
                //economicOperationForSaleVoArrayList.add(e);
                find=true;
            }
        }
        if(!find){
            economicOperationForSaleVoArrayList.add(economicOperationForSaleVo);
        }
    }

    public void fechandoVenda(View view){
        //startActivity( new Intent(getApplicationContext(), ClosingSaleActivity.class));
    }

    public void CallAddClient(View view){
        //startActivity(new Intent(getApplicationContext(),AddClientsActivity.class));
    }

//#################################  VISIBILIDADE DOS BOTOES  ######################################

    private void buttonVisibilityEnable(Boolean enable){
        if (enable.equals(Boolean.TRUE)){
            buttonConclusionSelect.setVisibility(View.VISIBLE);
        }else{
            buttonConclusionSelect.setVisibility(View.INVISIBLE);
        }
    }

//##################################################################################################

    private void reloadRecyclerEconomicOperation(){
        textViewOrder.setText("SELECIONE O QUE SERÁ VENDIDO");
        recyclerView= findViewById(R.id.recyclerViewprodutosavenda);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterProduct = new AdapterEconomicOperationForSales(listProduct,getApplicationContext(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterProduct);
    }

    private void findAllEconomicOperation(){
        firebaseInstance.getReference()
                .child(getIdUser())
                .child("EconomicOperations")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listProduct.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            EconomicOperation economicOperation= ds.getValue(EconomicOperation.class);
                            listProduct.add(economicOperation);
                        }
                        adapterProduct.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String x = String.valueOf(error);
                    }
                });
    }

    @Override
    public void onEconomicOperationForSaleClick(int position) {
        buttonConclusionSelect.setVisibility(View.VISIBLE);
        EconomicOperation economicOperationSelect = listProduct.get(position);
        if (economicOperationSelect.getType().equals(TypeOfProduct.PRODUTO.toString())){
            callDialogForProduct(economicOperationSelect,position,false);
        }else{
            callDialogForProduct(economicOperationSelect,position,true);
        }
    }

//##################################################################################################

    private void reloadRecyclerClient(){
        textViewOrder.setText("SELECIONE O CLIENTE");
        recyclerView= findViewById(R.id.recyclerViewprodutosavenda);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterClient = new AdapterClient(clientList,getApplicationContext(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterClient);
    }

    private void findAllClients(){
        firebaseInstance.getReference()
                .child(getIdUser())
                .child("Clients")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        clientList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Client client= ds.getValue(Client.class);
                            clientList.add(client);
                        }
                        adapterClient.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String x = String.valueOf(error);
                    }
                });
    }

    @Override
    public void onClientOperationClick(int position) {
        clientSelected = clientList.get(position);
        clientList.clear();
        findAllEconomicOperation();
        reloadRecyclerEconomicOperation();
    }

}