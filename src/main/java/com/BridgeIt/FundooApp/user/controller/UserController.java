package com.BridgeIt.FundooApp.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BridgeIt.FundooApp.user.Dto.ForgetPasswordDto;
import com.BridgeIt.FundooApp.user.Dto.LoginDto;
import com.BridgeIt.FundooApp.user.Dto.UserDto;
import com.BridgeIt.FundooApp.user.Model.Response;
import com.BridgeIt.FundooApp.user.Service.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserServiceImpl userServiceImpl;

	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody UserDto userDto, HttpServletRequest httpServletRequest) {
		Response response = userServiceImpl.registeruser(userDto, httpServletRequest);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/activation/{token}")
	public ResponseEntity<Response> validateUser(@PathVariable String token, HttpServletRequest httpServletRequest) {
		
		Response response = userServiceImpl.validateMail(token);
		System.out.println("re"+response);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) {
		Response response = userServiceImpl.loginuser(loginDto);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@RequestBody LoginDto loginDto) {
		Response response = userServiceImpl.forgotpassword(loginDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/restPassword/{token}")
	public ResponseEntity<Response> restpassword(@PathVariable String token,
			@RequestBody ForgetPasswordDto forgetPasswordDto) {
		Response response = userServiceImpl.resetpassword(token, forgetPasswordDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
