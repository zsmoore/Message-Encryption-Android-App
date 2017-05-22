package com.zachary_moore.messageencryption.backend;

import java.util.ArrayList;

/**
 * Created by zsmoore on 5/22/17.
 */

public class Conversation {

    private static final String TAG = "Conversation";

    private User _owner;
    private ArrayList<User> _recipients;
    private ArrayList<Message> _messages;

    public Conversation(User owner, ArrayList<User> recipients){
        _recipients = recipients;
        _owner = owner;
        _messages = new ArrayList<>();
    }

    public Conversation(User owner){
        _recipients = new ArrayList<>();
        _owner = owner;
    }

    public void addRecipient(User toAdd){
        _recipients.add(toAdd);
    }

    public void addRecipients(ArrayList<User> recipients){
        for(User u : recipients){
            _recipients.add(u);
        }
    }

    public void setRecipients(ArrayList<User> recipients){
        _recipients = recipients;
    }

    public void clearRecipients(){
        _recipients = new ArrayList<>();
    }

    public void addMessage(Message toAdd){
        _messages.add(toAdd);
        _owner.addMessage(toAdd);
    }

    public void addMessages(ArrayList<Message> messages){
        for(Message m : messages){
            _messages.add(m);
            _owner.addMessage(m);
        }
    }

    public void setMessages(ArrayList<Message> messages){
        for(Message m : messages){
            _owner.removeMessage(m);
        }
        _messages = messages;
    }

    public void deleteMessage(Message toDelete){
        _messages.remove(toDelete);
        _owner.removeMessage(toDelete);
    }

    public void clearMessages(){
        for(Message m : _messages){
            _owner.removeMessage(m);
        }
        _messages = new ArrayList<>();
    }

    public void encryptConversation(){
        for(Message m : _messages){
            if(m.isPlainText()) m.swapText();
        }
    }

    public void decryptConversation(){
        for(Message m : _messages){
            if(m.isEncrypted()) m.swapText();
        }
    }
}
