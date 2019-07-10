package com.BridgeIt.FundooApp.user.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.BridgeIt.FundooApp.Utility.ITokenGenerator;
import com.BridgeIt.FundooApp.exception.UserException;
import com.BridgeIt.FundooApp.user.Model.User;
import com.BridgeIt.FundooApp.user.Respository.IUserRespository;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonService {
	@Autowired
	IUserRespository userRespository;
	@Autowired
	private Environment environment;
	@Autowired
	private ITokenGenerator tokenGenerator;

	private AmazonS3 s3client;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@SuppressWarnings("deprecation")
	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);

	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(
				new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	public String uploadFile(MultipartFile multipartFile, String token) throws IOException {
		String id = tokenGenerator.verifyToken(token);
		Optional<User> optionaluser = userRespository.findByUserId(id);
		 return optionaluser.filter(user->{
			return user!=null;
		}).map(user->{
		
			String fileUrl = "";
			File file=null;
			try {
				file = convertMultiPartToFile(multipartFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String fileName = generateFileName(multipartFile);
			fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
			uploadFileTos3bucket(fileName, file);
			file.delete();
			user.setImage(fileUrl);
			userRespository.save(user);
			return environment.getProperty("aws.propic.successfull");
		}).orElseThrow(()-> new UserException(environment.getProperty("aws.propic.successfull")));
//		if (optionaluser.isPresent()) {
//			User user = optionaluser.get();
//			String fileUrl = "";
//			File file = convertMultiPartToFile(multipartFile);
//			String fileName = generateFileName(multipartFile);
//			fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
//			uploadFileTos3bucket(fileName, file);
//			file.delete();
//			user.setImage(fileUrl);
//			userRespository.save(user);
//			Response response = ResponceUtilty.getResponse(200, token,
//					environment.getProperty("aws.propic.successfull"));
//			return response;
//		}
//		Response response = ResponceUtilty.getResponse(204, "", environment.getProperty("aws.propic.successfull"));
//		return response;

	}

	public String deleteFileFromS3Bucket(String fileName, String token) {


		String id = tokenGenerator.verifyToken(token);
		Optional<User> optionalUser = userRespository.findByUserId(id);
	 return 	optionalUser.filter(user->{
			return user!=null;
		}).map(user->{
			s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
			user.setImage(null);
			userRespository.save(user);
			return  environment.getProperty("aws.propic.delete.successfull");
		}).orElseThrow(()-> new UserException(environment.getProperty("aws.propic.delete.failuer")));
	}
//		if (optionalUser.isPresent()) {
//			User user = optionalUser.get();
//			s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
//			user.setImage(null);
//			userRespository.save(user);
//			Response response = ResponceUtilty.getResponse(200, token, environment.getProperty("aws.propic.delete.successfull"));
//			return response;
//		}
//		Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("aws.propic.delete.failuer"));
//		return response;
//	}

}