package com.rnhidsdkbridge;

import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.assaabloy.mobilekeys.api.ApiConfiguration;
import com.assaabloy.mobilekeys.api.MobileKeys;
import com.assaabloy.mobilekeys.api.MobileKeysApi;
import com.assaabloy.mobilekeys.api.ReaderConnectionController;
import com.assaabloy.mobilekeys.api.ble.*;
import com.assaabloy.mobilekeys.api.MobileKeysException;
import com.assaabloy.mobilekeys.api.hce.HceConnectionCallback;
import com.assaabloy.mobilekeys.api.hce.HceConnectionEvent;
import com.assaabloy.mobilekeys.api.hce.HceConnectionListener;

import java.util.Map;
import java.util.HashMap;

public class HidSdkBridge extends ReactContextBaseJavaModule {

    private static final int LOCK_SERVICE_CODE = 2;

    private static final String TAG = HidSdkBridge.class.getName();
    private MobileKeys mobileKeys;
    private ReaderConnectionCallback readerConnectionCallback;
    private HceConnectionCallback hceConnectionCallback;
    private ReaderConnectionController readerConnectionController;
    private ScanConfiguration scanConfiguration;
    private MobileKeysApi mobileKeysFactory;

    public HidSdkBridge(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "HidSdkBridge";
    }

    @ReactMethod
    public void initializeMobileKeysApi() {
        ScanConfiguration scanConfiguration = new ScanConfiguration.Builder(new OpeningTrigger[]{
                new TapOpeningTrigger(getReactApplicationContext())}, LOCK_SERVICE_CODE).build();

        ApiConfiguration apiConfiguration = new ApiConfiguration.Builder()
                .setApplicationId("HQO")
                .setApplicationDescription("HID Mobile Keys Example Implementation")
                .build();
        mobileKeysFactory = MobileKeysApi.getInstance();
        mobileKeysFactory.initialize(getReactApplicationContext(), apiConfiguration, scanConfiguration);
        if(mobileKeysFactory.isInitialized() == false) {
            Toast.makeText(getReactApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            throw new IllegalStateException();
        } else {
            Toast.makeText(getReactApplicationContext(), "initialize success", Toast.LENGTH_SHORT).show();
        }
    }

    @ReactMethod
    public void show(String message) {
        Toast.makeText(getReactApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}