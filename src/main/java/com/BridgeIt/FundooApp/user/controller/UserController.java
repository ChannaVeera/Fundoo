package com.BridgeIt.FundooApp.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BridgeIt.FundooApp.response.Response;
import com.BridgeIt.FundooApp.user.Dto.ForgetPasswordDto;
import com.BridgeIt.FundooApp.user.Dto.LoginDto;
import com.BridgeIt.FundooApp.user.Dto.UserDto;

import com.BridgeIt.FundooApp.user.Service.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserServiceImpl userServiceImpl;

	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDto userDto,
			HttpServletRequest httpServletRequest) {
		String messege = userServiceImpl.registeruser(userDto, httpServletRequest);
		Response response = new Response(HttpStatus.OK.value(), messege, null);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/activation/{token}")
	public ResponseEntity<Response> validateUser(@Valid @PathVariable String token) {
		String messege = userServiceImpl.validateMail(token);
		Response response = new Response(HttpStatus.OK.value(), messege, "");
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@Valid @RequestBody LoginDto loginDto) {
		String token = userServiceImpl.loginuser(loginDto);
		Response response = new Response(HttpStatus.OK.value(),"User loggedin successfully", token);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@Valid @RequestParam String emailId) {
		String messege = userServiceImpl.forgotpassword(emailId);
		Response response = new Response(HttpStatus.OK.value(), messege, "");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/restPassword/{token}")
	public ResponseEntity<Response> restpassword(@Valid @RequestHeader String token,
			@RequestBody ForgetPasswordDto forgetPasswordDto) {
		String messege = userServiceImpl.resetpassword(token, forgetPasswordDto);
		Response response = new Response(HttpStatus.OK.value(), messege, "");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
