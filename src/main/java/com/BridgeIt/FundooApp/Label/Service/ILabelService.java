package com.BridgeIt.FundooApp.Label.Service;


import java.util.List;

import com.BridgeIt.FundooApp.Label.Dto.LabelDto;
import com.BridgeIt.FundooApp.user.Model.Response;

public interface ILabelService {
	Response createLable(String token,LabelDto labelDto );
	Response updateLable(LabelDto labelDto ,String lableId,String token);
	Response deleteLable(String token, String lableId);
	List<LabelDto> getLabel(String token);


}
