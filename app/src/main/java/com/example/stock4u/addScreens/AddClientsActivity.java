package com.example.stock4u.addScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stock4u.R;
import com.example.stock4u.entities.Client;
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

        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd/MM/yyyy");
        date.setText(simpleDateFormat.format(System.currentTimeMillis()));
    }

    public void cancelRegistrer (View view){
        startActivity(new Intent(getApplicationContext(), ClientFragment.class));
        this.finish();
    }
    public void listClients(){
        startActivity(new Intent(getApplicationContext(), ClientFragment.class));
    }

    public void ValidFields(View view){
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
        Toast toast=Toast. makeText(getApplicationContext(),client.save(),Toast. LENGTH_SHORT);
        toast. show();
        startActivity(new Intent(getApplicationContext(), ClientFragment.class));
    }
}