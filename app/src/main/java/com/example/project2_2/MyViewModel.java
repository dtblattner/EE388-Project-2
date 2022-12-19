package com.example.project2_2;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private static boolean mIsSigningIn;

    public MyViewModel() {
        mIsSigningIn = false;
    }

    public static boolean getIsSigningIn() {
        return mIsSigningIn;
    }

    public static void setIsSigningIn(boolean mIsSigningIn) {
        MyViewModel.mIsSigningIn = mIsSigningIn;
    }

    public int IndexCurr;
}
