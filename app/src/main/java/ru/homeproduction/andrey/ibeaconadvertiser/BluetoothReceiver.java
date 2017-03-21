package ru.homeproduction.andrey.ibeaconadvertiser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothReceiver extends BroadcastReceiver {

    private static final int STATE_OFF = 0x0000000a;
    private static final int STATE_ON = 0x0000000c;
    public static final int ERROR = 0x80000000;
    private static final String EXTRA_STATE = "android.bluetooth.adapter.extra.STATE";
    private static final String ACTION_STATE_CHANGED = "android.bluetooth.adapter.action.STATE_CHANGED";

    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ACTION_STATE_CHANGED))
        {
            int extraData = intent.getIntExtra(EXTRA_STATE,ERROR);
            if(extraData == STATE_ON ){

                BluetoothStatus bluetoothStatus =  BluetoothStatus.getInstance();
                bluetoothStatus.setBluetoothStatus(true);

            }
            else if (extraData == STATE_OFF) {

                BluetoothStatus bluetoothStatus =  BluetoothStatus.getInstance();
                bluetoothStatus.setBluetoothStatus(false);
            }

        }

    }

}