package com.example.coursegradetracking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursegradetracking.Model.Course;
import com.example.coursegradetracking.databinding.RecyclerListBinding;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
     ArrayList<Course> courseArrayList;
     Context context;

    public CourseAdapter(ArrayList<Course> courseArrayList, Context context) {
        this.courseArrayList = courseArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerListBinding binding=RecyclerListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CourseHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course course=courseArrayList.get(position);
        String name=course.getCourseName();
        holder.binding.recyclerCourseNameText.setText(name);
        String cCredits=course.getCourseCredits();
        String notes=course.getCourseNotes();
        String x=cCredits+" - Ders Notu: "+notes;
        holder.binding.recyclerCourseDetailsText.setText(x);

    }

    @Override
    public int getItemCount() {
        return courseArrayList.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder{
        private RecyclerListBinding binding;

        public CourseHolder(RecyclerListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
