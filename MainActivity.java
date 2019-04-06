package com.danmitch.android.fragmentcommunicationwithactivity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    private FrameLayout fragmentContainer;
    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
    }

    private void initWidgets() {
        fragmentContainer = findViewById(R.id.fragment_container);
        mEditText = findViewById(R.id.edit_text_one);
        mButton = findViewById(R.id.button_send_one);
    }

    private void initListeners() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();
                openFragment(text);
            }
        });
    }

    public void openFragment(String text) {
        BlankFragment fragment = BlankFragment.newInstance(text);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // added 3th and 4th times so anims work for back button
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);

        // added so that clicking back button while fragment is open only closes back button and not parent activity
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.add(R.id.fragment_container, fragment, "BLANK_FRAGMENT").commit();
    }

    /**
     * Required interface implementation which allows the fragment to communicate with the parent activity
     *
     * @param sendBackText
     */
    @Override
    public void onFragmentInteraction(String sendBackText) {
        mEditText.setText(sendBackText);

        // automatically closes our fragment
        onBackPressed();
    }
}
