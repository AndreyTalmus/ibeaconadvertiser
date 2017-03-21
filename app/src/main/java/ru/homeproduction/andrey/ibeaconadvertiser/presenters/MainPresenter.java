package ru.homeproduction.andrey.ibeaconadvertiser.presenters;

import android.bluetooth.BluetoothAdapter;

import java.util.Observable;
import java.util.Observer;

import ru.homeproduction.andrey.ibeaconadvertiser.BluetoothStatus;
import ru.homeproduction.andrey.ibeaconadvertiser.BluetoothUtils;
import ru.homeproduction.andrey.ibeaconadvertiser.ParametersControl;
import ru.homeproduction.andrey.ibeaconadvertiser.model.IbeaconAdvertisement;
import ru.homeproduction.andrey.ibeaconadvertiser.views.MainView;

public class MainPresenter implements Observer {

    IbeaconAdvertisement mBeacon;
    private MainView mainView;
    BluetoothStatus bluetoothStatus = BluetoothStatus.getInstance();

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        bluetoothStatus.addObserver(this);
        bluetoothStatus.setBluetoothStatus(BluetoothUtils.isBluetoothEnabled());
    }

    public void startAdvertisement(String UUID, String Major, String Minor){
        if(mBeacon == null){
            if(ParametersControl.checkAllParameters(UUID,Major,Minor)){
                mBeacon = new IbeaconAdvertisement(this,UUID,Major,Minor);
            }
            mainView.updateStatus(""+ParametersControl.status);
            ParametersControl.status = "";
        }
        else {
            if(!mBeacon.getAdvertisingStart()){
                if(ParametersControl.checkAllParameters(UUID,Major,Minor)){
                    mBeacon = new IbeaconAdvertisement(this,UUID,Major,Minor);
                }
                mainView.updateStatus(""+ParametersControl.status);
                ParametersControl.status = "";
            }
            else{
                AdvertisementStartYet();
            }

        }


    }

    public void AdvertisementStartFail(){
        mainView.updateStatus("Произошла ошибка при старте рассылки.");
    }

    public void AdvertisementStartSuccess(){
        mainView.updateStatus("Рассылка ведется успешно.");
    }

    public void AdvertisementNotStart(){
        mainView.updateStatus("Рассылка не стартовала.");
    }

    public void AdvertisementStartYet(){
        mainView.updateStatus("Рассылка уже ведется.");
    }

    public void stopAdvertisement(){
            if(mBeacon != null) {
                mainView.updateStatus("Рассылка остановлена.");
                mBeacon.stopAdvertisment();
            }
            else{
                AdvertisementNotStart();
            }
    }


    public void attemptBluetoothOn(){
        mainView.bluetoothUncertain();
        mainView.bluetoothRequestOn();
    }

    public void attemptBluetoothOnFailed(){
        mainView.bluetoothOff();
    }

    @Override
    public void update(Observable observable, Object data) {
        if(bluetoothStatus.getBluetoothStatus() && BluetoothAdapter.getDefaultAdapter() != null){
            if(BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser() != null){
                mainView.bluetoothWithAdvertiser();
            }
            else{
                mainView.bluetoothWithOutAdvertiser();
            }
        }
        else {
            mainView.bluetoothOff();
        }
    }




}
