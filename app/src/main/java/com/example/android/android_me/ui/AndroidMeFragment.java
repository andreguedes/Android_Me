package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;
import com.example.android.android_me.data.BodyPartEnum;

public class AndroidMeFragment extends Fragment {

    public static final String IMAGE_INDEX_LIST = "image_index";

    private int[] mImageIndex;

    public AndroidMeFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntArray(IMAGE_INDEX_LIST, mImageIndex);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mImageIndex = savedInstanceState.getIntArray(IMAGE_INDEX_LIST);
        }

        View rootView = inflater.inflate(R.layout.fragment_android_me, container, false);

        // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
        // Use setListindex(int index) to set the list index for all BodyPartFragments

        // Create a new head BodyPartFragment
        BodyPartFragment headFragment = new BodyPartFragment();

        // Set the list of image id's for the head fragment and set the position to the second image in the list
        headFragment.setImageIds(AndroidImageAssets.getHeads());

        // Get the correct index to access in the array of head images from the intent
        // Set the default value to 0
        headFragment.setListIndex(mImageIndex[0]);
        headFragment.setBodyPartEnum(BodyPartEnum.HEAD);

        // Add the fragment to its container using a FragmentManager and a Transaction
        FragmentManager fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.head_container, headFragment)
                .commit();

        // Create and display the body and leg BodyPartFragments

        BodyPartFragment bodyFragment = new BodyPartFragment();
        bodyFragment.setImageIds(AndroidImageAssets.getBodies());
        bodyFragment.setListIndex(mImageIndex[1]);
        bodyFragment.setBodyPartEnum(BodyPartEnum.BODY);

        fragmentManager.beginTransaction()
                .add(R.id.body_container, bodyFragment)
                .commit();

        BodyPartFragment legFragment = new BodyPartFragment();
        legFragment.setImageIds(AndroidImageAssets.getLegs());
        legFragment.setListIndex(mImageIndex[2]);
        legFragment.setBodyPartEnum(BodyPartEnum.LEG);

        fragmentManager.beginTransaction()
                .add(R.id.leg_container, legFragment)
                .commit();

        return rootView;
    }

    public void setImagesIndex(int[] imageIndex) {
        mImageIndex = imageIndex;
    }

}