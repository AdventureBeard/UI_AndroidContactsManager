package com.uidesign.braden.contactmanager;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by braden on 11/3/15.
 */
public class ContactFileIO {

    Context ctx;

    public ContactFileIO(Context context) throws IOException {
        this.ctx = context;
        File file = new File(context.getFilesDir(), "contacts.txt");
        if (!file.exists()) {
            generateTestFile();
        }
    }

    public ArrayList<Contact> readContacts() throws IOException {

        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            FileInputStream fileIn = ctx.openFileInput("contacts.txt");
            InputStreamReader inputReader = new InputStreamReader(fileIn);
            BufferedReader br = new BufferedReader(inputReader);
            String str;
            while ((str=br.readLine()) != null) {
                String[] data = str.split(",", -1);
                contacts.add(new Contact(data[0], data[1], data[2], data[3]));
            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("PLERP FILE NOT FOUND YOU SCRUB");
        } catch (IOException e) {
            System.out.println("SOMETHING WENT WRONG AHHHH");
        }

        return contacts;
    }

    public void writeContacts(ArrayList<Contact> contacts) throws IOException {
        try {
            FileOutputStream fileOut = ctx.openFileOutput("contacts.txt", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);
            String data = "";
            for (int i=0; i<contacts.size(); i++) {
                data = data + contacts.get(i).getFirstName() + ","
                        + contacts.get(i).getLastName() + ","
                        + contacts.get(i).getPhoneNumber() + ","
                        + contacts.get(i).getEmailAddress() + "\n";
            }
            outputWriter.write(data);
            outputWriter.close();
        } catch (Exception e) {
            System.out.println("Write failed, dawg.");
            e.printStackTrace();
        }
    }

    public void generateTestFile() throws IOException {
        Contact c1 = new Contact("Michelle","Nguyen","555-152-5555","michelle@nguyen.com");
        Contact c2 = new Contact("Braden","Herndon","555-278-2086","braden.herndon@gmail.com");
        Contact c3 = new Contact("Alex","Tombrello","499-555-5988","alex.sux@hotmail.com");
        Contact c4 = new Contact("Bardon","Bardonius","555-874-1111","bardon@gmail.com");
        Contact c5 = new Contact("Lionel","VonKittyFace","949-278-3333","lionel@kittyface.gov");
        Contact c6 = new Contact("Nicole","Shintaku","669-555-3055","nicoleshintaku@gmail.com");
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(c1);
        contacts.add(c2);
        contacts.add(c3);
        contacts.add(c4);
        contacts.add(c5);
        contacts.add(c6);
        writeContacts(contacts);
    }
}
