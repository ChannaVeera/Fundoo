package com.BridgeIt.FundooApp.Label.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.BridgeIt.FundooApp.Label.Dto.LabelDto;
import com.BridgeIt.FundooApp.Label.Model.Label;
import com.BridgeIt.FundooApp.Label.Repository.ILabelRepository;
//import com.BridgeIt.FundooApp.Note.Dto.NoteDto;
//import com.BridgeIt.FundooApp.Note.Model.Note;
//import com.BridgeIt.FundooApp.Note.Respository.INoteRepository;
import com.BridgeIt.FundooApp.Utility.ResponceUtilty;
import com.BridgeIt.FundooApp.Utility.TokenUtility;
import com.BridgeIt.FundooApp.Utility.Utility;
import com.BridgeIt.FundooApp.user.Model.Response;
import com.BridgeIt.FundooApp.user.Model.User;
import com.BridgeIt.FundooApp.user.Respository.IUserRespository;

@Service
public class LabelServiceImpl implements ILabelService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private Environment environment;
//	@Autowired
//	private INoteRepository iNoteRepository;
	@Autowired
	private ILabelRepository iLableRepository;

	@Autowired
	private IUserRespository iUserRespository;

	@Override
	public Response createLable(String token, LabelDto labelDto) {
		String id = TokenUtility.verifyToken(token);
		Optional<User> user = iUserRespository.findByUserId(id);
		if (user.isPresent()) {

			User useris = iUserRespository.findById(id).get();
			Label label = modelMapper.map(labelDto, Label.class);
			label.setUserId(useris.getUserId());
			label.setCreateTime(Utility.todayDate());
			label.setUpdateTime(Utility.todayDate());
			iLableRepository.save(label);
			Response response = ResponceUtilty.getResponse(200, "", environment.getProperty("label.create.success"));
			return response;
		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("label.create.unsuccess"));
			return response;
		}
	}

	@Override
	public Response updateLable(LabelDto labelDto, String lableId, String token) {
		String userid = TokenUtility.verifyToken(token);
		Optional<Label> label = iLableRepository.findByLabelIdAndUserId(lableId, userid);
		if (label.isPresent()) {
			label.get().setLableName(labelDto.getLableName());
			label.get().setUpdateTime(Utility.todayDate());
			iLableRepository.save(label.get());
			Response response = ResponceUtilty.getResponse(200, " ", environment.getProperty("label.update.success"));
			return response;
		} else {
			Response response = ResponceUtilty.getResponse(204, "0", environment.getProperty("label.notFound"));
			return response;
		}
	}

	@Override
	public Response deleteLable(String token, String lableId) {
		String userId = TokenUtility.verifyToken(token);
		Optional<Label> lable = iLableRepository.findByLabelIdAndUserId(lableId, userId);
		if (lable.isPresent()) {
			lable.get().setUpdateTime(Utility.todayDate());
			iLableRepository.delete(lable.get());
			Response response = ResponceUtilty.getResponse(200, "", environment.getProperty("label.delete.success "));
			return response;
		} else {
			Response response = ResponceUtilty.getResponse(204, " ", environment.getProperty("label.Unsuccessfull.delete"));
			return response;
		}
	}

	@Override
	public List<LabelDto> getLabel(String token) {
		String userId = TokenUtility.verifyToken(token);
		List<Label> labels = iLableRepository.findByUserId(userId);
		List<LabelDto> labelList = new ArrayList<LabelDto>();
		for (Label label : labels) {
			LabelDto lableslist = modelMapper.map(label, LabelDto.class);
			labelList.add(lableslist);
			System.out.println(lableslist);
		}
		return labelList;
	}


}
