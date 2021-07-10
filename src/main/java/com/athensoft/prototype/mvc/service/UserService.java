package com.athensoft.prototype.mvc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.athensoft.prototype.mvc.entity.User;

@Service
public class UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	public String API_SERVER;
	private final RestTemplate restTemplate;

	public UserService(@Value("${api.address}") String API_SERVER, RestTemplate restTemplate) {
		this.API_SERVER = API_SERVER;
		this.restTemplate = restTemplate;
	}

	public ResponseEntity<List<User>> getUserListAll() {
		String targetUrl = API_SERVER + "/users";
		ResponseEntity<List<User>> response = restTemplate.exchange(targetUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				});
		List<User> userList = response.getBody();
		LOGGER.debug("user list:" + userList);
		return response;
	}

	public ResponseEntity<User> getUserById(int userId) {
		String targetUrl = API_SERVER + "/users/" + userId;
		ResponseEntity<User> response = restTemplate.getForEntity(targetUrl, User.class);

		User user = response.getBody();
		LOGGER.debug("user:" + user);
		return response;
	}

	public ResponseEntity<User> createUser(User user) {
		String targetUrl = API_SERVER + "/users";
		HttpEntity<User> entity = new HttpEntity<>(user);
		ResponseEntity<User> response = restTemplate.postForEntity(targetUrl, entity, User.class);
		User createdUser = response.getBody();
		LOGGER.debug("user:" + createdUser);
		return response;
	}

	public ResponseEntity<User> updateUser(User user) {
		String targetUrl = API_SERVER + "/users";
		HttpEntity<User> entity = new HttpEntity<>(user);
		ResponseEntity<User> response = restTemplate.exchange(targetUrl, HttpMethod.PUT, entity, User.class);
		User updatedUser = response.getBody();
		LOGGER.debug("user:" + updatedUser);
		return response;
	}

	public ResponseEntity<User> deleteUserById(int userId) {
		String targetUrl = API_SERVER + "/users/" + userId;
		ResponseEntity<User> response = restTemplate.exchange(targetUrl, HttpMethod.DELETE, null, User.class);
		User deletedUser = response.getBody();
		LOGGER.debug("user:" + deletedUser);
		return response;

	}

	public ResponseEntity<User> deleteUser(User user) {
		String targetUrl = API_SERVER + "/users";
		HttpEntity<User> entity = new HttpEntity<>(user);
		ResponseEntity<User> response = restTemplate.exchange(targetUrl, HttpMethod.DELETE, entity, User.class);
		User deletedUser = response.getBody();
		LOGGER.debug("user:" + deletedUser);
		return response;

	}

}
