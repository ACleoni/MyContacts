package com.alexandercleoni;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ContactList {
    private List<Contact> contactList = new ArrayList<>();

    private Optional<Contact> activeContact;

    public void createNewContact(String name, String phoneNumber, String email) {
        Contact contact = new Contact(name, phoneNumber, email);
        if (contactList.contains(find(name))) {
            System.out.println("Contact already exists.");
        } else {
            contactList.add(contact);
            System.out.print("Contact saved.");
        }
    }

//    public void updateExistingContact(String name) {
//        activeContact = name;
//
////        activeContact = Optional.ofNullable(findContact(name));
//    }

    public void removeContact(String name) {
        contactList.remove(find(name));
    }

    public boolean onFile(String name) {
        activeContact = Optional.ofNullable(find(name));
        return activeContact.isPresent();
    }

    public void printContactList() {
        for (Contact contact : contactList) {
            System.out.print(contactList.indexOf(contact) + 1 + " - ");
            System.out.print("Name: " + contact.getName());
            System.out.print("\tPhone Number: " + contact.getPhoneNumber());
            System.out.print("\tEmail: " + contact.getEmail());
            System.out.println("\n");
        }
    }

    public void searchContact(String name) {
        Optional<Contact> contact = Optional.ofNullable(find(name));
        contact.ifPresentOrElse(this::displayContact, () -> System.out.println("Contact not found in list."));
    }

    private void displayContact(Contact contact) {
        System.out.println("Contact Found");
        System.out.println("Name: " + contact.getName());
        System.out.println("Phone Number: " + contact.getPhoneNumber());
        System.out.println("Email: " + contact.getEmail());
    }

    public void writeContactList() throws ParserConfigurationException, TransformerException {
        String filepath = "contactlist.xml";

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("ContactList");
        document.appendChild(root);

        for (Contact c: contactList) {

            Element contact = document.createElement("Contact");
            root.appendChild(contact);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(c.getName()));
            contact.appendChild(name);

            Element phoneNumber = document.createElement("phone_number");
            phoneNumber.appendChild(document.createTextNode(c.getPhoneNumber()));
            contact.appendChild(phoneNumber);

            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(c.getEmail()));
            contact.appendChild(email);

            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result =  new StreamResult(new File(filepath));
            transformer.transform(source, result);
        }
        System.out.println("Done");
    }

    private Contact find(String name) {
        return contactList
                .stream()
                .filter(contact -> name.equalsIgnoreCase(contact.getName()))
                .findAny()
                .orElse(null);
    }
}
