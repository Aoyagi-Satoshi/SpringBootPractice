package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.form.EditContactForm;

public interface ContactService {
	void saveContact(ContactForm contactForm);

	List<Contact> getAllContact();

	Contact getDetailContact(Long id);

	Contact getEdit(Long id);

	void updateContact(EditContactForm editContactForm);

	void delete(Long id);
}
