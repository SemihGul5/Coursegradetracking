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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.coursegradetracking.databinding.FragmentCourseAddBinding;
import com.example.coursegradetracking.databinding.FragmentLogInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseAddFragment extends Fragment {
    private FragmentCourseAddBinding binding;
    ArrayList<String> courseKredits;
    ArrayList<String> notes;
    FirebaseAuth auth;
    FirebaseFirestore firestore;


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

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

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

        adaptSpinner1();
        adaptSpinnerNotes();

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveClicked(view);
            }
        });

    }

    private void adaptSpinnerNotes() {
        notes=new ArrayList<>();
        notes.add("AA");notes.add("BA");notes.add("BB");notes.add("CB");notes.add("CC");notes.add("DC");notes.add("DD");notes.add("FF");
        ArrayAdapter<String> notesAdapter=new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,notes);
        binding.spinnerNotes.setAdapter(notesAdapter);
    }

    private void adaptSpinner1() {
        courseKredits=new ArrayList<>();
        for (int i=1;i<=10;i++){
            courseKredits.add(String.valueOf(i)+" Kredi");
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,courseKredits);
        binding.spinner1.setAdapter(adapter);
    }
    public void saveClicked(View view){

                String courseName=binding.courseName.getText().toString();
                String courseCredits=binding.spinner1.getSelectedItem().toString();
                String courseNote=binding.spinnerNotes.getSelectedItem().toString();
                if (courseName.equals("")||courseCredits.equals("")||courseNote.equals("")){
                    Snackbar.make(view,"Tüm alanları doldurun.",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    FirebaseUser user=auth.getCurrentUser();
                    String email=user.getEmail();
                    HashMap<String,Object> data=new HashMap<>();
                    data.put("Email",email);
                    data.put("CourseName",courseName);
                    data.put("Credits",courseCredits);
                    data.put("Note",courseNote);

                    firestore.collection("Data").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //kayıt başarılı!
                            Toast.makeText(getContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                            NavDirections directions=CourseAddFragmentDirections.actionCourseAddFragmentToMainCoursesFragment();
                            Navigation.findNavController(view).navigate(directions);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });


                }

    }
}