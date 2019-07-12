package com.melorean.avia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.melorean.avia.adapters.LocationItemAdapter;
import com.melorean.avia.model.LocationItem;
import com.melorean.avia.service.NetworkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationListActivity extends AppCompatActivity {
    private RecyclerView rv;
    private EditText etLoc;
    private LocationItemAdapter locationItemAdapter;
    private List<LocationItem> locationItems = new ArrayList<>();
    private List<LocationItem> filteredLocationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        etLoc = findViewById(R.id.location_list__et);
        etLoc.setHint(getEditTextHint());
        rv = findViewById(R.id.location_list__rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.HORIZONTAL));
        setTextWatcher();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocations();
        getFilteredLocationList("startInit");
        locationItemAdapter = new LocationItemAdapter(filteredLocationList);
        rv.setAdapter(locationItemAdapter);
    }

    public int getEditTextHint() {
        int requestCode = (int) getIntent().getExtras().get(MainActivity.REQUEST_CODE_STRING);
        if (requestCode == MainActivity.DEPARTURE_REQUEST_CODE) {
            return R.string.from;
        } else if (requestCode == MainActivity.ARRIVAL_REQUEST_CODE) {
            return R.string.where;
        } else return R.string.enter_location_name;
    }

    private void setTextWatcher() {
        etLoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getFilteredLocationList(etLoc.getText().toString());
                locationItemAdapter.notifyDataSetChanged();
                Toast.makeText(LocationListActivity.this, "Data changed: " + etLoc.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getLocations() {
        new NetworkService("http://api.travelpayouts.com/").getLocationAPIEndpoint().getAirportItems().enqueue(new Callback<List<LocationItem>>() {
            @Override
            public void onResponse(Call<List<LocationItem>> call, Response<List<LocationItem>> response) {
                Log.d("DEBUGA", "Получен лист: " + (response.body() != null ? response.body().size() : -1));
                if (response.body() != null) {
                    locationItems.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<LocationItem>> call, Throwable t) {
                Log.d("DEBUGA", "Ошибочка, братан", t);
            }
        });
        new NetworkService("http://api.travelpayouts.com/").getLocationAPIEndpoint().getCitiesItems().enqueue(new Callback<List<LocationItem>>() {
            @Override
            public void onResponse(Call<List<LocationItem>> call, Response<List<LocationItem>> response) {
                if (response.body() != null) {
                    locationItems.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<LocationItem>> call, Throwable t) {
                Log.d("DEBUGA", "Ошибочка, братан", t);
            }
        });
    }

    public void onItemClick(View view) {
        int i = rv.getChildLayoutPosition(view);
        LocationItem locationItem = filteredLocationList.get(i);
        Intent intent = new Intent();
        intent.putExtra("location", locationItem.getLocName() + ", " + locationItem.getLocCode());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelBtnClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }


    private List<LocationItem> getFilteredLocationList(String loc) {
        if (filteredLocationList == null) {
            filteredLocationList = new ArrayList<>();
        } else filteredLocationList.clear();
        filterList(locationItems, loc);
        return filteredLocationList;
    }

    private void filterList(List<LocationItem> locationItems, @NonNull String query) {
        for (LocationItem locationItem : locationItems) {
            if (locationItem.getLocName() == null) {
                locationItem.setLocName(locationItem.getNameTranslation().getEn());
            }
            if ((locationItem.getLocName().toUpperCase().startsWith(query.toUpperCase())) && filteredLocationList.size() < 5) {
                filteredLocationList.add(locationItem);
            }
        }
    }
}

