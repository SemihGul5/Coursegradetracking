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

import com.example.coursegradetracking.databinding.FragmentLogInBinding;
import com.example.coursegradetracking.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class LogInFragment extends Fragment {
    private FragmentLogInBinding binding;
    private FirebaseAuth auth;
    String email;
    String password;

    public LogInFragment() {
        // Required empty public constructor
    }

    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();

        FirebaseUser user=auth.getCurrentUser();
        if (user!=null&&user.isEmailVerified()){
            Intent intent= new Intent(getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goToSignIn(view);
        userLogin(view);
        forgotPasswordClicked(view);
    }
    private void userLogin(View view) {
        binding.girisYapButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                 email=binding.userEmailText.getText().toString();
                 password=binding.userPasswordText.getText().toString();
                if (email.equals("")||password.equals("")){
                    Toast.makeText(getContext(),"Email ve şifreyi girin.",Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (auth.getCurrentUser().isEmailVerified()){
                                Intent intent= new Intent(getContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getContext(),"E-mail adresinizi doğrulayın.",Toast.LENGTH_SHORT).show();
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

    private void goToSignIn(View view) {
        Button button=view.findViewById(R.id.kayitOlButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections directions=LogInFragmentDirections.actionLogInFragmentToSignInFragment();
                Navigation.findNavController(v).navigate(directions);
            }
        });
    }
    private void forgotPasswordClicked(View view){
        binding.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=binding.userEmailText.getText().toString();
                if (email.equals("")){
                    Toast.makeText(getContext(),"Email alanı dolu olmalıdır!",Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(),"Şifre sıfırlama e-mail'i gönderildi",Toast.LENGTH_SHORT).show();

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
}