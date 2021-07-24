package com.example.stock4u.ecMeiHealth.ui.CalculoDeInvestimentoTotal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stock4u.databinding.TotalInvestmentCalculationFragmentBinding;

public class TotalInvestmentCalculationFragment extends Fragment {

    private TotalInvestmentCalculationFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        binding = TotalInvestmentCalculationFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}