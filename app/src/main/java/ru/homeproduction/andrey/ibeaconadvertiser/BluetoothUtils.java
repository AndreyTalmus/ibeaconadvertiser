package ru.homeproduction.andrey.ibeaconadvertiser;

import android.bluetooth.BluetoothAdapter;

public class BluetoothUtils {

    public static boolean isBluetoothEnabled()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter.isEnabled();
    }

}
