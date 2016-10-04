package com.example.geraldmichael.zbarcodescan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final String TAG = "MainActivity";
    ZXingScannerView mScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScanner = new ZXingScannerView(this);
        setContentView(mScanner);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScanner.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScanner.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScanner.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        Log.v(TAG, result.getText()); // Prints scan results
        Log.v(TAG, result.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        mScanner.resumeCameraPreview(this);
    }
}
