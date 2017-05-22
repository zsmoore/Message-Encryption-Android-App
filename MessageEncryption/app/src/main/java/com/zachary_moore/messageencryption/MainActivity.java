package com.zachary_moore.messageencryption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zachary_moore.messageencryption.backend.Message;
import com.zachary_moore.messageencryption.backend.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User test = new User("test user", "test pass");
        Message testM = new Message("This is text", test);
        testM.swapText();
        Log.d("MainActivity", testM.getDisplayText());
        testM.swapText();
        Log.d("MainActivity", testM.getDisplayText());

    }
}
