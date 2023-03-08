package com.example.myapplication.model;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CourseAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentCourse extends Fragment {
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private DatabaseReference databaseRef;
    List<CourseModel> courses = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");

        recyclerView = view.findViewById(R.id.listCourse);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        databaseRef = FirebaseDatabase.getInstance().getReference("Courses");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courses.clear();

                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    CourseModel course = courseSnapshot.getValue(CourseModel.class);
                    courses.add(course);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        adapter = new CourseAdapter(requireContext(), courses);
        recyclerView.setAdapter(adapter);

//        User u = new User();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child("-NP_eUZamDv2vX8DH3a4");
//
//        TextView t = view.findViewById(R.id.text);
//
//        t.setText(u.getFullName());
    }

    @Override
    public void onStart() {
        super.onStart();

        // Add a listener for the Firebase database reference
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courses.clear();

                for (DataSnapshot courseSnapshot : dataSnapshot.getChildren()) {
                    CourseModel course = courseSnapshot.getValue(CourseModel.class);
                    courses.add(course);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
