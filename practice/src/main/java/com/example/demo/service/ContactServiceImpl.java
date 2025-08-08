package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.form.EditContactForm;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public void saveContact(ContactForm contactForm) {

		Contact contact = new Contact();

		contact.setLastName(contactForm.getLastName());
		contact.setFirstName(contactForm.getFirstName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhone(contactForm.getPhone());
		contact.setZipCode(contactForm.getZipCode());
		contact.setAddress(contactForm.getAddress());
		contact.setBuildingName(contactForm.getBuildingName());
		contact.setContactType(contactForm.getContactType());
		contact.setBody(contactForm.getBody());
		contactRepository.save(contact);
	}

	@Override
	public List<Contact> getAllContact() {
		return contactRepository.findAll();
	}

	@Override
	public Contact getDetailContact(Long id) {
		return contactRepository.findById(id).orElse(null);
	}

	@Override
	public Contact getEdit(Long id) {
		Contact contact = contactRepository.findById(id).orElse(null);

		Contact edit = new Contact();

		edit.setId(contact.getId());
		edit.setLastName(contact.getLastName());
		edit.setFirstName(contact.getFirstName());
		edit.setEmail(contact.getEmail());
		edit.setPhone(contact.getPhone());
		edit.setZipCode(contact.getZipCode());
		edit.setAddress(contact.getAddress());
		edit.setBuildingName(contact.getBuildingName());
		edit.setContactType(contact.getContactType());
		edit.setBody(contact.getBody());

		return edit;
	}

	@Override
	public void updateContact(EditContactForm editContactForm) {

		Contact contact = contactRepository.findById(editContactForm.getId())
				.orElseThrow(() -> new RuntimeException("IDが見つかりませんでした"));
		contact.setLastName(editContactForm.getLastName());
		contact.setFirstName(editContactForm.getFirstName());
		contact.setEmail(editContactForm.getEmail());
		contact.setPhone(editContactForm.getPhone());
		contact.setZipCode(editContactForm.getZipCode());
		contact.setAddress(editContactForm.getAddress());
		contact.setBuildingName(editContactForm.getBuildingName());
		contact.setContactType(editContactForm.getContactType());
		contact.setBody(editContactForm.getBody());

		contactRepository.save(contact);
	}

	@Override
	public void delete(Long id) {
		contactRepository.deleteById(id);
	}
}
