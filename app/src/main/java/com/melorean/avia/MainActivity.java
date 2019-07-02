package com.melorean.avia;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.melorean.avia.fragments.DatePickerDialogFragment;

public class MainActivity extends AppCompatActivity {

    EditText ed;
    private TextView mTvAdult;
    private TextView mTvChild;
    private TextView mTvInfant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        mTvAdult = findViewById(R.id.main__tv_adultCount);
        mTvChild = findViewById(R.id.main__tv_childCount);
        mTvInfant = findViewById(R.id.main__tv_infantCount);
    }

    public void increment(View view) {
        switch (view.getId()) {
            case R.id.main__ib_adultAdd:
                mTvAdult.setText(String.valueOf(Integer.valueOf(mTvAdult.getText().toString()) + 1));
                break;
            case R.id.main__ib_childAdd:
                mTvChild.setText(String.valueOf(Integer.valueOf(mTvChild.getText().toString()) + 1));
                break;
            case R.id.main__ib_infantAdd:
                mTvInfant.setText(String.valueOf(Integer.valueOf(mTvInfant.getText().toString()) + 1));
                break;
        }
    }

    public void decrement(View view) {
        switch (view.getId()) {
            case R.id.main__ib_adultAdd:
                mTvAdult.setText(String.valueOf(Integer.valueOf(mTvAdult.getText().toString()) - 1));
                break;
            case R.id.main__ib_childAdd:
                mTvChild.setText(String.valueOf(Integer.valueOf(mTvChild.getText().toString()) - 1));
                break;
            case R.id.main__ib_infantAdd:
                mTvInfant.setText(String.valueOf(Integer.valueOf(mTvInfant.getText().toString()) - 1));
                break;
        }
    }


    public void showDatePickerFragment(View view) {
        DatePickerDialogFragment.TAG = view.getId();
        DialogFragment newFragment = new DatePickerDialogFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
