package com.example.stock4u.addScreens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stock4u.R;
import com.example.stock4u.adapters.AdapterEconomicOperationForSaleVo;
import com.example.stock4u.entities.EconomicOperationForSaleVo;
import com.example.stock4u.entities.Sale;
import com.example.stock4u.login.MainActivity;
import com.example.stock4u.main.ui.sales.SalesFragment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.example.stock4u.addScreens.AddSealsActivity.clientSelected;
import static com.example.stock4u.addScreens.AddSealsActivity.economicOperationForSaleVoArrayList;
import static com.example.stock4u.util.FireBaseConfig.firebaseDbReference;

public class ClosingSaleActivity extends AppCompatActivity implements AdapterEconomicOperationForSaleVo.OnEconomicOperationForSaleVo{

    private Spinner spinnerPaymentstype;
    private RecyclerView recyclerView;
    private AlertDialog alertDialog;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private Set<EconomicOperationForSaleVo> set = new HashSet<>();
    private Sale sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closing_sale);

        recyclerView = findViewById(R.id.RecyclerViewEconomicOperationClosingSale);
        TextView clientName = findViewById(R.id.textViewClientNameClosingSale);
        ImageButton conclusion = findViewById(R.id.imageButtonConclusionSaleAddButton);
        ImageButton cancel = findViewById(R.id.imageButtonConclusionSaleCancelButton);
        spinnerPaymentstype = findViewById(R.id.listOfPaymentsTypeClosingSale);
        TextView date = findViewById(R.id.TextViewDateOfBuyClosingSale);

        sale = new Sale(firebaseDbReference.push().getKey(),simpleDateFormat.format(System.currentTimeMillis()),clientSelected);
        set.addAll(economicOperationForSaleVoArrayList);

        addListToSale();
        loadList();
        setFinalValue();

        setListPaymentsTypes(sale);
        clientName.setText(sale.getClient().getNome().toUpperCase());

        date.setText(simpleDateFormat.format(System.currentTimeMillis()));


        conclusion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountDialog();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    private void setFinalValue(){
        TextView finalText = findViewById(R.id.textViewFinalValueClosingSale);
        finalText.setText("R$:"+sale.getTotalValueFromProductsAndDiscount());
    }

    public void loadList(){
        AdapterEconomicOperationForSaleVo adapterEconomicOperationForSaleVo = new AdapterEconomicOperationForSaleVo(set,getApplicationContext(), this::onEconomicOperationForSaleVoClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterEconomicOperationForSaleVo);
    }

    private void setListPaymentsTypes(Sale sale){
        String[] listOfPaymentsType = {"DEBITO","CREDITO","DINHEIRO","CHEQUE","BOLETO","TRANSF. BANCARIA","OUTROS"};

        spinnerPaymentstype.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.item_list_spinner, listOfPaymentsType));
        sale.setPaymentType(spinnerPaymentstype.getSelectedItem().toString());
    }

    public void discountDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Deseja aplicar um desconto ?");

        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                applyDiscount(sale);
            }
        });

        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeSeal(sale);
            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void closeSeal(Sale sale){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Deseja concluir a venda?\n Total: R$ "+ sale.getTotalValueFromProducts());
        alertDialog.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveSale(sale);
            }
        });

        alertDialog.setNegativeButton("não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void addListToSale(){
        List<EconomicOperationForSaleVo> economicOperationForSaleVoList= new ArrayList<>();
        for(Iterator<EconomicOperationForSaleVo> it = set.iterator(); it.hasNext();){
            EconomicOperationForSaleVo e= it.next();
            economicOperationForSaleVoList.add(e);
        }
        sale.setEconomicOperationForSaleVoList(economicOperationForSaleVoList);
    }

    private void saveSale(Sale sale) {
        addListToSale();
        sale.save();
        Toast.makeText(ClosingSaleActivity.this, "Adicionado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onEconomicOperationForSaleVoClick(int position) {
        EconomicOperationForSaleVo economicOperationForSaleVo = set.iterator().next();
        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_item,null);

        ImageButton buttonDeleteEconomicOperation=mDialogView.findViewById(R.id.imageButtonCancelAddQuantity);
        ImageButton buttonAddQuantity=mDialogView.findViewById(R.id.imageButtonConclusionAddQuantity);
        SeekBar seekBar=mDialogView.findViewById(R.id.seekBarQuantityForAddInSale);
        TextView counter = mDialogView.findViewById(R.id.textViewCounterAddQuantity);

        seekBar.setProgress(Integer.parseInt(String.valueOf(economicOperationForSaleVo.getQuantitySelect())));
        seekBar.setMax(economicOperationForSaleVo.getEconomicOperation().getQuantity());
        counter.setText(String.valueOf(economicOperationForSaleVo.getQuantitySelect()));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                counter.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }});
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(mDialogView).setTitle("Quantidade");
        alertDialog=builder.create();

        buttonAddQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(counter.getText().toString())!=0){
                    economicOperationForSaleVo.setQuantitySelect(Integer.valueOf(counter.getText().toString()));
                    loadList();
                    setFinalValue();
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(ClosingSaleActivity.this, "Selecione uma quantidade", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonDeleteEconomicOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set.remove(position);
                if (set.isEmpty()){
                    startActivity(new Intent(getApplicationContext(), SalesFragment.class));
                    try { this.finalize(); } catch (Throwable throwable) { throwable.printStackTrace(); }
                }
                loadList();
                setFinalValue();
            }});
        alertDialog.show();

    }



    private void applyDiscount(Sale sale){
        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_discount,null);

        ImageButton butttonCancel = mDialogView.findViewById(R.id.buttonDeleteDiscountClosingSale);
        ImageButton buttonAddDiscount = mDialogView.findViewById(R.id.buttonAddDiscountClosingSale);
        EditText editText = mDialogView.findViewById(R.id.editTextDiscountClosingSale);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(mDialogView).setTitle("DESCONTO");
        alertDialog=builder.create();
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        buttonAddDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sale.setTotalDiscountFromSeal(Double.valueOf(editText.getText().toString()));

                if (sale.getTotalDiscountFromSeal().equals(0)){
                    Toast.makeText(ClosingSaleActivity.this, "Adicione um valor", Toast.LENGTH_SHORT).show();

                }if (sale.getTotalValueFromProductsAndDiscount().equals(sale.getTotalDiscountFromSeal())) {
                    Toast.makeText(ClosingSaleActivity.this, "O desconto esta iqual ao total", Toast.LENGTH_SHORT).show();

                }else{
                    closeSeal(sale);
                }

                alertDialog.dismiss();
            }});
        butttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSeal(sale);
            }
        });
        alertDialog.show();
    }

}