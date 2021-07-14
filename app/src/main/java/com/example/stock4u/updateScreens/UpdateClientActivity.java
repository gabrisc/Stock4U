package com.example.stock4u.updateScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stock4u.R;
import com.example.stock4u.entities.Client;
import com.example.stock4u.main.ui.clients.ClientFragment;

import java.text.SimpleDateFormat;

public class UpdateClientActivity extends AppCompatActivity {

    private Client clientSelect;
    private TextView date;
    private EditText name, email, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);

        clientSelect = findClientSelected();

        date = findViewById(R.id.TextViewDateClientUpdate);
        name = findViewById(R.id.editTextClientNameUpdate);
        email = findViewById(R.id.editTextEmailAddressUpdate);
        phoneNumber = findViewById(R.id.editTextPhoneUpdate);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd/MM/yyyy");

        date.setText(simpleDateFormat.format(System.currentTimeMillis()));
        name.setText(clientSelect.getNome());
        email.setText(clientSelect.getEmail());
        phoneNumber.setText(clientSelect.getTelefone());
        date.setText(clientSelect.getDate());

        ImageButton imageButtonUpdateClient = findViewById(R.id.imageButtonUpdateClient);
        imageButtonUpdateClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });
        ImageButton imageButtonCancelUpdateClient = findViewById(R.id.imageButtonCancelUpdateClient);
        imageButtonCancelUpdateClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ImageButton imageButtonDeleteClient = findViewById(R.id.imageButtonDeleteClient);
        imageButtonDeleteClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEconomicOperation();
            }
        });
    }

    private Client findClientSelected() {
        Client client = new Client();
        Bundle bundle = getIntent().getExtras();

        client.setId(bundle.getString("id"));
        client.setNome(bundle.getString("name"));
        client.setEmail(bundle.getString("email"));
        client.setTelefone(bundle.getString("phoneNumber"));
        client.setDate(bundle.getString("date"));

        return client;
    }

    private void validateFields(){
        if (name.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Não deixe o nome vazio",Toast. LENGTH_SHORT);
            toast. show();
        }else if(email.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Não deixe o email vazio",Toast. LENGTH_SHORT);
            toast. show();
        }else if(phoneNumber.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Não deixe o telefone vazio",Toast. LENGTH_SHORT);
            toast. show();
        } else{
            updateClient();
        }

    }

    private void saveClient(Client client) {
        client.save();
    }

    private void updateClient(){

        clientSelect.setNome(name.getText().toString());
        clientSelect.setTelefone(phoneNumber.getText().toString());
        clientSelect.setEmail(email.getText().toString());
        clientSelect.setDate(date.getText().toString());

        saveClient(clientSelect);
        Toast.makeText(this, "Alterado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), ClientFragment.class));
    }

    private void deleteEconomicOperation(){
        clientSelect.delete();
        Toast toast=Toast. makeText(this,"Deletado",Toast. LENGTH_SHORT);
        toast.show();
        startActivity(new Intent(getApplicationContext(), ClientFragment.class));
    }

    private double calcContributionValue(double sellValue,double buyValue) {
        return sellValue-buyValue;
    }
}