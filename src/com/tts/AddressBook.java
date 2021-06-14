package com.tts;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AddressBook {
    private List<Entry> entries = new ArrayList<>();
    File myAddressBook;

    public AddressBook() {
        createAddressBook("addressBook.txt");
    }

    public AddressBook(String fileName) {
        createAddressBook(fileName);
    }

    public void createAddressBook(String fileName) {
        try {
            myAddressBook= new File(fileName);
            if (!myAddressBook.createNewFile()) {
                loadAddressBook(myAddressBook);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void loadAddressBook(File myAddressBook) {
        try {
            Scanner myReader = new Scanner(myAddressBook);
            while (myReader.hasNextLine()) {
                Entry entry = new Entry();
                String data = myReader.nextLine();
                String[] dataArr = data.split(" ", 0);
                entry.setFirstName(dataArr[0]);
                entry.setLastName(dataArr[1]);
                entry.setPhoneNumber(dataArr[2]);
                entry.setEmail(dataArr[3]);
                entries.add(entry);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private int getEntryIndexByEmail(String email) {
        for(Entry entry : entries) {
            if(entry.getEmail().equals(email)) {
                return entries.indexOf(entry);
            }
        }
        return -1;
    }

    public void add(Entry entry) {
        if( getEntryIndexByEmail( entry.getEmail()) == -1 ) {
            entries.add(entry);
            save();
            System.out.println("Added new entry!\n");
            return;
        }
        System.out.println("Entry with that email address already exists.\n");
    }

    public void removeByEmail(String email) {
        int index = getEntryIndexByEmail(email);
        if ( index == -1 ) {
            System.out.println("No entry with that email address exists.\n");
        } else {
            System.out.println("Deleted entry:");
            System.out.println(entries.get(index));
            entries.remove(index);
            save();
        }
    }

    public void deleteAll() {
        entries.clear();
        save();
    }

    public void search(String type, String value) {
        boolean found = false;
        String pattern = "(?i).*" + value + ".*";

        System.out.println("Address Book entries that contain '" + value + "':");
        switch(type) {
            case "1":
                for(Entry entry : entries) {
                    if( entry.getFirstName().matches(pattern) ) {
                        System.out.print(entry);
                        found = true;
                    }
                }
                break;
            case "2":
                for(Entry entry : entries) {
                    if( entry.getLastName().matches(pattern) ) {
                        System.out.print(entry);
                        found = true;
                    }
                }
                break;
            case "3":
                for(Entry entry : entries) {
                    if( entry.getPhoneNumber().matches(pattern) ) {
                        System.out.print(entry);
                        found = true;
                    }
                }
                break;
            case "4":
                for(Entry entry : entries) {
                    if( entry.getEmail().matches(pattern) ) {
                        System.out.print(entry);
                        found = true;
                    }
                }
                break;
            default:
                break;
        }
        if(found){
            System.out.println();
        } else {
            System.out.println("No entries contain your search.\n");
        }
    }

    private void save() {
        try {
            StringBuilder data = new StringBuilder();
            for ( Entry entry : entries ) {
                data.append(entry.getFirstName()).append(" ").append(entry.getLastName()).append(" ")
                        .append(entry.getPhoneNumber()).append(" ").append(entry.getEmail()).append("\n");
            }
            PrintWriter writer = new PrintWriter(myAddressBook);
            writer.print(data.toString());
            writer.close();
        } catch( FileNotFoundException e ) {
            System.out.println("Error saving address book.");
        }
    }

    @Override
    public String toString() {
        if(entries.size() == 0) {
            return "Address Book is empty.\n";
        }
        return "Address Book: \n" + Arrays.toString(entries.toArray())
                .replace("[", "")
                .replace(", ","")
                .replace("]", "");
    }
}
