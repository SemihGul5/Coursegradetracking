package com.example.coursegradetracking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursegradetracking.databinding.FragmentSignInBinding;
import com.example.coursegradetracking.databinding.FragmentVerificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class VerificationFragment extends Fragment {
    private FragmentVerificationBinding binding;
    private String name;
    private String email;
    private String password;
    FirebaseUser user;
    FirebaseAuth auth;
    public VerificationFragment() {
        // Required empty public constructor
    }


    public static VerificationFragment newInstance() {
        VerificationFragment fragment = new VerificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVerificationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle=getArguments();
        if (bundle!=null){
            name=bundle.getString("name");
            email=bundle.getString("email");
            password=bundle.getString("password");
        }

        verificationButtonClicked(view);


    }

    private void verificationButtonClicked(View view) {
        binding.verificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}