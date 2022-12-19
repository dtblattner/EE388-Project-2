package com.example.project2_2;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    /**
     * Sign in variable
     */
    private static boolean mIsSigningIn;

    /**
     * Call to set sign in to false
     */
    public MyViewModel() {
        mIsSigningIn = false;
    }

    /**
     * Gets value of sign in
     * @return
     */
    public static boolean getIsSigningIn() {
        return mIsSigningIn;
    }

    /**
     * Set sign in variable
     * @param mIsSigningIn
     */
    public static void setIsSigningIn(boolean mIsSigningIn) {
        MyViewModel.mIsSigningIn = mIsSigningIn;
    }

    /**
     * Keeps track of elapsed time
     */
    private MutableLiveData<Long> elapsedTime;

    /**
     * Function to begin the elapsedTime
     * @return elapsed time
     */
    public MutableLiveData<Long> getElapsedTime() {
        if (elapsedTime == null) {
            elapsedTime = new MutableLiveData<Long>();
        }
        return elapsedTime;
    }

    /**
     * Current index
     */
    public int IndexCurr;
    /**
     * Current time
     */
    public long nextTime;
}
