package com.namclu.android.deputyscheduler;

import android.location.Location;

/**
 * Created by namlu on 8/15/2017.
 *
 * Interface to provide the device location to other fragments
 */

public interface DeviceLocationService {
    Location getDeviceLocation();
}
