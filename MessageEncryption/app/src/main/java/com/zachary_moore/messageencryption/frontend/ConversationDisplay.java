package com.zachary_moore.messageencryption.frontend;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zachary_moore.messageencryption.R;
import com.zachary_moore.messageencryption.backend.Conversation;
import com.zachary_moore.messageencryption.backend.Message;
import com.zachary_moore.messageencryption.backend.User;

import java.util.ArrayList;

public class ConversationDisplay extends AppCompatActivity {

    private User _owner;
    private Conversation _convo;
    private ArrayList<TextView> _allText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _owner = new User("test", "testPass");
        _convo = new Conversation(_owner);
        _allText = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton sendButton = (FloatingActionButton) findViewById(R.id.newButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                newMessage(v);
            }
        });

        ToggleButton t = (ToggleButton) findViewById(R.id.encryptButton);
        t.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    _convo.encryptConversation();

                    ArrayList<Message> allMessages = _convo.getMessages();
                    for (int i = 0; i < _allText.size(); i++) {

                        Message m = allMessages.get(i);
                        _allText.get(i).setText(m.getDisplayText());
                    }
                } else {
                    // The toggle is disabled
                    _convo.decryptConversation();

                    ArrayList<Message> allMessages = _convo.getMessages();
                    for (int i = 0; i < _allText.size(); i++) {

                        Message m = allMessages.get(i);
                        _allText.get(i).setText(m.getDisplayText());
                    }
                }
            }
        });

    }

    public void newMessage(View view){
        EditText field = (EditText) findViewById(R.id.messageToSend);
        String messageText = field.getText().toString();

        Message toSend = new Message(messageText, _owner);
        _convo.addMessage(toSend);

        LinearLayout v = (LinearLayout) findViewById(R.id.messageDisplay);
        TextView t = new TextView(v.getContext());
        _allText.add(t);
        t.setText(messageText);
        v.addView(t);
    }
}
