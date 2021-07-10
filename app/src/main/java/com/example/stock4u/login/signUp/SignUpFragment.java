package com.example.stock4u.login.signUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stock4u.login.firstScreen.FirstScreen;
import com.example.stock4u.main.CrudsMenusActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.auth.AuthResult;
import com.example.stock4u.R;
import com.example.stock4u.entities.Business;
import com.example.stock4u.login.firstScreen.SignUpViewModel;
import com.example.stock4u.util.FireBaseConfig;

import static com.example.stock4u.util.FireBaseConfig.firebaseAuth;
import static com.example.stock4u.util.FireBaseConfig.firebaseDbReference;

public class SignUpFragment extends Fragment {

    private SignUpViewModel mViewModel;
    private EditText textEmail,textPassword,textFantasyName,textAdress,textCNPJ,phoneNumber,businessBranch;
    private Button buttonCadastrar;
    private Context context = getContext();

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);

        textEmail= view.findViewById(R.id.editEmailCad);
        textPassword= view.findViewById(R.id.editPasswordCad);
        textFantasyName = view.findViewById(R.id.editFantasyName);
        textAdress= view.findViewById(R.id.editTextAdress);
        textCNPJ= view.findViewById(R.id.editTextCNPJ);
        phoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        businessBranch = view.findViewById(R.id.editTextBusinessBranch);
        buttonCadastrar = view.findViewById(R.id.button2);

        verefyFields();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
    }

    public void verefyFields(){
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (businessBranch.getText() == null){
                    Toast toast=Toast. makeText(context,"O ramo nao pode estar vazio",Toast. LENGTH_LONG);
                    toast. show();
                }
                if (phoneNumber.getText() == null){
                    Toast toast=Toast. makeText(context,"O telefone está vazio",Toast. LENGTH_LONG);
                    toast. show();
                }
                if (textEmail.getText() == null){
                    Toast toast=Toast. makeText(context,"O E-mail está vazio",Toast. LENGTH_LONG);
                    toast. show();
                }
                if (textFantasyName.getText() == null){
                    Toast toast=Toast. makeText(context,"O nome fantasia está vazio",Toast. LENGTH_LONG);
                    toast. show();
                }
                if (textCNPJ.getText() == null){
                    Toast toast=Toast. makeText(context,"O CNPJ ou CPF nao pode ficar vazio",Toast. LENGTH_LONG);
                    toast. show();
                }
                if (textPassword.getText() == null) {
                    Toast toast=Toast. makeText(context,"A senha está vazia",Toast. LENGTH_LONG);
                    toast. show();
                }else {
                    Business business = new Business(firebaseDbReference.push().getKey(),
                            textEmail.getText().toString(),
                            textPassword.getText().toString(),
                            textFantasyName.getText().toString().toUpperCase(),
                            textCNPJ.getText().toString(),textAdress.getText().toString(),
                            phoneNumber.getText().toString(),businessBranch.getText().toString());
                    SingUpUser(business);
                }

            }
        });
    }

    private void SingUpUser(Business business) {
        firebaseAuth.createUserWithEmailAndPassword(business.getEmail(), business.getPassword()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    business.setuId(firebaseAuth.getUid());
                    business.save();
                    startActivity(new Intent(getContext(), CrudsMenusActivity.class));
                }else{
                    try {
                        throw task.getException();
                    }catch (Exception e){
                        Toast toast=Toast. makeText(getContext(),e.getMessage(),Toast. LENGTH_LONG);
                        toast. show();
                    }
                }
            }
        });

    }



}