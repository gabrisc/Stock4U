package com.example.stock4u.login.firstScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stock4u.databinding.FragmentMainBinding;
import com.example.stock4u.main.CrudsMenusActivity;
import com.example.stock4u.util.FireBaseConfig;

public class FirstScreen extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;

    public static FirstScreen newInstance() {
        return new FirstScreen();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        verifyLogin();
    }

    private void verifyLogin() {
        if (FireBaseConfig.firebaseAuth.getCurrentUser()!=null){
            openMenu();
        }
    }

    private void openMenu(){startActivity(new Intent(getContext(), CrudsMenusActivity.class));}

}