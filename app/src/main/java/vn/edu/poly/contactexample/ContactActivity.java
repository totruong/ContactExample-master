package vn.edu.poly.contactexample;

import android.database.Cursor;
import android.net.Uri;
import android.provider.Browser;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.contactexample.adapter.ContactAdapter;
import vn.edu.poly.contactexample.model.MyBooKmark;
import vn.edu.poly.contactexample.model.MyContact;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ContactActivity extends AppCompatActivity {

    private Button btnLoadContacts;
    private RecyclerView lvListContact;
    private Button btnLoadBookMark;


    private ContactAdapter contactAdapter;
    private List<MyContact> myContacts;
    private List<MyBooKmark> myBooKmarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initViews();
        initData();
        initActions();


    }


    private void initActions() {

        btnLoadBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String []projection={
                            "title",
                            "url",
                    };
                    Cursor c=getContentResolver()
                            .query(Uri.parse("content://browser/bookmarks"), projection, null, null, null);
                    c.moveToFirst();
                    String s="";
                    int titleIndex=c.getColumnIndex
                            ("title");
                    int urlIndex=c.getColumnIndex
                            ("url");
                    while(!c.isAfterLast())
                    {
                        s+=c.getString(titleIndex)+" - "+
                                c.getString(urlIndex);
                        c.moveToNext();

                    }
                    c.close();

                Toast.makeText(getApplicationContext() , s, Toast.LENGTH_LONG).show();
            }
        });


        btnLoadContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://contacts/people");
                myContacts = new ArrayList<>();
                CursorLoader cursorLoader =
                        new CursorLoader(ContactActivity.this, uri,
                                null, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                cursor.moveToFirst();

                // Neu kiem tra thay vi tri cuoi cung ko con du lieu thi ket thuc vong while
                while (cursor.isAfterLast() == false) {
                    // khai bao 2 cot trong bang Contact
                    String column_id = ContactsContract.Contacts._ID;
                    String column_name = ContactsContract.Contacts.DISPLAY_NAME;


                    // lay du lieu tu con tro Cursor thong qua column Name (Ten Cot)
                    int id = cursor.getInt(cursor.getColumnIndex(column_id));
                    String name = cursor.getString(cursor.getColumnIndex(column_name));

                    Log.e("NAME", name);
                    // gan du lieu vao MyContact

                    MyContact myContact = new MyContact();
                    myContact.name = name;
                    myContact.phone = "" + id;

                    // gan du lieu vao ArrayList
                    myContacts.add(myContact);

                    cursor.moveToNext();

                }

                cursor.close();
                // khai bao va set data cho List
                contactAdapter = new ContactAdapter(ContactActivity.this, myContacts);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ContactActivity.this);

                lvListContact.setLayoutManager(linearLayoutManager);
                lvListContact.setAdapter(contactAdapter);


            }
        });

    }

    private void initData() {
        // khoi tao du lieu fake test RecycleView
        myContacts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MyContact myContact = new MyContact();
            myContact.name = "to truong";
            myContact.phone = "0966841003";

            // Bo MyContact vao arrayList
            myContacts.add(myContact);
        }
        contactAdapter = new ContactAdapter(this, myContacts);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        lvListContact.setLayoutManager(linearLayoutManager);
        lvListContact.setAdapter(contactAdapter);

    }


    public void initViews() {
        btnLoadContacts = findViewById(R.id.btnLoadContacts);
        lvListContact = findViewById(R.id.lvListContact);
        btnLoadContacts = findViewById(R.id.btnLoadContacts);
        btnLoadBookMark = findViewById(R.id.btnLoadBookMark);

    }

}
