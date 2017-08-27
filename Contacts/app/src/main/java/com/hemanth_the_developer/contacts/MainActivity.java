package com.hemanth_the_developer.contacts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.*;
import java.net.*;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating a button click listener for the "Add Contact" button
        OnClickListener addClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Getting reference to Name EditText

                // Getting reference to Mobile EditText

                // Getting reference to HomePhone EditText

                // Getting reference to HomeEmail EditText

                // Getting reference to WorkEmail EditText


                URL url = new URL("http://www.cs.columbia.edu/~coms6998-8/assignments/homework2/contacts/contacts.txt");

                BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));

                while ((str = input.readLine()) != null) {
                    String array[] = str.trim().split("\\s+");



                    ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
                    arrayList.add(ContentProviderOperation.newInsert(
                            ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                            .build());

                    //------------------------------------------------------ Names
                    if (array[0] != null) {
                        arrayList.add(ContentProviderOperation.newInsert(
                                ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                .withValue(
                                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                        array[0]).build());
                    }

                    //------------------------------------------------------ Mobile Number
                    if (array[2] != null) {
                        arrayList.add(ContentProviderOperation.
                                newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, array[2])
                                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                                .build());
                    }

                    //------------------------------------------------------ Home Numbers
                    if (array[3] != null) {
                        arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, array[3])
                                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                        ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                                .build());
                    }

                    //------------------------------------------------------ Email
                    if (array[1] != null) {
                        arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Email.DATA, array[1])
                                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                .build());
                    }








                    ArrayList<ContentProviderOperation> ops =
                            new ArrayList<ContentProviderOperation>();

                    int rawContactID = ops.size();

                    // Adding insert operation to operations list
                    // to insert a new raw contact in the table ContactsContract.RawContacts
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(RawContacts.ACCOUNT_NAME, null)
                            .build());



                    // Adding insert operation to operations list
                    // to insert display name in the table ContactsContract.Data


                    // Adding insert operation to operations list
                    // to insert Mobile Number in the table ContactsContract.Data


                    // Adding insert operation to operations list
                    // to  insert Home Phone Number in the table ContactsContract.Data


                    // Adding insert operation to operations list
                    // to insert Home Email in the table ContactsContract.Data

                    // Adding insert operation to operations list
                    // to insert Work Email in the table ContactsContract.Data


                    try{
                        // Executing all the insert operations as a single database transaction
                        getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                        Toast.makeText(getBaseContext(), "Contact is successfully added", Toast.LENGTH_SHORT).show();
                    }catch (RemoteException e) {
                        e.printStackTrace();
                    }catch (OperationApplicationException e) {
                        e.printStackTrace();
                    }
                }
            };

            // Creating a button click listener for the "Add Contact" button
            OnClickListener contactsClickListener = new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Creating an intent to open Android's Contacts List
                    Intent contacts = new Intent(Intent.ACTION_VIEW,ContactsContract.Contacts.CONTENT_URI);

                    // Starting the activity
                    startActivity(contacts);
                }
            };

            // Getting reference to "Add Contact" button
            Button btnAdd = (Button) findViewById(R.id.btn_add);

            // Getting reference to "Contacts List" button
            Button btnContacts = (Button) findViewById(R.id.btn_contacts);

            // Setting click listener for the "Add Contact" button
            btnAdd.setOnClickListener(addClickListener);

            // Setting click listener for the "List Contacts" button
            btnContacts.setOnClickListener(contactsClickListener);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.activity_main, menu);
            return true;
        }
    }
