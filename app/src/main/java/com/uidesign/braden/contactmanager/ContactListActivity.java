package com.uidesign.braden.contactmanager;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class ContactListActivity extends AppCompatActivity {

    ArrayList<Contact> contactList;
    ListView listView;
    int lastSelectedPosition;
    ArrayAdapter<Contact> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetailView(new Contact(), 2);
            }
        });

        // contactList = ContactLoader.loadContacts();
        Contact contact = new Contact("Braden", "Herndon","555-555-5555","braden.herndon@gmail.com");
        Contact contact2 = new Contact("Michelle", "Hui Hua", "555-152-6152", "hui@hua.com");
        Contact contact3 = new Contact("Craig", "Buttlord", "666-152-6152", "buttlord@hua.com");
        contactList = new ArrayList<Contact>();
        contactList.add(contact);
        contactList.add(contact2);
        contactList.add(contact3);
        Collections.sort(contactList);

        adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, contactList);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastSelectedPosition = position;
                Contact contact = (Contact) listView.getAdapter().getItem(position);
                openDetailView(contact, 1);
            }
        });
    }

    public void openDetailView(Contact contact, int code) {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("contact", contact);
        intent.putExtra("code", code);
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Contact newContact = (Contact) result.getSerializableExtra("contact");
                contactList.set(lastSelectedPosition, newContact);
                listView.setAdapter(adapter);
            }
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                Contact newContact = (Contact) result.getSerializableExtra("contact");
                contactList.add(newContact);
                Collections.sort(contactList);
                listView.setAdapter(adapter);
            }
        }
    }

}
