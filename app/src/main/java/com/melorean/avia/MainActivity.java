package com.melorean.avia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melorean.avia.fragments.DatePickerDialogFragment;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    private TextView mTvAdult;
    private TextView mTvChild;
    private TextView mTvInfant;
    private LinearLayout mLlErrorBar;
    private TextView mTvErrorText;
    private EditText mEtDeparture;
    private EditText mEtArrival;
    private EditText mEtDepartureDate;
    private EditText getmEtDepartureDateBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.main_layout);

        mTvAdult = findViewById(R.id.main__tv_adultCount);
        mTvChild = findViewById(R.id.main__tv_childCount);
        mTvInfant = findViewById(R.id.main__tv_infantCount);

        mLlErrorBar = findViewById(R.id.main__ll_error_bar);
        mTvErrorText = findViewById(R.id.main__tv_error_text);

        mEtDeparture = findViewById(R.id.main__settings_departure);
        mEtArrival = findViewById(R.id.main__settings_arrival);

        mEtDepartureDate = findViewById(R.id.main__settings_departure_date);
        getmEtDepartureDateBack = findViewById(R.id.main__settings_departure_date_back);


    }


    private void showError(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvErrorText.setText(error);
                mLlErrorBar.setVisibility(View.VISIBLE);
                Thread wait = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mLlErrorBar.setVisibility(View.GONE);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                wait.start();
            }
        });
    }

    public void increment(View view) {
        switch (view.getId()) {
            case R.id.main__ib_adultAdd:
                if (passengersCount() < 9) {
                    mTvAdult.setText(String.valueOf(Integer.valueOf(mTvAdult.getText().toString()) + 1));
                } else {
                    showError("Maximum 9 passengers may to be");
                }
                break;
            case R.id.main__ib_childAdd:
                if (passengersCount() < 9) {
                    mTvChild.setText(String.valueOf(Integer.valueOf(mTvChild.getText().toString()) + 1));
                } else {
                    showError("Maximum 9 passengers may to be");
                }
                break;
            case R.id.main__ib_infantAdd:
                if (passengersCount() < 9) {
                    if (!mTvInfant.getText().toString().equals(mTvAdult.getText().toString())) {
                        mTvInfant.setText(String.valueOf(Integer.valueOf(mTvInfant.getText().toString()) + 1));
                    } else {
                        showError("Infants can't be more then adult");
                    }
                } else {
                    showError("Maximum 9 passengers may to be");
                }
                break;
        }
    }

    public void decrement(View view) {
        switch (view.getId()) {
            case R.id.main__ib_adultRemove:
                if (mTvAdult.getText().toString().equals("1")) {
                    showError("At least 1 adult must be");
                } else {
                    mTvAdult.setText(String.valueOf(Integer.valueOf(mTvAdult.getText().toString()) - 1));
                }
                break;
            case R.id.main__ib_childRemove:
                if (!mTvChild.getText().toString().equals("0")) {
                    mTvChild.setText(String.valueOf(Integer.valueOf(mTvChild.getText().toString()) - 1));
                }
                break;
            case R.id.main__ib_infantRemove:
                if (!mTvInfant.getText().toString().equals("0")) {
                    mTvInfant.setText(String.valueOf(Integer.valueOf(mTvInfant.getText().toString()) - 1));
                }
                break;
        }
    }


    public void showDatePickerFragment(View view) {
        DatePickerDialogFragment.TAG = view.getId();
        DialogFragment newFragment = new DatePickerDialogFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showLocPickerActivity(View view) {
        Intent locPickerIntent = new Intent(this, LocationListActivity.class);
        int requestCode = 1;
        switch (view.getId()) {
            case R.id.main__settings_departure:
                requestCode = 9910;
                break;
            case R.id.main__settings_arrival:
                requestCode = 9920;

        }
        startActivityForResult(locPickerIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 9910:
                    mEtDeparture.setText(data.getStringExtra("location"));
                    break;
                case 9920:
                    mEtArrival.setText(data.getStringExtra("location"));
                    break;
            }
        }
    }

    private Integer passengersCount() {
        return Integer.valueOf(mTvAdult.getText().toString()) + Integer.valueOf(mTvChild.getText().toString()) + Integer.valueOf(mTvInfant.getText().toString());
    }
}
