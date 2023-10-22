package com.example.coursegradetracking;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.coursegradetracking.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInFragment extends Fragment {
    private FragmentSignInBinding binding;
    private FirebaseAuth auth;
    String name;
    String email;
    String password;


    public SignInFragment() {
        // Required empty public constructor
    }


    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goToLogIn(view);
        userSignIn(view);
    }

    private void userSignIn(View view) {
        Button button=binding.kayitolkayitOlButton;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=binding.signInUserNameText.getText().toString();
                email=binding.singInUserEmailText.getText().toString();
                password=binding.singInPasswordText.getText().toString();
                if (name.equals("")||email.equals("")||password.equals("")){
                    Toast.makeText(getContext(),"Tüm alanları doldurun.",Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //kullanıcı oluşturuldu
                            //email ile doğrulama yapılacak, doğrulanmazsa ana ekrandan da giriş yapılamayacak ve doğrulama ekranına gidilecek
                            FirebaseUser user=auth.getCurrentUser();
                            if (user!=null){
                                sendEmail(user,view);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void sendEmail(FirebaseUser user,View view){
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //doğrulama işlemleri için diğer fragmenta gidiliyor
                NavDirections directions= SignInFragmentDirections.actionSignInFragmentToLogInFragment();
                Navigation.findNavController(view).navigate(directions);
                Toast.makeText(getContext(),"Email gönderildi, hesabınızı doğrulayın.",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToLogIn(View view) {

        Button button=view.findViewById(R.id.kayitolGirisYapButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NavDirections directions=SignInFragmentDirections.actionSignInFragmentToLogInFragment();
                Navigation.findNavController(v).navigate(directions);


            }
        });

    }
}