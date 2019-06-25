package com.BridgeIt.FundooApp.user.Service;


import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.BridgeIt.FundooApp.Utility.EncryptUtil;
import com.BridgeIt.FundooApp.Utility.MailService;
import com.BridgeIt.FundooApp.Utility.ResponceUtilty;
import com.BridgeIt.FundooApp.Utility.TokenUtility;
import com.BridgeIt.FundooApp.Utility.Utility;
import com.BridgeIt.FundooApp.user.Dto.ForgetPasswordDto;

import com.BridgeIt.FundooApp.user.Dto.LoginDto;

import com.BridgeIt.FundooApp.user.Dto.UserDto;
import com.BridgeIt.FundooApp.user.Model.Email;
import com.BridgeIt.FundooApp.user.Model.Response;
import com.BridgeIt.FundooApp.user.Model.User;
import com.BridgeIt.FundooApp.user.Respository.IUserRespository;

@Component
@PropertySource("classpath:message.properties")
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserRespository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MailService emailSender;
	@Autowired
	private EncryptUtil encryptUtil;
	@Autowired
	private Environment environment;

	public Response registeruser(UserDto userDto, HttpServletRequest request) {
		System.out.println(request);
		Email email = new Email();
		boolean isemail = userRepository.findByEmailId(userDto.getEmailId()).isPresent();
		
		if (isemail) {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("user.register.failuer"));
			return response;
		} else {
			User user = modelMapper.map(userDto, User.class);
			String token = TokenUtility.generateToken(user.getUserId());
			user.setPassword(encryptUtil.encryptPassword(userDto.getPassword()));
			user.setToken(token);
			user.setRegisterStamp(Utility.todayDate());
			user.setUpdateStamp(Utility.todayDate());
			User status = userRepository.save(user);
			email.setEmailId("nk8790525589@gmail.com");
			email.setTo(user.getEmailId());
			email.setSubject("verification");
			email.setBody("Body");
			try {
				email.setBody(
						emailSender.getlink("http://localhost:9090/users/activation/",status.getUserId()));

			} catch (Exception e) {
				// TODO: handle exception
			}
			emailSender.send(email);

			Response response = ResponceUtilty.getResponse(200, token, environment.getProperty("user.register.success "));
			return response;
		}
		
	}

	public Response validateMail(String token) {
		String id = TokenUtility.verifyToken(token);
		Optional<User> user = userRepository.findById(id);
		//Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			user.get().setToken(token);
			user.get().setVarified(true);
			user.get().setUpdateStamp(Utility.todayDate());
			userRepository.save(user.get());
		Response response = ResponceUtilty.getResponse(200,"0", environment.getProperty("user.activate.success"));
		return response;
		}
		else {

			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("user.activate.unsuccess"));
			return response;
		}
		
	}



	@Override
	public Response loginuser(LoginDto loginDto) {
		boolean isemail = userRepository.findByEmailId(loginDto.getEmailId()).isPresent();
		{
			if (!isemail) {
				Response response = ResponceUtilty.getResponse(204, "0",environment.getProperty("user.login.unsuccess"));
				return response;
			}
			User user = userRepository.findByEmailId(loginDto.getEmailId()).get();
			//String token = TokenUtility.generateToken(user.getUserId());
			boolean ispassword = encryptUtil.ispassword(loginDto, user);
			if (!ispassword) {
				Response response = ResponceUtilty.getResponse(200, "0", environment.getProperty("user.login.success"));
				return response;
			}
			user.setUpdateStamp(Utility.todayDate());
			userRepository.save(user);
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("user.login.unsuccess"));
			return response;
		}

	}


	@Override
	public Response forgotpassword(LoginDto loginDto) {
		Email email = new Email();
		Optional<User> user = userRepository.findByEmailId(loginDto.getEmailId());
		if (user.isPresent()) {
			
			email.setEmailId("nk8790525589@gmail.com");
			email.setTo(loginDto.getEmailId());
			email.setSubject("ChangeLink");
			try {
				email.setBody(emailSender.getlink("http://localhost:9090/users/restPassword/", user.get().getToken()));

			} catch (Exception e) {
			}
			emailSender.send(email);
			Response response = ResponceUtilty.getResponse(200, "0",environment.getProperty("user.forget.password"));
			return response;
		} else {
			Response response = ResponceUtilty.getResponse(204, "0",environment.getProperty("user.forget.password.fail"));
			return response;
		}
		

	}

	@Override
	public Response resetpassword(String token, ForgetPasswordDto forgotPassword) {
		Response response = null;
		String id = TokenUtility.verifyToken(token);
		
		System.out.println(forgotPassword.getPassword());
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
	
				user.get().setPassword(encryptUtil.encryptPassword(forgotPassword.getPassword()));
				user.get().setUpdateStamp(Utility.todayDate());
				userRepository.save(user.get());
				response = ResponceUtilty.getResponse(200, token, environment.getProperty("user.restpassword.change"));
				return response;
		}
		else {
			
		
			response = ResponceUtilty.getResponse(500, "0", environment.getProperty("user.restpassword.changeInfo"));
		
		return response;
		}
	}
}
