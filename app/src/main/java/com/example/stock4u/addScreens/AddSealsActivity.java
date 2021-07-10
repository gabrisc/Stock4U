package com.example.stock4u.addScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private RecyclerView recyclerViewprodutosavenda;
    private AlertDialog alertDialog;
    private TextView textViewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seals);

    }


    @Override
    public void onClientOperationClick(int position) {

    }

    @Override
    public void onEconomicOperationForSaleClick(int position) {

    }
}