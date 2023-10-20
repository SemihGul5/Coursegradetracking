package com.example.coursegradetracking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coursegradetracking.databinding.FragmentLogInBinding;
import com.example.coursegradetracking.databinding.FragmentMainCoursesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;


public class MainCoursesFragment extends Fragment {
    private FragmentMainCoursesBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;


    public MainCoursesFragment() {
        // Required empty public constructor
    }

    public static MainCoursesFragment newInstance(String param1, String param2) {
        MainCoursesFragment fragment = new MainCoursesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainCoursesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabClicked(view);

        getData();
    }

    private void getData() {

        firestore.collection("Data").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null){
                    Toast.makeText(getContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                if (value!=null){
                    for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                        Map<String,Object> data= documentSnapshot.getData();
                        String eMail= (String) data.get("Email");
                        String courseName= (String) data.get("CourseName");
                        String credits= (String) data.get("Credits");
                        String note= (String) data.get("Note");
                    }
                }
            }
        });
    }

    public void fabClicked(View view) {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections directions=MainCoursesFragmentDirections.actionMainCoursesFragmentToCourseAddFragment();
                Navigation.findNavController(view).navigate(directions);
            }
        });

    }
}