package com.cemilanku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cemilanku.model.Contact;
import com.cemilanku.service.ContactService;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    // Mendapatkan semua kontak
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    // Mendapatkan kontak berdasarkan id
    @GetMapping("/{id}")
    public Contact getContactById(@PathVariable String id) {
        return contactService.getContactById(id);
    }

    // Menambahkan kontak
    @PostMapping
    public Contact addContact(@RequestBody Contact contact) {
        return contactService.addContact(contact); // Menambahkan kontak, termasuk order jika ada
    }

    // Memperbarui kontak
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable String id, @RequestBody Contact contact) {
        return contactService.updateContact(id, contact); // Memperbarui kontak
    }

    // Menghapus kontak berdasarkan id
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable String id) {
        contactService.deleteContact(id); // Menghapus kontak
    }
}