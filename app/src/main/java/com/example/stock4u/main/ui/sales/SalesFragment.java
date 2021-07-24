package com.example.stock4u.main.ui.sales;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stock4u.R;
import com.example.stock4u.adapters.AdapterSales;
import com.example.stock4u.addScreens.AddSealsActivity;

import com.example.stock4u.databinding.FragmentSealsBinding;
import com.example.stock4u.entities.Sale;
import com.example.stock4u.login.firstScreen.PageViewModel;
import com.example.stock4u.updateScreens.UpdateClientActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import static com.example.stock4u.util.FireBaseConfig.firebaseInstance;
import static com.example.stock4u.util.FireBaseConfig.getIdUser;

public class SalesFragment extends Fragment implements AdapterSales.OnSaleListerner {

    private SalesViewModel salesViewModel;
    private FragmentSealsBinding binding;
    private AdapterSales adapterSales;
    private List<Sale> saleList = new ArrayList<>();
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        salesViewModel = new ViewModelProvider(this).get(SalesViewModel.class);
        binding = FragmentSealsBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        ImageButton addSalesButton = root.findViewById(R.id.AddSalesButton);


        recyclerView = root.findViewById(R.id.recyclerViewSale);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        adapterSales =  new AdapterSales(saleList,root.getContext(),this::onSaleListenerClick);

        firebaseInstance.getReference()
                .child(getIdUser())
                .child("Sales")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                saleList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Sale saleTemp = ds.getValue(Sale.class);
                    saleList.add(saleTemp);
                }
                adapterSales.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String x = String.valueOf(error);
            }
        });
        recyclerView.setAdapter(adapterSales);

        addSalesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext().getApplicationContext(), AddSealsActivity.class));
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSaleListenerClick(int position) {

    }

}