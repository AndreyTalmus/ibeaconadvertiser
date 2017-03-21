package ru.homeproduction.andrey.ibeaconadvertiser.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;

import java.nio.ByteBuffer;
import java.util.UUID;

import ru.homeproduction.andrey.ibeaconadvertiser.UuidUtils;
import ru.homeproduction.andrey.ibeaconadvertiser.presenters.MainPresenter;

public class IbeaconAdvertisement {

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private AdvertiseData mAdvertiseData;
    private AdvertiseSettings mAdvertiseSettings;
    private AdvertiseCallback mAdvertiseCallback;
    private AdvertiseCallback mAdvertiseCallbackStop;

    private UUID mUUID;
    private int major;
    private int minor;
    private int majorHIGH;
    private int majorLOW;
    private int minorHIGH;
    private int minorLOW;

    private boolean AdvertisingStart;

    private final MainPresenter presenter;

    public IbeaconAdvertisement(MainPresenter presenter, String uuid, String major, String minor){

        this.presenter = presenter;


        this.major = Integer.parseInt(major);
        this.minor = Integer.parseInt(minor);
        byte[] dataMajor = new byte[2];

        dataMajor[0] = (byte)(this.major & 0xFF);
        dataMajor[1] = (byte)((this.major >> 8) & 0xFF);

        majorHIGH = dataMajor[1] >= 0 ? dataMajor[1] : 256 + dataMajor[1];
        majorLOW= dataMajor[0] >= 0 ? dataMajor[0] : 256 + dataMajor[0];

        byte[] dataMinor = new byte[2];
        dataMinor[0] = (byte)(this.minor & 0xFF);
        dataMinor[1] = (byte)((this.minor >> 8) & 0xFF);

        minorHIGH = dataMinor[1] >= 0 ? dataMinor[1] : 256 + dataMinor[1];
        minorLOW = dataMinor[0] >= 0 ? dataMinor[0] : 256 + dataMinor[0];

        mUUID = UuidUtils.getUUID(uuid);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();

        setAdvertiseData();
        setAdvertiseSettings();

        mAdvertiseCallback = new AdvertiseCallback() {

            @Override
            public void onStartFailure(int errorCode) {
                FailStart();
            }

            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                SuccessStart();
            }

        };

        mBluetoothLeAdvertiser.startAdvertising(mAdvertiseSettings, mAdvertiseData, mAdvertiseCallback);
        AdvertisingStart = true;

    }

    public void stopAdvertisment(){
        if(AdvertisingStart){
            mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
            AdvertisingStart = false;
        }
        else{
            presenter.AdvertisementNotStart();
        }

    }

    public boolean getAdvertisingStart(){
        return AdvertisingStart;
    }


    private void FailStart(){
        presenter.AdvertisementStartFail();
    }

    private void SuccessStart(){
        presenter.AdvertisementStartSuccess();
    }

    protected void setAdvertiseData() {
        AdvertiseData.Builder mBuilder = new AdvertiseData.Builder();
        ByteBuffer mManufacturerData = ByteBuffer.allocate(24);
        byte[] uuid = UuidUtils.asBytes(mUUID);
        mManufacturerData.put(0, (byte)0x02);
        mManufacturerData.put(1, (byte)0x15);
        for (int i=2; i<=17; i++) {
            mManufacturerData.put(i, uuid[i-2]); // adding the UUID
        }
        mManufacturerData.put(18, (byte)majorHIGH); // first byte of Major
        mManufacturerData.put(19, (byte)majorLOW); // second byte of Major
        mManufacturerData.put(20, (byte)minorHIGH); // first minor
        mManufacturerData.put(21, (byte)minorLOW); // second minor
        mManufacturerData.put(22, (byte)0xB5); // txPower
        mBuilder.addManufacturerData(76, mManufacturerData.array()); // apple ID
        mAdvertiseData = mBuilder.build();
    }


    protected void setAdvertiseSettings() {
        AdvertiseSettings.Builder mBuilder = new AdvertiseSettings.Builder();
        mBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER);
        mBuilder.setConnectable(false);
        mBuilder.setTimeout(0);
        mBuilder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM);
        mAdvertiseSettings = mBuilder.build();
    }

}
