package com.BridgeIt.FundooApp.Label.Service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.BridgeIt.FundooApp.Label.Dto.LabelDto;
import com.BridgeIt.FundooApp.Label.Model.Label;
import com.BridgeIt.FundooApp.Label.Repository.ILabelRepository;
import com.BridgeIt.FundooApp.Utility.ITokenGenerator;

import com.BridgeIt.FundooApp.Utility.Utility;
import com.BridgeIt.FundooApp.exception.LabelException;
import com.BridgeIt.FundooApp.exception.UserException;

import com.BridgeIt.FundooApp.user.Model.User;
import com.BridgeIt.FundooApp.user.Respository.IUserRespository;

@Service
public class LabelServiceImpl implements ILabelService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private Environment environment;
	@Autowired
	private ILabelRepository iLableRepository;
	@Autowired
	private ITokenGenerator iTokenGenerator;
	@Autowired
	private IUserRespository iUserRespository;

	@Override
	public String createLable(String token, LabelDto labelDto) {
		String id = iTokenGenerator.verifyToken(token);
		Optional<User> optionalUser = iUserRespository.findByUserId(id);
		return optionalUser.filter(user -> {
			return user != null;
		}).filter(label -> {
			return labelDto.getLableName() != null;
		}).map(user -> {
			Label label = modelMapper.map(labelDto, Label.class);
			label.setUserId(user.getUserId());
			label.setCreateTime(Utility.todayDate());
			label.setUpdateTime(Utility.todayDate());
			iLableRepository.save(label);
			return environment.getProperty("label.create.success");
		}).orElseThrow(() -> new UserException(environment.getProperty("user.not.found")));
}

	@Override
	public String updateLable(LabelDto labelDto, String lableId, String token) {
		String userid = iTokenGenerator.verifyToken(token);
		Optional<Label> optionalLabel = iLableRepository.findByLabelIdAndUserId(lableId, userid);
		return optionalLabel.filter(label -> {
			return label != null;
		}).map(label -> {
			label.setLableName(labelDto.getLableName());
			label.setUpdateTime(Utility.todayDate());
			iLableRepository.save(label);
			return environment.getProperty("label.update.success");
		}).orElseThrow(() -> new LabelException(environment.getProperty("label.notFound")));

	}

	@Override
	public String deleteLable(String token, String lableId) {
		String userId = iTokenGenerator.verifyToken(token);
		Optional<Label> optionalLable = iLableRepository.findByLabelIdAndUserId(lableId, userId);
		return optionalLable.filter(lable -> {
			return lable != null;
		}).map(lable -> {
			lable.setUpdateTime(Utility.todayDate());
			iLableRepository.delete(lable);
			return environment.getProperty("label.delete.success ");
		}).orElseThrow(() -> new LabelException(environment.getProperty("label.Unsuccessfull.delete")));

	}

	@Override
	public List<Label> getLabel(String token) {
		String userId = iTokenGenerator.verifyToken(token);
		List<Label> labels = iLableRepository.findByUserId(userId);
		return labels;
	}

}
