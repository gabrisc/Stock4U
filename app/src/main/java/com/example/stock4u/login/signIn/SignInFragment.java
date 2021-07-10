package com.example.stock4u.login.signIn;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stock4u.R;
import com.example.stock4u.main.CrudsMenusActivity;
import com.example.stock4u.util.FireBaseConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.Executor;

public class SignInFragment extends Fragment {

    private SignInViewModel mViewModel;
    private EditText textEmail,textPassword;
    private Context context = getContext();

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_fragment, container, false);
        textEmail=view.findViewById(R.id.editEmailSigIn);
        textPassword=view.findViewById(R.id.editPasswordSigIn);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
    }

    public void login(View view){
        if (textEmail.getText() == null){
            Toast toast=Toast. makeText(context,"O E-mail está vazio",Toast. LENGTH_LONG);
            toast. show();
        }else if (textPassword.getText() == null) {
            Toast toast=Toast. makeText(context,"A senha está vazia", Toast. LENGTH_LONG);
            toast. show();
        }else {
            SingUpUser(textEmail.getText().toString(),textPassword.getText().toString());
        }
    }

    private void SingUpUser(String email, String password) {
        FireBaseConfig.firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if (task.isSuccessful()){
                        startActivity(new Intent( context, CrudsMenusActivity.class));
                    }
                }catch (Exception e){
                    Toast toast=Toast. makeText(context,e.getMessage(),Toast. LENGTH_LONG);
                    toast. show();
                }
            }
        });
    }

}