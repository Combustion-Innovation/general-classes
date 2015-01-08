package com.ci.hub.contactmanager;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 1/7/15.
 */

/*
 * Julian's Asana instructions:
 * make an api to get contacts from a users phone book. Methods should include:

 Did user agree to getcontacts

 bool

 getContactsNumbers

 getContactsNames

 startGettingContacts
 */

public class ContactManager {
    public static final String TAG = "ContactManager";

    private Activity activity;
    private boolean contactPermission;

    private List getAllContacts() {
        String[] mProjection = new String[]
                {
                        ContactsContract.Profile._ID,
                        ContactsContract.Profile.DISPLAY_NAME_PRIMARY,
                        ContactsContract.Profile.LOOKUP_KEY,
                        ContactsContract.Profile.PHOTO_THUMBNAIL_URI
                };

        // Retrieves the profile from the Contacts Provider
        Cursor mProfileCursor = activity.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                mProjection,
                null,
                null,
                null);

        Log.d(TAG, mProfileCursor.getCount() + "");

        return null;
    }

    public ContactManager(Activity activity) {
        this.activity = activity;
        contactPermission = false;

        getAllContacts();
    }

    public boolean hasContactPermission() {
        return contactPermission;
    }

    public List<String> getContactsNumbers() {
        List<String> numbers = new ArrayList<String>();
        // collect numbers...
        return numbers;
    }

    public List<String> getContactsNames() {
        List<String> names = new ArrayList<String>();
        // collect names
        return names;
    }

    public void startGettingContacts() {

    }
}
