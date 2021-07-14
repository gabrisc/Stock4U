package com.example.stock4u.main.ui.clients;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stock4u.R;
import com.example.stock4u.adapters.AdapterClient;
import com.example.stock4u.addScreens.AddClientsActivity;

import com.example.stock4u.databinding.FragmentClientBinding;
import com.example.stock4u.entities.Client;
import com.example.stock4u.login.firstScreen.PageViewModel;
import com.example.stock4u.updateScreens.UpdateClientActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.stock4u.util.FireBaseConfig.firebaseInstance;
import static com.example.stock4u.util.FireBaseConfig.getIdUser;

public class ClientFragment extends Fragment implements AdapterClient.OnClientListener{

    private ClientViewModel testeViewModel;
    private FragmentClientBinding binding;
    private static final String ARG_SECTION_NUMBER = "3";
    private PageViewModel pageViewModel;
    private Intent intent;
    private RecyclerView recyclerView;
    private List<Client> clientList= new ArrayList<>();
    private int positionEconomicOperationSelect;

    public static ClientFragment newInstance() {
        return new ClientFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        testeViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        binding = FragmentClientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        recyclerView = root.findViewById(R.id.recyclerViewClients);
        AdapterClient adapterClient = new AdapterClient(clientList,root.getContext(),this::onClientOperationClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setHasFixedSize(true);

        firebaseInstance.getReference()
                .child(getIdUser())
                .child("Clients")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        clientList.clear();
                        for (DataSnapshot ds:snapshot.getChildren()){
                            Client client = ds.getValue(Client.class);
                            clientList.add(client);
                        }
                        adapterClient.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String x =String.valueOf(error);
                    }
                });
        recyclerView.setAdapter(adapterClient);


        ImageButton addClientButton = root.findViewById(R.id.AddClientButton);
        addClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext().getApplicationContext(), AddClientsActivity.class));
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
    public void onClientOperationClick(int position) {
        Client client = clientList.get(position);
        positionEconomicOperationSelect =position;
        intent= new Intent(getContext().getApplicationContext(), UpdateClientActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",client.getId());
        bundle.putString("email",client.getEmail());
        bundle.putString("name",client.getNome());
        bundle.putString("phoneNumber",client.getTelefone());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}