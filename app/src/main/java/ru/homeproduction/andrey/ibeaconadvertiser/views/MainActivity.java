package ru.homeproduction.andrey.ibeaconadvertiser.views;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import ru.homeproduction.andrey.ibeaconadvertiser.R;
import ru.homeproduction.andrey.ibeaconadvertiser.presenters.MainPresenter;

public class MainActivity extends Activity implements MainView  {

    private Button btnTurnOn,btnStart,btnStop;
    private TextView tvName,tvUUID,tvMajor,tvMinor,tvError,tvStatus;
    private EditText etUUID,etMajor,etMinor;
    private ProgressBar progressBar;
    private ScrollView scroll;

    private MainPresenter presenter;
    private static final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scroll = (ScrollView) findViewById(R.id.scroll);
        btnTurnOn = (Button) findViewById(R.id.btnTurnOn);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);

        tvName = (TextView) findViewById(R.id.tvName);
        tvUUID = (TextView) findViewById(R.id.tvUUID);
        tvMajor = (TextView) findViewById(R.id.tvMajor);
        tvMinor = (TextView) findViewById(R.id.tvMinor);
        tvError = (TextView) findViewById(R.id.tvError);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        etUUID = (EditText) findViewById(R.id.etUUID);
        etMajor = (EditText) findViewById(R.id.etMajor);
        etMinor = (EditText) findViewById(R.id.etMinor);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        presenter = new MainPresenter(this);

        btnTurnOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                presenter.attemptBluetoothOn();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String UUID = etUUID.getText().toString();
                String Major = etMajor.getText().toString();
                String Minor = etMinor.getText().toString();
                presenter.startAdvertisement(UUID,Major,Minor);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                presenter.stopAdvertisement();
            }
        });
    }


    @Override
    public void bluetoothOff(){
        btnTurnOn.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        btnStop.setVisibility(View.INVISIBLE);
        tvName.setVisibility(View.INVISIBLE);
        tvUUID.setVisibility(View.INVISIBLE);
        tvMajor.setVisibility(View.INVISIBLE);
        tvMinor.setVisibility(View.INVISIBLE);
        tvError.setVisibility(View.INVISIBLE);
        tvStatus.setVisibility(View.INVISIBLE);
        etUUID.setVisibility(View.INVISIBLE);
        etMajor.setVisibility(View.INVISIBLE);
        etMinor.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        scroll.setVisibility(View.INVISIBLE);
    }

    @Override
    public void bluetoothUncertain(){
        btnTurnOn.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        btnStop.setVisibility(View.INVISIBLE);
        tvName.setVisibility(View.INVISIBLE);
        tvUUID.setVisibility(View.INVISIBLE);
        tvMajor.setVisibility(View.INVISIBLE);
        tvMinor.setVisibility(View.INVISIBLE);
        tvError.setVisibility(View.INVISIBLE);
        tvStatus.setVisibility(View.INVISIBLE);
        etUUID.setVisibility(View.INVISIBLE);
        etMajor.setVisibility(View.INVISIBLE);
        etMinor.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        scroll.setVisibility(View.INVISIBLE);
    }

    @Override
    public void bluetoothRequestOn(){
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, REQUEST_ENABLE_BT);
    }

    @Override
    public void bluetoothWithAdvertiser(){
        btnTurnOn.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.VISIBLE);
        tvName.setVisibility(View.VISIBLE);
        tvUUID.setVisibility(View.VISIBLE);
        tvMajor.setVisibility(View.VISIBLE);
        tvMinor.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.INVISIBLE);
        tvStatus.setVisibility(View.VISIBLE);
        etUUID.setVisibility(View.VISIBLE);
        etMajor.setVisibility(View.VISIBLE);
        etMinor.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        scroll.setVisibility(View.VISIBLE);
    }

    @Override
    public void bluetoothWithOutAdvertiser(){
        btnTurnOn.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        btnStop.setVisibility(View.INVISIBLE);
        tvName.setVisibility(View.INVISIBLE);
        tvUUID.setVisibility(View.INVISIBLE);
        tvMajor.setVisibility(View.INVISIBLE);
        tvMinor.setVisibility(View.INVISIBLE);
        tvError.setVisibility(View.VISIBLE);
        tvStatus.setVisibility(View.INVISIBLE);
        etUUID.setVisibility(View.INVISIBLE);
        etMajor.setVisibility(View.INVISIBLE);
        etMinor.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        scroll.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateStatus(String status){
        tvStatus.setText(""+status);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {

            }
            else {
                presenter.attemptBluetoothOnFailed();
            }
        }
    }


}
