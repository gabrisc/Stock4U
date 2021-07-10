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
import androidx.recyclerview.widget.RecyclerView;
import com.example.stock4u.R;
import com.example.stock4u.adapters.AdapterClient;
import com.example.stock4u.addScreens.AddClientsActivity;
import com.example.stock4u.databinding.FragmentClientBinding;
import com.example.stock4u.entities.Client;
import com.example.stock4u.login.firstScreen.PageViewModel;
import com.example.stock4u.updateScreens.UpdateClientActivity;
import java.util.ArrayList;
import java.util.List;

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