package com.example.GuestBook.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.GuestBook.model.User;
import com.example.GuestBook.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/user")
	public String index(Model model) {
		model.addAttribute("users", userService.findAll());
		return "list";
	}

	@GetMapping("/user/create")
	public String create(Model model) {
		model.addAttribute("user", new User());
		return "form";
	}
	
	@GetMapping("/user/{id}/edit")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("user", userService.findOne(id));
		return "form";
	}
	
	@PostMapping("/user/save")
	public String save(@Valid User user, BindingResult result, RedirectAttributes redirect) {
		//logger.info(user.getUserName() + " : " + user.getEmail());
		if (result.hasErrors()) {
			return "form";
		}
		userService.save(user);
		redirect.addFlashAttribute("success", "Saved user successfully!");
		return "redirect:/user";
	}
	
	@GetMapping("/user/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		userService.delete(id);
		redirect.addFlashAttribute("success", "Deleted user successfully!");
		return "redirect:/user";
	}
	
}
