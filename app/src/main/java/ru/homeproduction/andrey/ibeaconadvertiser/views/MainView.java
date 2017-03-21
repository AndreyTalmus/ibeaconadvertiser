package ru.homeproduction.andrey.ibeaconadvertiser.views;

public interface MainView {

    //Bluetooth отключен/недоступен.
    void bluetoothOff();

    //Bluetooth в неопределенном состоянии.
    void bluetoothUncertain();

    //Запрос на включение Bluetooth.
    void bluetoothRequestOn();

    //Bluetooth активен и поддерживает методы Advertiser.
    void bluetoothWithAdvertiser();

    //Bluetooth активен, но не поддерживает методы Advertiser.
    void bluetoothWithOutAdvertiser();

    //Обновления состояния о текущей Advertisment.
    void updateStatus(String status);

}
