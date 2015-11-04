package com.uidesign.braden.contactmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ContactListActivity extends AppCompatActivity {

    ArrayList<Contact> contactList;
    ListView listView;
    int lastSelectedPosition;
    ArrayAdapter<Contact> adapter;
    ContactFileIO contactFileIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Contacts");
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

        populateContactsList();
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
        // If the requestCode was 1, just update the last selected Contact entry.
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Contact newContact = (Contact) result.getSerializableExtra("contact");
                contactList.set(lastSelectedPosition, newContact);
            }
        // If the requestCode was 2, add a new entry to the Contact list.
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                Contact newContact = (Contact) result.getSerializableExtra("contact");
                contactList.add(newContact);
            }
        }
        Collections.sort(contactList);
        try {
            contactFileIO.writeContacts(contactList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        populateContactsList();
    }

    public void populateContactsList() {
        // contactList = ContactFileIO.loadContacts();
        try {
            contactFileIO= new ContactFileIO(this);
            contactList = contactFileIO.readContacts();
            Collections.sort(contactList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, contactList);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

}
