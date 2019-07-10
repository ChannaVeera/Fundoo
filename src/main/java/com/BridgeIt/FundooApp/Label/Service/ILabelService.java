package com.BridgeIt.FundooApp.Label.Service;


import java.util.List;

import com.BridgeIt.FundooApp.Label.Dto.LabelDto;
import com.BridgeIt.FundooApp.Label.Model.Label;

public interface ILabelService {
	String  createLable(String token,LabelDto labelDto );
	String updateLable(LabelDto labelDto ,String lableId,String token);
	String deleteLable(String token, String lableId);
	List<Label> getLabel(String token);


}
