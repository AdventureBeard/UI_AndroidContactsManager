package com.uidesign.braden.contactmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.ActionMenuItem;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class DetailViewActivity extends AppCompatActivity {

    EditText fname;
    EditText lname;
    EditText phone;
    EditText email;

    boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Contact Details");
        setContentView(R.layout.activity_contact_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fname = (EditText) findViewById(R.id.fnameField);
        lname = (EditText) findViewById(R.id.lnameField);
        phone = (EditText) findViewById(R.id.phoneField);
        email = (EditText) findViewById(R.id.emailField);

        fname.setEnabled(false);
        lname.setEnabled(false);
        phone.setEnabled(false);
        email.setEnabled(false);

        Intent i = getIntent();
        Contact contact = (Contact) i.getSerializableExtra("contact");
        fname.setText(contact.getFirstName());
        lname.setText(contact.getLastName());
        phone.setText(contact.getPhoneNumber());
        email.setText(contact.getEmailAddress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contact_detail_view, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_action) {
            toggleEditMode();
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleEditMode() {
        ActionMenuItemView saveAction = (ActionMenuItemView) findViewById(R.id.edit_action);
        if (editMode) {
            if (fname.getText().toString().length() > 0) {
                saveAction.setTitle("Edit");
                editMode = !editMode;

                fname.setEnabled(false);
                lname.setEnabled(false);
                phone.setEnabled(false);
                email.setEnabled(false);

                Intent result = new Intent();
                Contact contact = new Contact(
                        fname.getText().toString(),
                        lname.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString());
                result.putExtra("contact", contact);
                setResult(Activity.RESULT_OK, result);
                finish();
            } else {
                Snackbar.make(this.findViewById(android.R.id.content),
                        "First name required", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else {
            saveAction.setTitle("Save");
            editMode = !editMode;
            fname.setEnabled(true);
            lname.setEnabled(true);
            phone.setEnabled(true);
            email.setEnabled(true);
        }

    }
}
