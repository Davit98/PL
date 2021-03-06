package com.davitmartirosyan.pl.ui.fragment;

import android.support.v4.app.Fragment;

import com.davitmartirosyan.pl.ui.activity.BaseActivity;


public abstract class BaseFragment extends Fragment {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================

    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    protected void hideActionBarIcon() {
        ((BaseActivity) getActivity()).hideActionBarIcon();
    }

    protected void showActionBarIcon() {
        ((BaseActivity) getActivity()).showActionBarIcon();
    }

    protected void setActionBarIcon() {
        ((BaseActivity) getActivity()).hideActionBarIcon();
    }

    protected void setActionBarTitle(String actionBarTitle) {
        ((BaseActivity) getActivity()).setActionBarTitle(actionBarTitle);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
