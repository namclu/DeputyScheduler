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

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private static final String START_TIME = "StartTime";
    private static final String END_TIME = "EndTime";
    private static final String SHIFT = "Shift";
    // Map zoom levels: 1.0f = World view, 20.0f = Buildings view
    private static final float MAP_ZOOM_CITY_LEVEL = 10.0f;

    // Class variables
    private TextView mTextDatePicker;
    private TextView mTextStartTimePicker;
    private TextView mTextEndTimePicker;
    private Calendar mCalendar;
    private Button mSaveButton;
    private Button mCancelButton;
    private GoogleMap mMap;
    private Location mDeviceLocation;
    private Marker mDeviceLocationMarker;
    private DeviceLocationService mDeviceLocationService;

    public static ShiftDetailsFragment newInstance(Shift shift) {
        ShiftDetailsFragment fragment = new ShiftDetailsFragment();
        Bundle args = new Bundle();
        //args.putSerializable(START_TIME, shift.getStartTime());
        //args.putSerializable(END_TIME, shift.getEndTime());
        args.putParcelable(SHIFT, shift);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shift_details, container, false);

        Bundle args = getArguments();
        Shift shift = args.getParcelable(SHIFT);
        String completeStartTimeString = shift.getStartTime();//args.getString(START_TIME);
        String startDateString = completeStartTimeString.split("T")[0];
        String startTimeString = completeStartTimeString.split("T")[1];
        String completeEndTimeString  = shift.getEndTime();//args.getString(END_TIME);

        // Find view ids
        mTextDatePicker = (TextView) view.findViewById(R.id.text_shift_date_picker);
        mTextStartTimePicker = (TextView) view.findViewById(R.id.text_start_time_picker);
        mTextEndTimePicker = (TextView) view.findViewById(R.id.text_end_time_picker);
        mSaveButton = (Button) view.findViewById(R.id.button_save);
        mCancelButton = (Button) view.findViewById(R.id.button_cancel);

        // Todo: Format display date/time to match that of when date/time are entered
        // e.g. Thu, 3 Aug 2017 and 13:31
        mTextDatePicker.setText(String.format("%s", startDateString));
        mTextStartTimePicker.setText(String.format("%s", startTimeString));
        //mTextDatePicker.setText(new SimpleDateFormat("EEE, MMM d yyyy", Locale.ENGLISH).format(completeStartTimeString));

        // Init variables
        mCalendar = Calendar.getInstance();
        setUpMapIfNeeded();

        if (!completeEndTimeString.isEmpty()) {
            String endTimeString = completeEndTimeString.split("T")[1];
            mTextEndTimePicker.setText(String.format("%s", endTimeString));
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

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ShiftPostBody postBody = new ShiftPostBody();
                postBody.setTime(
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
                                .format(mCalendar.getTime()));
                if (mDeviceLocation == null) {
                    postBody.setLatitude(String.valueOf(0));
                    postBody.setLongitude(String.valueOf(0));
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
                            Toast.makeText(
                                    getActivity(),
                                    getResources().getString(R.string.toast_shift_ended),
                                    Toast.LENGTH_SHORT)
                                    .show();
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

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFragment();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DeviceLocationService) {
            mDeviceLocationService = (DeviceLocationService) context;
            mDeviceLocation = mDeviceLocationService.getDeviceLocation();
        } else {
            throw new ClassCastException(
                    "Activity must implement DeviceLocationService");
        }
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

    private void updateMapMarker() {
        if (mMap == null) {
            return;
        }
        if (mDeviceLocation != null) {
            LatLng mapLocation = new LatLng(mDeviceLocation.getLatitude(), mDeviceLocation.getLongitude());
            if (mDeviceLocationMarker == null) {
                mDeviceLocationMarker = mMap.addMarker(new MarkerOptions()
                        .position(mapLocation)
                        .title(getString(R.string.label_map_marker_end)));
            } else {
                mDeviceLocationMarker.setPosition(mapLocation);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mapLocation));
            mMap.setMinZoomPreference(MAP_ZOOM_CITY_LEVEL);
        }
    }
}
