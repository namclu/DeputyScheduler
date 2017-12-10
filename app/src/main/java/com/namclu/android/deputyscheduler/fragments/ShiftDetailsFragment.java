package com.namclu.android.deputyscheduler.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.namclu.android.deputyscheduler.BuildConfig;
import com.namclu.android.deputyscheduler.DeviceLocationService;
import com.namclu.android.deputyscheduler.R;
import com.namclu.android.deputyscheduler.models.Shift;
import com.namclu.android.deputyscheduler.models.ShiftPostBody;
import com.namclu.android.deputyscheduler.rest.ApiClient;
import com.namclu.android.deputyscheduler.rest.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by namlu on 7/31/2017.
 *
 * Displays the details of a Shift and allows user to submit an end time if the
 * shift is in progress.
 */

public class ShiftDetailsFragment extends Fragment implements
        TimePickerDialog.OnTimeSetListener,
        OnMapReadyCallback {

    private static final String TAG = ShiftDetailsFragment.class.getSimpleName();
    private static final String DEPUTY_USER_SHA = "Deputy " + BuildConfig.USER_SHA;
    private static final String DIALOG_TIME = "DialogTime";
    private static final String SHIFT = "Shift";
    // Map zoom levels: 1.0f = World view, 20.0f = Buildings view
    private static final float MAP_ZOOM_CITY_LEVEL = 9.0f;

    private TextView mTextEndTimePicker;
    private Calendar mCalendar;
    private GoogleMap mMap;
    private Location mDeviceLocation;
    private Shift mShift;

    public static ShiftDetailsFragment newInstance(Shift shift) {
        ShiftDetailsFragment fragment = new ShiftDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(SHIFT, shift);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DeviceLocationService) {
            DeviceLocationService deviceLocationService = (DeviceLocationService) context;
            mDeviceLocation = deviceLocationService.getDeviceLocation();
        } else {
            throw new ClassCastException(
                    "Activity must implement DeviceLocationService");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mShift = getArguments().getParcelable(SHIFT);
        mCalendar = Calendar.getInstance();


        View view = inflater.inflate(R.layout.fragment_shift_details, container, false);

        // Find view ids
        TextView textDatePicker = (TextView) view.findViewById(R.id.text_shift_date_picker);
        TextView textStartTimePicker = (TextView) view.findViewById(R.id.text_start_time_picker);
        mTextEndTimePicker = (TextView) view.findViewById(R.id.text_end_time_picker);
        Button saveButton = (Button) view.findViewById(R.id.button_save);
        Button cancelButton = (Button) view.findViewById(R.id.button_cancel);

        // Set @Shift start date and time
        if (!mShift.getStartTime().isEmpty()) {
            Date startDateTime = shiftDateStringToDate(mShift.getStartTime());

            // Set @Shift start date and start time
            if (startDateTime != null) {
                textDatePicker.setText(String.format(Locale.ENGLISH, "%s",
                        new SimpleDateFormat("EEE, dd MMM yyyy").format(startDateTime)));
                textStartTimePicker.setText(String.format(Locale.ENGLISH, "%s",
                        new SimpleDateFormat("hh:mm a").format(startDateTime)));
            }
        }

        // Set @Shift end time if present, else allow user to enter an end time
        if (!mShift.getEndTime().isEmpty()) {
            Date endDateTime = shiftDateStringToDate(mShift.getEndTime());

            // Set @Shift end time
            if (endDateTime != null) {
                mTextEndTimePicker.setText(String.format(Locale.ENGLISH, "%s",
                        new SimpleDateFormat("hh:mm a").format(endDateTime)));
            }
        } else {
            mTextEndTimePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getChildFragmentManager();
                    TimePickerFragment timePickerFragment =
                            TimePickerFragment.newInstance(mCalendar);
                    timePickerFragment.show(fragmentManager, DIALOG_TIME);
                }
            });
        }

        setUpMapIfNeeded();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ShiftPostBody postBody = new ShiftPostBody();
                postBody.setTime(
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
                                .format(mCalendar.getTime()));
                if (mDeviceLocation == null) {
                    postBody.setLatitude(String.valueOf(0.00000));
                    postBody.setLongitude(String.valueOf(0.00000));
                } else {
                    postBody.setLatitude(String.valueOf(mDeviceLocation.getLatitude()));
                    postBody.setLongitude(String.valueOf(mDeviceLocation.getLongitude()));
                }

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

                Call<String> call = apiInterface.endShift(DEPUTY_USER_SHA, postBody);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        int statusCode = response.code();
                        Log.v(TAG, response.toString());

                        if (statusCode == 200) {
                            Toast.makeText(getActivity(),
                                    getResources().getString(R.string.toast_shift_ended),
                                    Toast.LENGTH_SHORT).show();
                            closeFragment();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(
                getResources().getString(R.string.fragment_shift_details));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
        mCalendar.set(Calendar.HOUR, hours);
        mCalendar.set(Calendar.MINUTE, minutes);

        mTextEndTimePicker.setText(String.format(Locale.ENGLISH, "%02d:%02d", hours, minutes));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initializeMap();
    }

    private void closeFragment() {
        getActivity()
                .getSupportFragmentManager()
                .popBackStack();
    }

    // Set up GoogleMap
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            ((SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map))
                    .getMapAsync(this);
        } else {
            initializeMap();
        }
    }

    private void initializeMap() {
        updateMapMarker();
    }

    // Update map with map location markers
    private void updateMapMarker() {
        LatLng startLocation;
        LatLng endLocation;

        if (mMap == null) {
            return;
        }
        // Map marker for start location
        startLocation = new LatLng(Double.parseDouble(mShift.getStartLatitude()),
                Double.parseDouble(mShift.getStartLongitude()));
        Marker startLocationMarker = mMap.addMarker(new MarkerOptions()
                .position(startLocation)
                .title(getString(R.string.label_map_marker_start)));
        startLocationMarker.setPosition(startLocation);

        // Map marker for end location
        if (mDeviceLocation != null) {
            Marker endLocationMarker;

            // If an end time has not been set, then end location will also not have been set so
            // we need to get end location from device
            if (mShift.getEndTime().isEmpty()) {
                endLocation = new LatLng(mDeviceLocation.getLatitude(), mDeviceLocation.getLongitude());
                endLocationMarker = mMap.addMarker(new MarkerOptions()
                        .position(endLocation)
                        .title(getString(R.string.label_map_marker_current)));
            } else {
                endLocation = new LatLng(Double.parseDouble(mShift.getEndLatitude()),
                        Double.parseDouble(mShift.getEndLongitude()));
                endLocationMarker = mMap.addMarker(new MarkerOptions()
                        .position(endLocation)
                        .title(getString(R.string.label_map_marker_end)));
            }
            endLocationMarker.setPosition(endLocation);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(endLocation));
            mMap.setMinZoomPreference(MAP_ZOOM_CITY_LEVEL);
        }
    }

    // Convert @Shift date String to Date object
    private Date shiftDateStringToDate(String dateTimeString) {
        Date shiftDateTime = null;

        try {
            shiftDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
                    .parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                shiftDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                        .parse(dateTimeString);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return shiftDateTime;
    }
}
