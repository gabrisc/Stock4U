package com.example.stock4u.updateScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stock4u.R;
import com.example.stock4u.databinding.FragmentEconomicOperationBinding;
import com.example.stock4u.entities.EconomicOperation;

import com.example.stock4u.main.ui.economicOperations.EconomicOperationsFragment;
import com.example.stock4u.util.TypeOfProduct;
import com.example.stock4u.util.TypeOfQuantity;

import static com.example.stock4u.util.TypeOfProduct.PRODUTO;
import static com.example.stock4u.util.TypeOfProduct.SERVIÇO;
import static com.example.stock4u.util.TypeOfQuantity.CAIXAS;
import static com.example.stock4u.util.TypeOfQuantity.KG;
import static com.example.stock4u.util.TypeOfQuantity.UND;
import static java.lang.Integer.parseInt;

public class UpdateEconomicOperationsActivity extends AppCompatActivity {


    private EconomicOperation economicOperation;
    private TextView counter,titleOfQuantity;
    private EditText name,expense,seal;
    private Spinner spinnerTypeOfQuantity;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_economic_operations);

        economicOperation = findEconomicOperationSelected();

        name = findViewById(R.id.editTextNameProductUpdate);
        seekBar = findViewById(R.id.seekBarForQuantityUpdate);
        expense = findViewById(R.id.editTextBuyValueUpdate);
        seal = findViewById(R.id.editTextSellValueUpdate);
        counter = findViewById(R.id.textViewQuantidadeUpdate);
        spinnerTypeOfQuantity = findViewById(R.id.spinnerUnidadeDeMedidaUpdateEO);
        titleOfQuantity=findViewById(R.id.textViewQuantidadeUpdateTitle);

        setSpinners();
        setValues();
        setButtonActions();
    }

    private void setButtonActions() {
        ImageButton UpdateButton = findViewById(R.id.updateImageButton);
        ImageButton cancelButton = findViewById(R.id.cancelImageButton);
        ImageButton deleteButton = findViewById(R.id.deleteImageButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEconomicOperation();
            }
        });
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
    }

    private void setValues() {
        name.setText(economicOperation.getName());
        expense.setText(String.format("%s", economicOperation.getExpenseValue()));
        seal.setText(String.format("%s", economicOperation.getSealValue()));
        counter.setText(String.format("%d", parseInt(String.valueOf(economicOperation.getQuantity()))));
        

        setSeekBar(10000,0,economicOperation.getQuantity());

        if(economicOperation.getType().equals(SERVIÇO.toString())){
            spinnerTypeOfQuantity.setVisibility(View.INVISIBLE);
            counter.setVisibility(View.INVISIBLE);
        }
    }

    private void setSpinners(){
        TypeOfQuantity[] listOfMed = {UND, CAIXAS, KG};
        ArrayAdapter adapter =new ArrayAdapter(getApplicationContext(),R.layout.item_list_spinner,listOfMed);

        spinnerTypeOfQuantity.setAdapter(adapter);

        spinnerTypeOfQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerTypeOfQuantity.getSelectedItem().toString().equals(SERVIÇO.toString())) {
                    seekBar.setVisibility(View.INVISIBLE);
                }else{
                    seekBar.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});
    }



    private void setSeekBar(Integer max,Integer min, Integer progress){
        seekBar.setMax(max);
        seekBar.setMin(min);
        seekBar.setProgress(progress);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                counter.setText("" + seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private EconomicOperation findEconomicOperationSelected() {
        EconomicOperation economicOperationSelect = new EconomicOperation();
        Bundle bundle = getIntent().getExtras();
        economicOperationSelect.setId(bundle.getString("id"));
        economicOperationSelect.setName(bundle.getString("Name"));
        economicOperationSelect.setType(bundle.getString("type"));
        economicOperationSelect.setSealValue(bundle.getDouble("SealValue"));
        economicOperationSelect.setExpenseValue(bundle.getDouble("ExpenseValue"));
        economicOperationSelect.setContributionValue(bundle.getDouble("ContributionValue"));
        economicOperationSelect.setQuantity(bundle.getInt("Quantity"));
        economicOperationSelect.setDate(bundle.getString("Date"));
        economicOperationSelect.setTypeQuantity("typeQuantity");
        return economicOperationSelect;
    }

    public void validateFields(){
        if (name.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com um nome",Toast. LENGTH_SHORT);
            toast. show();
        }
        if(expense.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de compra",Toast. LENGTH_SHORT);
            toast. show();
        }
        if(seal.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de venda",Toast. LENGTH_SHORT);
            toast. show();
        }
        if (economicOperation.getType().equals(TypeOfProduct.PRODUTO.toString()) && Integer.parseInt(counter.getText().toString())==0){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com uma quantidade",Toast. LENGTH_SHORT);
            toast. show();
        }else{
            updateEconomicOperation();
        }

    }

    private void saveProduct(EconomicOperation economicOperation) {
        economicOperation.save();
    }

    private void updateEconomicOperation(){
        economicOperation.setName(name.getText().toString());
        economicOperation.setSealValue(Double.parseDouble(seal.getText().toString()));
        economicOperation.setExpenseValue(Double.parseDouble(expense.getText().toString()));
        economicOperation.setTypeQuantity(spinnerTypeOfQuantity.getSelectedItem().toString());

        if(economicOperation.getType().equals(TypeOfProduct.PRODUTO.toString())){
            economicOperation.setQuantity(Integer.parseInt(counter.getText().toString()));
        }

        //economicOperation.setDate(date.getText().toString());
        economicOperation.setContributionValue(calcContributionValue(economicOperation.getSealValue(),economicOperation.getExpenseValue()));

        saveProduct(economicOperation);
        Toast.makeText(getApplicationContext(), "Alterado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), FragmentEconomicOperationBinding.class));
    }

    public void deleteEconomicOperation(){
        economicOperation.delete();
        Toast toast=Toast. makeText(getApplicationContext(),"Deletado",Toast. LENGTH_SHORT);
        toast.show();
        //startActivity(new Intent(getApplicationContext(), EconomicOperationsFragment.class));
        Bundle bundle = new Bundle();
        bundle.putInt("some_int", 0);


    }

    private double calcContributionValue(double sellValue,double buyValue) {
        return sellValue-buyValue;
    }
}