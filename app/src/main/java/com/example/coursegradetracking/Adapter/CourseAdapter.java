package com.example.coursegradetracking.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursegradetracking.Interface.OnCourseDeletedListener;
import com.example.coursegradetracking.MainCoursesFragment;
import com.example.coursegradetracking.Model.Course;
import com.example.coursegradetracking.databinding.RecyclerListBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
     ArrayList<Course> courseArrayList;
     Context context;
     private OnCourseDeletedListener listener;

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

        recyclerClicked(holder);


    }
    public void setOnCourseDeleteListener(OnCourseDeletedListener listener){
        this.listener=listener;
    }

    private void recyclerClicked(CourseHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Sil");
                alert.setMessage("Emin misin ?");
                alert.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int pos=holder.getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            Course course=courseArrayList.get(pos);
                            String docID=course.getDocID();
                            deleteDocFromFirestore(docID);
                        }
                    }
                });
                alert.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void deleteDocFromFirestore(String docID){
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection("Data2").document(docID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Ders başarıyla silindi.", Toast.LENGTH_SHORT).show();
                if (listener!=null){
                    listener.onCourseDeleted();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        courseArrayList.clear();

        notifyDataSetChanged();
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
