package com.example.project2_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project2_2.databinding.FragmentFirstBinding;
import com.example.project2_2.model.Workout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private FirebaseFirestore mFirestore;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Workout workout = new Workout();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyViewModel myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        SharedPreferences pref = this.getActivity().getSharedPreferences("Index", Context.MODE_PRIVATE);
        myViewModel.IndexCurr = 0;
        //myViewModel.IndexCurr = pref.getInt("Index", 1000);

        CollectionReference ref = db.collection("workouts");
        ref.whereEqualTo("index", myViewModel.IndexCurr).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                workout = queryDocumentSnapshots.getDocuments().get(0).toObject(Workout.class);
                setViews();
            }
        });


        binding.buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myViewModel.IndexCurr = myViewModel.IndexCurr - 1;
                CollectionReference ref = db.collection("workouts");
                ref.whereEqualTo("index", myViewModel.IndexCurr).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        workout = queryDocumentSnapshots.getDocuments().get(0).toObject(Workout.class);
                        setViews();
                    }
                });
            }
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  myViewModel.IndexCurr = myViewModel.IndexCurr + 1;
                  CollectionReference ref = db.collection("workouts");
                  ref.whereEqualTo("index", myViewModel.IndexCurr).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                      @Override
                      public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                          workout = queryDocumentSnapshots.getDocuments().get(0).toObject(Workout.class);
                          setViews();
                      }
                  });
              }
          });

        binding.buttonWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    public void setViews() {
        String date = getString(R.string.textView_date, workout.getDate());
        binding.textViewDate.setText(date);

        String distance = getString(R.string.textView_distance, workout.getDistance());
        binding.textViewDistance.setText(distance);

        String rating = getString(R.string.textView_rating, workout.getRating());
        binding.textViewRating.setText(rating);

        String time = getString(R.string.textView_time, workout.getTime());
        binding.textViewTime.setText(time);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}