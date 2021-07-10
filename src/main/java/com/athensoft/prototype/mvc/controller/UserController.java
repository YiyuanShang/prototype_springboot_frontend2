package com.athensoft.prototype.mvc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.athensoft.prototype.mvc.entity.User;
import com.athensoft.prototype.mvc.service.UserService;

@Controller
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user/signup")
	public String gotoUserSignUp(User user) {
		return "user/user_create";
	}

	@GetMapping("/user/list")
	public String gotoUserList(Model model) {
		LOGGER.debug("entering /user/list");
		ResponseEntity<List<User>> response = userService.getUserListAll();

		List<User> userList = response.getBody();
		LOGGER.debug("returning user list:" + userList);
		model.addAttribute("userList", userList);

		return "user/user_list";
	}

	@GetMapping("/user/edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		LOGGER.debug("entering showUpdateForm id:" + id);
		int userId = Integer.valueOf(id);
		ResponseEntity<User> response = userService.getUserById(userId);
		User user = response.getBody();

		model.addAttribute("user", user);
		return "user/user_edit";
	}

	@GetMapping("/user/delete/{id}")
	public String showDeleteForm(@PathVariable("id") String id, Model model) {
		LOGGER.debug("entering showDeleteForm id:" + id);
		int userId = Integer.valueOf(id);
		ResponseEntity<User> response = userService.getUserById(userId);
		User user = response.getBody();

		model.addAttribute("user", user);
		return "user/user_delete";
	}

	@PostMapping("/user/add")
	public String addUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/user_create";
		}
		ResponseEntity<User> response = userService.createUser(user);

		if (response.getStatusCode() != HttpStatus.CREATED) {
			return "/user/signup";
		}
		return "redirect:/user/list";
	}

	@PostMapping("/user/update")
	public String updateUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			int userId = user.getUserId();
			user.setUserId(userId);
			return "user/user_edit";
		}

		LOGGER.info("update user:" + user);
		ResponseEntity<User> response = userService.updateUser(user);

		if (response.getStatusCode() != HttpStatus.OK) {
			return "/user/edit/" + user.getUserId();
		}
		return "redirect:/user/list";
	}

	@PostMapping("/user/delete")
	public String deleteUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			int userId = user.getUserId();
			user.setUserId(userId);
			return "user/user_delete";
		}

		LOGGER.info("delete user:" + user);
		ResponseEntity<User> response = userService.deleteUser(user);

		if (response.getStatusCode() != HttpStatus.OK) {
			return "/user/delete/" + user.getUserId();
		}
		return "redirect:/user/list";
	}

}
