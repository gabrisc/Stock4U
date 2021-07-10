package com.example.stock4u.main.ui.ec_mei;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stock4u.R;
import com.example.stock4u.adapters.AdapterEconomicOperation;
import com.example.stock4u.addScreens.AddSealsActivity;
import com.example.stock4u.databinding.FragmentEcMeiBinding;
import com.example.stock4u.entities.Business;
import com.example.stock4u.entities.EconomicOperation;
import com.example.stock4u.login.firstScreen.PageViewModel;
import com.example.stock4u.updateScreens.UpdateClientActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.stock4u.util.FireBaseConfig.firebaseAuth;
import static com.example.stock4u.util.FireBaseConfig.firebaseInstance;
import static com.example.stock4u.util.FireBaseConfig.getIdUser;


public class EC_MEI_Fragment extends Fragment {

    private EC_MEI_ViewModel ECMEIViewModel;
    private FragmentEcMeiBinding binding;
    private PageViewModel pageViewModel;
    private AdapterEconomicOperation adapterEconomicOperation;
    private List<EconomicOperation> economicOperationList = new ArrayList<>();
    private Intent intent;
    private RecyclerView recyclerView;
    private int positionEconomicOperationSelect;
    private Business currentBusiness = new Business();
    private ImageButton imageButtonEdict,imageButtonShare;
    private TextView businessBranch,fantasyName,CNPJorCPF,adress,phoneNumber,email;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ECMEIViewModel = new ViewModelProvider(this).get(EC_MEI_ViewModel.class);
        binding = FragmentEcMeiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageButton addEcMeiButton = root.findViewById(R.id.AddEcMeiButton);
        addEcMeiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getContext().getApplicationContext(), AddSealsActivity.class));

            }
        });


        imageButtonEdict = root.findViewById(R.id.imageButtonEdit);
        imageButtonShare = root.findViewById(R.id.imageButtonShare);
        businessBranch = root.findViewById(R.id.editTextBusinessBranchFragmentMain);
        fantasyName = root.findViewById(R.id.textViewFantasyNameFragmentMain);
        CNPJorCPF = root.findViewById(R.id.textViewCodeFragmentMain);
        adress = root.findViewById(R.id.textViewAdressFragmentMain);
        phoneNumber = root.findViewById(R.id.textViewPhoneNumberFragmentMain);
        email = root.findViewById(R.id.textViewEmailFragmentMain);

        firebaseInstance.getReference()
                .child("Users")
                .child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Business u= ds.getValue(Business.class);
                            currentBusiness = u;
                            AddValues();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void AddValues(){
        businessBranch.setText(currentBusiness.getBusinessBranch());
        fantasyName.setText(currentBusiness.getFantasyName());
        CNPJorCPF.setText(currentBusiness.getCNPJ());
        adress.setText(currentBusiness.getAdress());
        phoneNumber.setText(currentBusiness.getPhoneNumber());
        email.setText(currentBusiness.getEmail());

        imageButtonEdict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}