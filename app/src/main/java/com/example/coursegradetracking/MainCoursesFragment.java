package com.example.coursegradetracking;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coursegradetracking.Adapter.CourseAdapter;
import com.example.coursegradetracking.Interface.OnCourseDeletedListener;
import com.example.coursegradetracking.Model.Course;
import com.example.coursegradetracking.databinding.FragmentLogInBinding;
import com.example.coursegradetracking.databinding.FragmentMainCoursesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;


public class MainCoursesFragment extends Fragment implements OnCourseDeletedListener {
    private FragmentMainCoursesBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ArrayList<Course> courseArrayList;
    CourseAdapter adapter;
    double x=0,y=0;


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
        courseArrayList=new ArrayList<>();



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
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new CourseAdapter(courseArrayList, getContext());
        getData();
        binding.recyclerView.setAdapter(adapter);


    }

    Double calculateNote(String credits,String note) {
        double cr=translateCredi(credits);
        double nt= translateNote(note);
        return cr*nt;

    }
    Double translateCredi(String credi){
        double nt=0.0;
        switch (credi) {
            case "1 Kredi":
                nt = 1.0;
                break;
            case "2 Kredi":
                nt = 2.0;
                break;
            case "3 Kredi":
                nt = 3.0;
                break;
            case "4 Kredi":
                nt = 4.0;
                break;
            case "5 Kredi":
                nt = 5.0;
                break;
            case "6 Kredi":
                nt =6.0;
                break;
            case "7 Kredi":
                nt = 7.0;
                break;
            case "8 Kredi":
                nt = 8.0;
                break;
            case "9 Kredi":
                nt = 9.0;
                break;
            case "10 Kredi":
                nt = 10.0;
                break;
        }
        return nt;
    }

    Double translateNote(String note) {
        double nt=0.0;
        switch (note) {
            case "AA":
                nt = 4.0;
                break;
            case "BA":
                nt = 3.5;
                break;
            case "BB":
                nt = 3.0;
                break;
            case "CB":
                nt = 2.5;
                break;
            case "CC":
                nt = 2.0;
                break;
            case "DC":
                nt = 1.5;
                break;
            case "DD":
                nt = 1.0;
                break;
            case "FF":
                nt = 0.0;
                break;
        }
        return nt;
    }


    public void getData() {
        courseArrayList.clear();
        x=0;y=0;
        double res=0;
        firestore.collection("Data2")
                .whereEqualTo("Email",auth.getCurrentUser().getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
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

                        //diziye kaydedilen veriyi işliyoruz. recyclerda göstermek için
                        x+=calculateNote(credits,note);
                        y+=translateCredi(credits);
                        String docid=documentSnapshot.getId();

                        Course course= new Course(eMail,courseName,credits,note,docid);
                        courseArrayList.add(course);
                    }
                    double res=x/y;
                    String formattedRes = String.format("GENEL ORTALAMA: %.2f", res); // .2f ile 2 ondalık basamak kullanılır
                    binding.textView5.setText(formattedRes);
                    adapter.notifyDataSetChanged();
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

    @Override
    public void onCourseDeleted() {
        getData();
    }
}