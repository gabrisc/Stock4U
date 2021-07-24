package com.example.stock4u.ecMeiHealth.ui.RentabilidadeLucratividade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.stock4u.databinding.ProfitabilityFragmentBinding;

public class ProfitabilityFragment extends Fragment {

    private ProfitabilityFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = ProfitabilityFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}