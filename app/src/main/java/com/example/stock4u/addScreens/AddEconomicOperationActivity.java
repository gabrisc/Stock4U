package com.example.stock4u.addScreens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stock4u.R;
import com.example.stock4u.entities.EconomicOperation;
import com.example.stock4u.main.ui.economicOperations.EconomicOperationsFragment;
import com.example.stock4u.util.TypeOfProduct;
import com.example.stock4u.util.TypeOfQuantity;

import java.text.SimpleDateFormat;

import static com.example.stock4u.util.FireBaseConfig.firebaseDbReference;
import static com.example.stock4u.util.TypeOfProduct.PRODUTO;
import static com.example.stock4u.util.TypeOfProduct.SERVIÇO;
import static com.example.stock4u.util.TypeOfQuantity.CAIXAS;
import static com.example.stock4u.util.TypeOfQuantity.KG;
import static com.example.stock4u.util.TypeOfQuantity.UND;
import static java.lang.Integer.parseInt;

public class AddEconomicOperationActivity extends AppCompatActivity {

    private Spinner spinnerProductType,spinnerUnidadeDeMedida;
    private TextView editTextQuantidade;
    private SeekBar seekBar;
    private AlertDialog alertDialog;
    private TextView titleQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economic_operation);

        seekBar = findViewById(R.id.seekBarForQuantity);
        editTextQuantidade = findViewById(R.id.textViewQuantidade);
        spinnerProductType = findViewById(R.id.ProductType);
        spinnerUnidadeDeMedida= findViewById(R.id.spinnerUnidadeDeMedida);
        titleQuantity= findViewById(R.id.textViewQuantidade2);

        ImageButton buttonCadastrarEO = findViewById(R.id.imageButtonConclusion);
        ImageButton buttonCancelarEO = findViewById(R.id.imageButtonCancel);
        ImageButton ButtonHelpToCalc = findViewById(R.id.imageButtonHelpToCalc);

        updateSeekBar(10000,0,0);
        setSpinners();

        buttonCadastrarEO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            validFields();
            }
        });
        buttonCancelarEO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRegistrer();
            }
        });
        ButtonHelpToCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogCalcSellValue();
            }
        });


    }
    private void setDialogCalcSellValue(){
        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_calc_sell_value,null);
        ImageButton ButtonNotAcceptValue = mDialogView.findViewById(R.id.imageButtonNotAcceptValue);
        ImageButton ButtonAcceptValue = mDialogView.findViewById(R.id.imageButtonAcceptValue);

        EditText mostBiggerValue = mDialogView.findViewById(R.id.editTextMostBiggerValue);
        EditText smallPriceFound = mDialogView.findViewById(R.id.editTextSmallPriceFound);
        EditText mostCommomValue = mDialogView.findViewById(R.id.editTextMostCommomValue);

        TextView sugestionValue = mDialogView.findViewById(R.id.textViewSugestionValue);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(mDialogView).setTitle("DESCONTO");

        alertDialog=builder.create();

        alertDialog.show();
    }

    private void setSpinners(){
        TypeOfProduct[] listOfPaymentsType = {PRODUTO, SERVIÇO};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.item_list_spinner, listOfPaymentsType);
        TypeOfQuantity[] listOfMed = {UND, CAIXAS, KG};
        ArrayAdapter adapter =new ArrayAdapter(getApplicationContext(),R.layout.item_list_spinner,listOfMed);
        spinnerUnidadeDeMedida.setAdapter(adapter);
        spinnerProductType.setAdapter(arrayAdapter);

        spinnerProductType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerProductType.getSelectedItem().toString().equals(SERVIÇO.toString())) {
                    titleQuantity.setVisibility(View.INVISIBLE);
                    spinnerUnidadeDeMedida.setVisibility(View.INVISIBLE);
                    editTextQuantidade.setVisibility(View.INVISIBLE);
                    seekBar.setVisibility(View.INVISIBLE);
                }else{
                    spinnerUnidadeDeMedida.setVisibility(View.VISIBLE);
                    titleQuantity.setVisibility(View.VISIBLE);
                    editTextQuantidade.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    editTextQuantidade.setText("0");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});

    }


    public void updateSeekBar(Integer max,Integer min, Integer progress){
        seekBar.setMax(max);
        seekBar.setMin(min);
        seekBar.setProgress(progress);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editTextQuantidade.setText("" + seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }


    private void validFields(){
        EditText productName = findViewById(R.id.editTextNameProduct);
        EditText buyValue = findViewById(R.id.editTextBuyValue);
        EditText sellValue = findViewById(R.id.editTextSellValue);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");

        if (productName.getText().toString().isEmpty() ){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com um nome",Toast. LENGTH_SHORT);
            toast. show();
        }

        if(buyValue.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de compra do produto",Toast. LENGTH_SHORT);
            toast. show();
        }

        if (sellValue.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de venda do produto",Toast. LENGTH_SHORT);
            toast. show();
        }

        if(spinnerProductType.getSelectedItem()!=SERVIÇO && parseInt(editTextQuantidade.getText().toString())==0){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com a quantidade de produtos",Toast. LENGTH_SHORT);
            toast. show();
        }

        if (spinnerProductType.getSelectedItem()==null){
            Toast toast=Toast. makeText(getApplicationContext(),"Escolha um tipo",Toast. LENGTH_SHORT);
            toast. show();
        }

        if (spinnerProductType.getSelectedItem()== PRODUTO){
            if (Integer.parseInt(editTextQuantidade.getText().toString())==0){
                Toast toast=Toast. makeText(getApplicationContext(),"Adicione a quantidade",Toast. LENGTH_SHORT);
                toast. show();
            }else{
                saveProduct(new EconomicOperation(productName.getText().toString(),Double.parseDouble(sellValue.getText().toString()),
                        Double.parseDouble(buyValue.getText().toString()), spinnerProductType.getSelectedItem().toString(),
                        Integer.parseInt(editTextQuantidade.getText().toString()),simpleDateFormat.format(System.currentTimeMillis()),
                        calcContributionValue(Double.parseDouble(sellValue.getText().toString()),Double.parseDouble(buyValue.getText().toString())),
                        spinnerUnidadeDeMedida.getSelectedItem().toString()));
            }
        }
        if (spinnerProductType.getSelectedItem()==SERVIÇO){
            saveProduct(new EconomicOperation(productName.getText().toString(),
                    Double.parseDouble(sellValue.getText().toString()),
                    Double.parseDouble(buyValue.getText().toString()),
                    spinnerProductType.getSelectedItem().toString(),
                    simpleDateFormat.format(System.currentTimeMillis()),
                    calcContributionValue(Double.parseDouble(sellValue.getText().toString()),Double.parseDouble(buyValue.getText().toString()))
            ));
        }
    }

    private double calcContributionValue(double sellValue,double buyValue) {
        return sellValue-buyValue;
    }

    private void saveProduct(EconomicOperation economicOperation) {
        economicOperation.setId(firebaseDbReference.push().getKey());
        economicOperation.save();
        Toast.makeText(AddEconomicOperationActivity.this, "Adicionado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), EconomicOperationsFragment.class));
    }

    private void cancelRegistrer(){
        startActivity(new Intent(getApplicationContext(), EconomicOperationsFragment.class));
    }

}