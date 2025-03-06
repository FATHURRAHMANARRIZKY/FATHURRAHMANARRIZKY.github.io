package com.cemilanku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cemilanku.model.Contact;
import com.cemilanku.repository.ContactRepository;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    // Mengambil semua kontak
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // Mengambil kontak berdasarkan id
    public Contact getContactById(String id) {
        return contactRepository.findById(id).orElse(null);
    }

    // Menambahkan kontak dan order
    public Contact addContact(Contact contact) {
        return contactRepository.save(contact); // Menyimpan kontak dan platform (order) jika ada
    }

    // Memperbarui kontak
    public Contact updateContact(String id, Contact contact) {
        contact.setId(id);
        return contactRepository.save(contact); // Memperbarui kontak
    }

    // Menghapus kontak
    public void deleteContact(String id) {
        contactRepository.deleteById(id);
    }
}