package com.shimaa.placeautocomplete;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{
    private static final String TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 23;
    private TextView mAutocompleteTextView,mAutocompleteTextView2;
    private GoogleApiClient mGoogleApiClient;
    private List<String> selectedList;
    private RecyclerView recyclerView;
    private PlacesRecyclerViewAdapter placesAdapter;
    private Boolean first;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAutocompleteTextView = findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView2= findViewById(R.id.autoCompleteTextView2);
        selectedList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerplaces);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        placesAdapter = new PlacesRecyclerViewAdapter(this, selectedList);
        recyclerView.setAdapter(placesAdapter);


        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        mAutocompleteTextView.setOnClickListener(v -> {
            try {
                first=true;
                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                        .setCountry("EG")
                        .build();

                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                                .build(MainActivity.this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }
        });
        mAutocompleteTextView2.setOnClickListener(v -> {
            try {
                first=false;
                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                        .setCountry("EG")
                        .build();

                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                                .build(MainActivity.this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if(first==true)
                     mAutocompleteTextView.setText(place.getAddress());
                if(first==false)
                    mAutocompleteTextView2.setText(place.getAddress());
                selectedList.add(place.getAddress().toString());
                for(int i =0 ;i<selectedList.size();i++){
                    Log.i("Tag",selectedList.get(i));
                }
                placesAdapter.notifyDataSetChanged();
                //   itemsAdapter.add(place.getAddress().toString());

                //place.getLatLng()
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.i(TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();

    }
}
