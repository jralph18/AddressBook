package com.tts;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        AddressBook addressBook = new AddressBook();
        Scanner input = new Scanner(System.in);

        while(true) {
            System.out.println("Address Book Operations");
            System.out.println("""
                    1) Add an entry
                    2) Remove an entry
                    3) Search for an entry
                    4) Print Address Book
                    5) Clear Address Book
                    6) Quit""");
            System.out.print("Choose an action (Enter 1-6): ");
            String choice = input.nextLine();
            System.out.println();

            switch (choice) {
                // Add an entry to the address book
                case "1":
                    Pattern pattern;
                    Entry newEntry = new Entry();
                    System.out.println("Entry to add:");

                    System.out.print("First Name: ");
                    newEntry.setFirstName(input.nextLine());

                    System.out.print("Last Name: ");
                    newEntry.setLastName(input.nextLine());

                    // Get user input for contact phone number and check phone number for valid format
                    System.out.print("Phone Number: ");
                    String phoneNumber = input.nextLine();
                    pattern = Pattern.compile("[0-9]{3}-?[0-9]{3}-?[0-9]{4}");
                    Matcher matcher = pattern.matcher(phoneNumber);
                    while(!matcher.find()) {
                        System.out.print("Please enter a Phone Number with format XXX-XXX-XXXX: ");
                        phoneNumber = input.nextLine();
                        matcher = pattern.matcher(phoneNumber);
                    }
                    newEntry.setPhoneNumber(phoneNumber.replace("-", ""));

                    System.out.print("Email: ");
                    String email = input.nextLine();
                    pattern = Pattern.compile("\\S+@\\S+[.]\\S{3}");
                    matcher = pattern.matcher(email);
                    while(!matcher.find()) {
                        System.out.print("Please enter an Email with format local-part@domain.xxx: ");
                        email = input.nextLine();
                        matcher = pattern.matcher(email);
                    }
                    newEntry.setEmail(email);

                    addressBook.add(newEntry);
                    break;
                // Remove an entry from the address book by email address
                case "2":
                    System.out.print("Enter email address of entry to remove: ");
                    addressBook.removeByEmail(input.nextLine());
                    break;
                case "3":
                    String searchType;
                    while(true) {
                        System.out.println("Search By:");
                        System.out.println("""
                            1) First Name
                            2) Last Name
                            3) Phone Number
                            4) Email""");
                        System.out.print("Choose a search type (Enter 1-4): ");
                        searchType = input.nextLine();
                        if(searchType.equals("1") || searchType.equals("2") || searchType.equals("3") || searchType.equals("4")) break;
                        System.out.println("Invalid search type.");
                    }
                    System.out.print("Enter your search: ");
                    addressBook.search(searchType, input.nextLine());
                    break;
                case "4":
                    System.out.println(addressBook);
                    break;
                case "5":
                    addressBook.deleteAll();
                    System.out.println("Address Book cleared!\n");
                    break;
                case "6":
                    System.out.println("Quitting application...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1-6.");
            }
        }
    }

}
