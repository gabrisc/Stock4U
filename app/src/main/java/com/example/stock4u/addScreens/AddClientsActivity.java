package com.example.stock4u.addScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stock4u.R;
import com.example.stock4u.databinding.FragmentClientBinding;
import com.example.stock4u.entities.Client;
import com.example.stock4u.main.CrudsMenusActivity;
import com.example.stock4u.main.ui.clients.ClientFragment;

import java.text.SimpleDateFormat;

import static com.example.stock4u.util.FireBaseConfig.firebaseDbReference;

public class AddClientsActivity extends AppCompatActivity {
    private TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clients);
        date = findViewById(R.id.TextViewDateClient);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd/MM/yyyy");
        date.setText(simpleDateFormat.format(System.currentTimeMillis()));


        ImageButton imageButtonAddClient = findViewById(R.id.imageButtonAddClient);
        imageButtonAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validFields();
            }
        });
        ImageButton imageButtonCancelAddClient = findViewById(R.id.imageButtonCancelAddClient);

        imageButtonCancelAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FragmentClientBinding.class));
            }
        });
    }

    public void cancelRegistrer (View view){
        startActivity(new Intent(getApplicationContext(), CrudsMenusActivity.class));
        this.finish();
    }
    public void listClients(){
        startActivity(new Intent(getApplicationContext(), CrudsMenusActivity.class));
    }

    private void validFields(){
        EditText name = findViewById(R.id.editTextClientName);
        EditText email= findViewById(R.id.editTextEmailAddress);
        EditText telefone = findViewById(R.id.editTextPhone);

        if (name.getText().equals(null)){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o nome",Toast. LENGTH_SHORT);
            toast. show();
        }else if (email.getText().equals(null) || telefone.getText().equals(null)){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o email ou telefone",Toast. LENGTH_SHORT);
            toast. show();
        }else{
            saveClient(new Client(firebaseDbReference.push().getKey(),
                    name.getText().toString(),
                    email.getText().toString(),
                    telefone.getText().toString(),
                    date.getText().toString()));
        }
    }

    private void saveClient(Client client) {
        client.save();
        Toast toast=Toast. makeText(getApplicationContext(),"Cadastrado",Toast. LENGTH_SHORT);
        toast. show();
        startActivity(new Intent(getApplicationContext(), CrudsMenusActivity.class));
    }
}