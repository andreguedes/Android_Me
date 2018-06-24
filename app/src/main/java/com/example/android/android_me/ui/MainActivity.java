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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.BodyPartEnum;

// This activity is responsible for displaying the master list of all images
// Implement the MasterListFragment callback, OnImageClickListener
public class MainActivity extends AppCompatActivity implements OnImageClickListener {

    // Variables to store the values for the list index of the selected images
    // The default value will be index = 0
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    private int[] imageIndexes = new int[3];

    // TODO (3) Create a variable to track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO (4) If you are making a two-pane display, add new BodyPartFragments to create an initial Android-Me image
        // Also, for the two-pane display, get rid of the "Next" button in the master list fragment
        mTwoPane = findViewById(R.id.android_me_layout) != null;

        if (mTwoPane) {
            // TODO Set all body part fragments
            imageIndexes = new int[]{
                    0, 0, 0
            };
            showBodyForTablet(imageIndexes);
        }
    }

    // Define the behavior for onImageSelected
    @Override
    public void onImageSelected(BodyPartEnum bodyPartEnum, int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        // TODO (5) Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        // The two-pane case will not need a Bundle or Intent since a new activity will not be started;
        // This is all happening in this MainActivity and one fragment will be replaced at a time


        // Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments

        // bodyPartNumber will be = 0 for the head fragment, 1 for the body, and 2 for the leg fragment
        // Dividing by 12 gives us these integer values because each list of images resources has a size of 12

        // Store the correct list index no matter where in the image list has been clicked
        // This ensures that the index will always be a value between 0-11

        // Set the currently displayed item for the correct body part fragment
        switch (bodyPartEnum) {
            case HEAD:
                headIndex = position;
                break;
            case BODY:
                bodyIndex = position;
                break;
            case LEG:
                legIndex = position;
                break;
            default:
                break;
        }

        if (!mTwoPane) {
            // Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
            Bundle b = new Bundle();
            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);

            // Attach the Bundle to an intent
            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(b);

            // The "Next" button launches a new AndroidMeActivity
            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        } else {
            imageIndexes[0] = headIndex;
            imageIndexes[1] = bodyIndex;
            imageIndexes[2] = legIndex;
            showBodyForTablet(imageIndexes);
        }

    }

    private void showBodyForTablet(int[] imageIndexes) {
        AndroidMeFragment androidMeFragment = new AndroidMeFragment();
        androidMeFragment.setImagesIndex(imageIndexes);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.android_me_container, androidMeFragment)
                .commit();
    }

}
