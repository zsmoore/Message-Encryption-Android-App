package com.zachary_moore.messageencryption.backend;

import android.provider.BaseColumns;

/**
 * Created by zsmoore on 5/22/17.
 */

public final class MessageEncryptionContract {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserBase.TABLE_NAME + " (" +
                    UserBase._ID + " INTEGER PRIMARY KEY," +
                    UserBase.USERNAME_COLUMN + " TEXT," +
                    UserBase.PASSWORD_COLUMN + " TEXT," +
                    UserBase.MESSAGE_ID + "INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserBase.TABLE_NAME;

    private MessageEncryptionContract(){}

    public static class UserBase implements BaseColumns{

        public static final String TABLE_NAME = "UserBase";
        public static final String USERNAME_COLUMN = "username";
        public static final String PASSWORD_COLUMN = "password";
        public static final String MESSAGE_ID = "messageID";

    }

}
