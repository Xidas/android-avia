package com.melorean.avia.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.melorean.avia.MainActivity;
import com.melorean.avia.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static Integer TAG;
    EditText ed;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        switch (TAG) {
            case R.id.main__settings_departure_date:
                ed = Objects.requireNonNull(getDialog().getOwnerActivity()).findViewById(R.id.main__settings_departure_date);
                calendar.set(year, month, dayOfMonth);
                ed.setText(new StringBuilder().append(calendar.get(Calendar.DAY_OF_MONTH)).append(".")
                        .append(calendar.get(Calendar.MONTH)).append(".")
                        .append(calendar.get(Calendar.YEAR)));
                break;
            case R.id.main__settings_departure_date_back:
                ed = Objects.requireNonNull(getDialog().getOwnerActivity()).findViewById(R.id.main__settings_departure_date_back);
                calendar.set(year, month, dayOfMonth);
                ed.setText(new StringBuilder().append(calendar.get(Calendar.DAY_OF_MONTH)).append(".")
                        .append(calendar.get(Calendar.MONTH)).append(".")
                        .append(calendar.get(Calendar.YEAR)));
                break;
        }
        Toast.makeText(getContext(), "User selected: год - " + year + " месяц - " + month + " день - " + dayOfMonth, Toast.LENGTH_LONG).show();
    }
}
