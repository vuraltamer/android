package com.example.toshiba.androidguide_to_listview_simplelistitem2;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends ListActivity {

    List<String[]> List = new LinkedList<String[]>(); //Kişi ad ve numaraları burada tutulacak.
    // {{"Tamer Vural","554xxxxxxx"},{}} gibi.

    public void fetchContacts() { //Rehbere baglanıp kayıtları alıyor...

        String phoneNumber = null;
        String email = null;
        String kisiad = "";
        String kisitel = "";

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        //StringBuffer output = new StringBuffer();

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

                if (hasPhoneNumber > 0) {
                    kisiad = "" + name;

                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        kisitel = "" + phoneNumber;

                    }

                    phoneCursor.close();

                    // Query and loop for every email of the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);

                    while (emailCursor.moveToNext()) {

                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA)); //Bize lazım olmayacak.

                    }
                    List.add(new String[] {kisiad, kisitel}); //Listemize ad ve tel.numaraları burada alınıyor.
                    emailCursor.close();
                }

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchContacts();

        setListAdapter(new ArrayAdapter<String[]>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                List) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String[] entry = List.get(position); // Listenin icinden bir ad ve telefon alıyor.
                // {"Tamer Vural","0554xxxxxxx"} gibi...
                TextView text1 = (TextView) view.findViewById(android.R.id.text1); // Listview'e ilk text alınıyor.
                TextView text2 = (TextView) view.findViewById(android.R.id.text2); // Listview'e ikinci text alınıyor.
                text1.setText(entry[0]); // text1'e ad veriliyor.
                text2.setText(entry[1]); // text2'e telefon numarası veriliyor.
                return view;
            }
        });
    }
}