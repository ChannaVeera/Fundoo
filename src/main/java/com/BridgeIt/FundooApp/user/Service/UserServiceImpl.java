package com.BridgeIt.FundooApp.user.Service;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.BridgeIt.FundooApp.Utility.Email;

import com.BridgeIt.FundooApp.Utility.IPasswordEncrypt;
import com.BridgeIt.FundooApp.Utility.ITokenGenerator;
import com.BridgeIt.FundooApp.Utility.MailService;

import com.BridgeIt.FundooApp.Utility.Utility;
import com.BridgeIt.FundooApp.exception.UserException;
import com.BridgeIt.FundooApp.user.Dto.ForgetPasswordDto;
import com.BridgeIt.FundooApp.user.Dto.LoginDto;
import com.BridgeIt.FundooApp.user.Dto.UserDto;
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
	private ITokenGenerator tokengenerator;
	@Autowired
	private MailService emailSender;
	@Autowired
	private IPasswordEncrypt encryptUtil;
	@Autowired
	private Environment environment;

	@Override
	public String registeruser(UserDto userDto, HttpServletRequest request) {
		System.out.println(request);
		Email email = new Email();
		boolean isemail = userRepository.findByEmailId(userDto.getEmailId()).isPresent();

		if (isemail) {
			throw new UserException(environment.getProperty("user.register.failuer"));

		} else {
			User user = modelMapper.map(userDto, User.class);
			String token = tokengenerator.generateToken(user.getUserId());
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
				email.setBody(emailSender.getlink("http://localhost:9090/users/activation/", status.getUserId()));

			} catch (Exception e) {

			}
			emailSender.send(email);
			return environment.getProperty("user.register.success");

		}

	}

	public String validateMail(String token) {
		String id = tokengenerator.verifyToken(token);
		User user = userRepository.findById(id).orElseThrow(() -> new UserException("user.activate.unsuccess"));
		user.setVarified(true);
		user.setUpdateStamp(Utility.todayDate());
		userRepository.save(user);
		return environment.getProperty("user.activate.success");
	}

	@Override
	public String loginuser(LoginDto loginDto) {
		String token = null;
		User user = userRepository.findByEmailId(loginDto.getEmailId())
				.orElseThrow(() -> new UserException(environment.getProperty("user.login.unsuccess")));
		boolean ispassword = encryptUtil.ispassword(loginDto, user);
		if (ispassword && user.isVarified()) {
			token = tokengenerator.generateToken(user.getUserId());
			user.setUpdateStamp(Utility.todayDate());
			userRepository.save(user);
			return token;
		} else {
			throw new UserException(environment.getProperty("user.valid.password"));
		}

	}

	@Override
	public String forgotpassword(String emailId) {
		Email email = new Email();
		User user = userRepository.findByEmailId(emailId)
				.orElseThrow(() -> new UserException(environment.getProperty("user.forget.password.fail")));
		email.setEmailId("nk8790525589@gmail.com");
		email.setTo(emailId);
		email.setSubject("ChangeLink");
		try {
			email.setBody(emailSender.getlink("http://localhost:9090/users/restPassword/", user.getUserId()));
		} catch (Exception e) {
		}
		emailSender.send(email);
		return environment.getProperty("user.forget.password");

	}

	@Override
	public String resetpassword(String token, ForgetPasswordDto forgotPassword) {
		String id = tokengenerator.verifyToken(token);
		User user = userRepository.findById(id).orElseThrow(() -> new UserException("user.restpassword.changeInfo"));
		user.setPassword(encryptUtil.encryptPassword(forgotPassword.getPassword()));
		user.setUpdateStamp(Utility.todayDate());
		userRepository.save(user);
		return environment.getProperty("user.restpassword.change");
	}
}
