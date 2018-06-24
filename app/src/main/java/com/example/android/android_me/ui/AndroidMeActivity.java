/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.android_me.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.android_me.R;
import com.example.android.android_me.data.BodyPartEnum;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class AndroidMeActivity extends AppCompatActivity implements OnImageClickListener {

    public static final String IMAGE_INDEX_LIST = "image_index";

    private int[] imageIndexes = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        if (savedInstanceState != null) {
            imageIndexes = savedInstanceState.getIntArray(IMAGE_INDEX_LIST);
        } else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                imageIndexes = new int[]{
                        bundle.getInt("headIndex"),
                        bundle.getInt("bodyIndex"),
                        bundle.getInt("legIndex")};
            }
        }

        AndroidMeFragment androidMeFragment = new AndroidMeFragment();
        androidMeFragment.setImagesIndex(imageIndexes);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.android_me_container, androidMeFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntArray(IMAGE_INDEX_LIST, imageIndexes);
    }

    @Override
    public void onImageSelected(BodyPartEnum bodyPartEnum, int position) {

        switch (bodyPartEnum) {
            case HEAD:
                imageIndexes[0] = position;
                break;
            case BODY:
                imageIndexes[1] = position;
                break;
            case LEG:
                imageIndexes[2] = position;
                break;
            default:
                break;
        }
    }

}