package com.example.demo.controller;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.AdminForm;
import com.example.demo.form.EditContactForm;
import com.example.demo.service.AdminService;
import com.example.demo.service.ContactService;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private ContactService contactService;
	@Autowired
	private AdminService adminService;

	@GetMapping("/contacts")
	public String adminContact(Model model) {
		model.addAttribute("contactlist", contactService.getAllContact());
		return "contactList";
	}

	@GetMapping("/contacts/{id}")
	public String detailContact(@PathVariable Long id, Model model) {
		model.addAttribute("contactdetail", contactService.getDetailContact(id));
		return "ContactDetail";
	}

	@GetMapping("/contacts/{id}/edit")
	public String editContact(@PathVariable Long id, Model model) {
		model.addAttribute("editContactForm", contactService.getEdit(id));
		return "ContactEdit";
	}

	@PostMapping("/update")
	public String contact(@Validated @ModelAttribute("editContactForm") EditContactForm editContactForm,
			BindingResult errorResult) {

		if (errorResult.hasErrors()) {
			return "ContactEdit";
		}
		contactService.updateContact(editContactForm);
		return "redirect:/admin/contacts";
	}

	@GetMapping("/contacts/{id}/delete")
	@Transactional
	public String delete(@PathVariable Long id) {
		contactService.delete(id);
		return "redirect:/admin/contacts";
	}

	@GetMapping("/signup")
	public String admin(Model model) {
		model.addAttribute("adminForm", new AdminForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String signup(@Validated @ModelAttribute("adminForm") AdminForm adminForm, BindingResult errorResult) {

		if (errorResult.hasErrors()) {
			return "signup";
		}
		adminService.saveAdmin(adminForm);
		return "redirect:/admin/signin";
	}

	@GetMapping("/signin")
	public String signin(Model model) {
		model.addAttribute("adminForm", new AdminForm());
		return "signin";
	}
}
