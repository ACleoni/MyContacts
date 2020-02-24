package com.alexandercleoni;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ContactList contactList = new ContactList();

    public static void main(String[] args) {
        boolean quit = false;
        int choice = 0;

        printInstructions();

        while(!quit) {
            System.out.println("Enter your choice: ");

            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            scanner.nextLine();

            switch (choice) {
                case 0:
                    printInstructions();
                    break;
                case 1:
                    contactList.printContactList();
                    break;
                case 2:
                    addContact();
                    break;
                case 3:
                    updateExistingContact();
                    break;
                case 4:
                    removeContact();
                    break;
                case 5:
                    searchContact();
                    break;
                case 6:
                    writeContactList();
                    break;
                case 7:
                    quit = true;
                    break;
                default:
                    System.out.println(choice + " is not a valid selection. Please try again.");
            }
        }
    }

    public static void addContact() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        String email = null;

        boolean validEmailSelection = false;
        while (!validEmailSelection) {
            System.out.println("Would you like to enter an email for this contact? y/n ");
            String emailSelection = scanner.nextLine();

            Hashtable<String, Boolean> userInput = getUserInput(emailSelection);

            if (userInput.get("selection") && userInput.get("isValidSelection")){
                System.out.print("Enter Email: ");
                email = scanner.nextLine();
                validEmailSelection = true;
            } else if (!userInput.get("selection") && userInput.get("isValidSelection")) {
                email = "n/a";
                validEmailSelection = true;
            } else {
                System.out.println("That is not a valid selection. Please try again.");
            }
        }

        contactList.createNewContact(name, phoneNumber, email);
        scanner.nextLine();
    }

    public static void updateExistingContact() {
        System.out.println("Enter contact name: ");
        String name = scanner.nextLine();
        boolean contactExists = contactList.onFile(name);
        if (contactExists) {
            String newName;
            String newPhoneNumber;
            String newEmail;


            System.out.println("Would you like to change the name? y/n ");
            String emailSelection = scanner.nextLine();

        }
    }

    public static void removeContact() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        contactList.removeContact(name);
    }

    public static Hashtable<String, Boolean> getUserInput (String selection) {
        Hashtable<String, Boolean> userInput = new Hashtable<>();

        if (selection.equalsIgnoreCase("y") ||
                selection.equalsIgnoreCase("ye") ||
                selection.equalsIgnoreCase("yes")) {
            userInput.put("selection", true);
            userInput.put("isValidSelection", true);
        } else if (selection.equalsIgnoreCase("n") ||
                    selection.equalsIgnoreCase("no")) {
            userInput.put("selection", false);
            userInput.put("isValidSelection", true);
        } else {
            userInput.put("selection", false);
            userInput.put("isValidSelection", false);
        }
        return userInput;
    }

    public static void searchContact() {
        System.out.println("Enter contact name: ");
        String name = scanner.nextLine();
        contactList.searchContact(name);
    }

    public static void writeContactList() {
        try {
            contactList.writeContactList();
        } catch (TransformerException | ParserConfigurationException e) {
            System.out.println("Error occured. Could not save contacts to file.");
            e.printStackTrace();
        }
    }

    public static void printInstructions() {
        System.out.println("\nPress ");
        System.out.println("\t 0 - To print the choice options.");
        System.out.println("\t 1 - To print the list of contacts.");
        System.out.println("\t 2 - To add a contact.");
        System.out.println("\t 3 - To update a contact.");
        System.out.println("\t 4 - To remove a contact");
        System.out.println("\t 5 - To search for a contact.");
        System.out.println("\t 6 - To quit the application.");
    }
}
