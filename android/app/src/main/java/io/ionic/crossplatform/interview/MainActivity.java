package io.ionic.crossplatform.interview;

import android.os.Bundle;

import com.getcapacitor.BridgeActivity;

import io.ionic.crossplatform.interview.di.KoinStarter;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KoinStarter.INSTANCE.start(this);
    }
}
