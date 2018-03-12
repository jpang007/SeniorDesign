package com.example.along002.testingfinal.ManageSet;


import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.CardGames.SpeedRoundActivity;
import com.example.along002.testingfinal.R;


/**
 * Created by along002 on 2/22/2018.
 */

public class CustomSettingDialog extends DialogFragment {//DialogSetting for speed round

    private ImageView cancelImageView;
    private TextView continueTextView;
    private RadioGroup radioGroup;
    private RadioButton termRadioButton;
    private EditText timerEditText;
    private CheckBox randomizeCheckbox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_custom_setting, container, false);

        timerEditText = view.findViewById(R.id.timerEditText);
        radioGroup = view.findViewById(R.id.radioGroup);
        continueTextView = view.findViewById(R.id.continueTextView);
        cancelImageView = view.findViewById(R.id.cancelImageView);
        randomizeCheckbox = view.findViewById(R.id.randomizeCheckbox);
        termRadioButton = view.findViewById(R.id.termRadioButton);

        termRadioButton.setChecked(true);

        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isRandomized = randomizeCheckbox.isChecked();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = view.findViewById(selectedId);
                String testChoice = radioButton.getText().toString();

                ManageSetActivity ManageSetActivity = (ManageSetActivity)getActivity();
                ManageSetActivity.goToSpeedRound(testChoice,Integer.valueOf(timerEditText.getText().toString()),isRandomized);
                ManageSetActivity.setScreenTransitionUp();
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
