package com.melorean.avia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.melorean.avia.fragments.DatePickerDialogFragment;
import com.melorean.avia.model.Location;
import com.melorean.avia.utils.HttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private String error;

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

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient<Location> httpClient = new HttpClient<>();
                Log.d("DEBUGAsss", httpClient.connectGet("http://api.travelpayouts.com/data/ru/airports.json", new HashMap<String, String>(), Location.class).toString());
            }
        });
        t.start();


    }


    private void showError(String error) {
        this.error = error;
        new ErrorBarAsyncTask().execute();
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

    private Integer passengersCount() {
        return Integer.valueOf(mTvAdult.getText().toString()) + Integer.valueOf(mTvChild.getText().toString()) + Integer.valueOf(mTvInfant.getText().toString());
    }

    private class ErrorBarAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            mTvErrorText.setText(error);
            mLlErrorBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... strings) {
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            mLlErrorBar.setVisibility(View.GONE);
        }
    }
}
