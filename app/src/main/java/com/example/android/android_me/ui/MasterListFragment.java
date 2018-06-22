package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.ArrayList;
import java.util.List;

public class MasterListFragment extends Fragment {

    public static final String IMAGE_ID_LIST = "image_ids";

    private List<Integer> mImageIds = AndroidImageAssets.getAll();

    public MasterListFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
        }

        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.master_list_grid_view);

        MasterListAdapter adapter = new MasterListAdapter(getActivity(), mImageIds);
        gridView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
    }
}