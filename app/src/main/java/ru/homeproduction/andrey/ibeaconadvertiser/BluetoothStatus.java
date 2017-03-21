package ru.homeproduction.andrey.ibeaconadvertiser;

import java.util.Observable;

public class BluetoothStatus extends Observable {

    private boolean bluetoothStatus;

    private static class BluetoothStatusHolder {
        private static final BluetoothStatus INSTANCE = new BluetoothStatus();
    }

    public static BluetoothStatus getInstance() {
        return  BluetoothStatusHolder.INSTANCE;
    }

    public void setBluetoothStatus(boolean bluetoothStatus) {
        this.bluetoothStatus = bluetoothStatus;

        synchronized (this) {
            setChanged();
            notifyObservers();
        }
    }

    public boolean getBluetoothStatus() {
        return bluetoothStatus;
    }

}