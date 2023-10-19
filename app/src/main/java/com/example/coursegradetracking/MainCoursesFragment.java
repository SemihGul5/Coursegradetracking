package com.example.coursegradetracking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coursegradetracking.databinding.FragmentLogInBinding;
import com.example.coursegradetracking.databinding.FragmentMainCoursesBinding;


public class MainCoursesFragment extends Fragment {
    private FragmentMainCoursesBinding binding;


    public MainCoursesFragment() {
        // Required empty public constructor
    }

    public static MainCoursesFragment newInstance(String param1, String param2) {
        MainCoursesFragment fragment = new MainCoursesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fab();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainCoursesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
    private void fab() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections directions=MainCoursesFragmentDirections.actionMainCoursesFragmentToCourseAddFragment();
                Navigation.findNavController(view).navigate(directions);
            }
        });
    }
}