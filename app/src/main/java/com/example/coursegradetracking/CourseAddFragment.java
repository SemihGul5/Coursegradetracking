package com.example.coursegradetracking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.coursegradetracking.databinding.FragmentCourseAddBinding;
import com.example.coursegradetracking.databinding.FragmentLogInBinding;

import java.util.ArrayList;

public class CourseAddFragment extends Fragment {
    private FragmentCourseAddBinding binding;
    ArrayList<String> courseKredits;


    public CourseAddFragment() {
        // Required empty public constructor
    }


    public static CourseAddFragment newInstance(String param1, String param2) {
        CourseAddFragment fragment = new CourseAddFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCourseAddBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseKredits=new ArrayList<>();
        for (int i=1;i<=10;i++){
            courseKredits.add(String.valueOf(i)+" Kredi");
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,courseKredits);
        binding.spinner1.setAdapter(adapter);
    }
}