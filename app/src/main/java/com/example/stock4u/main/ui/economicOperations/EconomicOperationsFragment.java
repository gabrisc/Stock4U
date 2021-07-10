package com.example.stock4u.main.ui.economicOperations;

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
import com.example.stock4u.adapters.AdapterEconomicOperation;
import com.example.stock4u.addScreens.AddEconomicOperationActivity;
import com.example.stock4u.databinding.FragmentEconomicOperationBinding;
import com.example.stock4u.entities.EconomicOperation;
import com.example.stock4u.updateScreens.UpdateClientActivity;
import com.example.stock4u.updateScreens.UpdateEconomicOperationsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.stock4u.util.FireBaseConfig.firebaseInstance;
import static com.example.stock4u.util.FireBaseConfig.getIdUser;


public class EconomicOperationsFragment extends Fragment implements AdapterEconomicOperation.OnEconomicOperationListerner{

    private EconomicOperationsViewModel economicOperationsViewModel;
    private FragmentEconomicOperationBinding binding;
    private AdapterEconomicOperation adapterEconomicOperation;
    private List<EconomicOperation> economicOperationList = new ArrayList<>();
    private Intent intent;
    private RecyclerView recyclerView;
    private int positionEconomicOperationSelect;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        economicOperationsViewModel = new ViewModelProvider(this).get(EconomicOperationsViewModel.class);

        binding = FragmentEconomicOperationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerViewSale);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapterEconomicOperation = new AdapterEconomicOperation(economicOperationList,root.getContext(), this);

        firebaseInstance.getReference()
                .child(getIdUser())
                .child("EconomicOperations")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        economicOperationList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            EconomicOperation economicOperationTemp = ds.getValue(EconomicOperation.class);
                            economicOperationList.add(economicOperationTemp);
                        }
                        adapterEconomicOperation.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String x = String.valueOf(error);
                    }
                });
        recyclerView.setAdapter(adapterEconomicOperation);

        ImageButton addEconomicOperationsButton = root.findViewById(R.id.AddEconomicOperationsButton);
        addEconomicOperationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext().getApplicationContext(), AddEconomicOperationActivity.class));
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
    public void onEconomicOperationClick(int position) {
        EconomicOperation operation = economicOperationList.get(position);
        positionEconomicOperationSelect =position;
        intent= new Intent(getContext().getApplicationContext(), UpdateEconomicOperationsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",operation.getId());
        bundle.putString("type",operation.getType());
        bundle.putDouble("ContributionValue",operation.getContributionValue());
        bundle.putDouble("ExpenseValue",operation.getExpenseValue());
        bundle.putString("Date",operation.getDate());
        bundle.putString("Name",operation.getName());
        bundle.putInt("Quantity",operation.getQuantity());
        bundle.putDouble("SealValue",operation.getSealValue());
        bundle.putString("typeQuantity",operation.getTypeQuantity());
        intent.putExtras(bundle);
        startActivity(intent);
    }

}