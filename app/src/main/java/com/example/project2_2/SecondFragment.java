package com.example.project2_2;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project2_2.databinding.FragmentSecondBinding;
import com.example.project2_2.model.Workout;
import com.example.project2_2.util.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FirebaseFirestore mFirestore;
    private MyViewModel myViewModel;
    Random rand;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences pref = this.getActivity().getSharedPreferences("Index", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        Workout newWork = new Workout(5, 5, 4, 3, pref.getInt("Index", 1000));
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        myViewModel.getElapsedTime().setValue(0L);
        myViewModel.nextTime = 0;

        // Timing
        final Observer<Long> longObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long newElapsedTime) {
                long hour = newElapsedTime / 36000;
                long minute = (newElapsedTime / 600) - (hour * 60);
                long second = (newElapsedTime / 10) - (minute * 60);
                String text = getString(R.string.textview_timer, hour, minute, second, newElapsedTime % 10);
                binding.textviewTimer.setText(text);
            }
        };

        Handler handler = new Handler(Looper.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                myViewModel.nextTime += 1;
                handler.postAtTime(this, SystemClock.uptimeMillis() + 100);
                myViewModel.getElapsedTime().setValue(myViewModel.nextTime);
            }
        };

        myViewModel.getElapsedTime().observe(getViewLifecycleOwner(), longObserver);


        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Initialize Firestore
        mFirestore = FirebaseUtil.getFirestore();


        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.postAtTime(myRunnable, SystemClock.uptimeMillis() + 100);
            }
        });



        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.buttonEndWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionReference workouts = mFirestore.collection("workouts");

                newWork.setDate((int) myViewModel.nextTime * 5);
                newWork.setDistance((int) myViewModel.nextTime / 100);
                newWork.setTime((int) myViewModel.nextTime);
                workouts.add(newWork);

                myViewModel.IndexCurr = pref.getInt("Index", 1000);
                editor.putInt("Index", myViewModel.IndexCurr + 1);
                editor.apply();

                handler.removeCallbacks(myRunnable);
                myViewModel.nextTime = 0;
                myViewModel.getElapsedTime().setValue(myViewModel.nextTime);

                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}