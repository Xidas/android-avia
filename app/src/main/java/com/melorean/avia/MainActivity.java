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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    public static final int DEPARTURE_REQUEST_CODE = 9910;
    public static final int ARRIVAL_REQUEST_CODE = 9920;
    public static final String REQUEST_CODE_STRING = "requestCode";


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
        Calendar calendar = Calendar.getInstance();
        mEtDepartureDate.setText(new StringBuilder().append(calendar.get(Calendar.DAY_OF_MONTH)).append(".").append(calendar.get(Calendar.MONTH)).append(".").append(calendar.get(Calendar.YEAR)));
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
        switch (view.getId()) {
            case R.id.main__settings_departure:
                startLocationPickerForFrom();
                break;
            case R.id.main__settings_arrival:
                startLocationPickerForWhere();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case DEPARTURE_REQUEST_CODE:
                    mEtDeparture.setText(data.getStringExtra("location"));
                    if (mEtArrival.getText().toString().equals("") || mEtArrival.getText() == null) {
                        startLocationPickerForWhere();
                    }
                    break;
                case ARRIVAL_REQUEST_CODE:
                    mEtArrival.setText(data.getStringExtra("location"));
                    break;
            }
        }
    }

    private void startLocationPickerForFrom() {
        Intent locPickerIntent = new Intent(this, LocationListActivity.class);
        locPickerIntent.putExtra(REQUEST_CODE_STRING, DEPARTURE_REQUEST_CODE);
        startActivityForResult(locPickerIntent, DEPARTURE_REQUEST_CODE);
    }

    private void startLocationPickerForWhere() {
        Intent locPickerIntent = new Intent(this, LocationListActivity.class);
        locPickerIntent.putExtra(REQUEST_CODE_STRING, ARRIVAL_REQUEST_CODE);
        startActivityForResult(locPickerIntent, ARRIVAL_REQUEST_CODE);
    }

    private Integer passengersCount() {
        return Integer.valueOf(mTvAdult.getText().toString()) + Integer.valueOf(mTvChild.getText().toString()) + Integer.valueOf(mTvInfant.getText().toString());
    }
}
