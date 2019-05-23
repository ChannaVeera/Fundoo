package com.BridgeIt.FundooApp.Label.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BridgeIt.FundooApp.Label.Dto.LabelDto;
import com.BridgeIt.FundooApp.Label.Service.LabelServiceImpl;
import com.BridgeIt.FundooApp.user.Model.Response;

@RestController
@RequestMapping("/lable")
public class LabelController {
	@Autowired
	private LabelServiceImpl labelServiceImpl;

	@PostMapping("/create")
	public ResponseEntity<Response> createLabel(@RequestHeader String token, @RequestBody LabelDto labelDto) {
		Response response = labelServiceImpl.createLable(token, labelDto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Response> updateLable(@RequestBody LabelDto labelDto, @RequestParam String lableId,@RequestHeader String token) {
		Response response = labelServiceImpl.updateLable(labelDto, lableId, token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteLable(@RequestHeader String token, @RequestParam String labelId) {
		Response response = labelServiceImpl.deleteLable(token, labelId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PostMapping("/getAll")
	public List<LabelDto> getlable(@RequestHeader String token) {
		List<LabelDto> lable = labelServiceImpl.getLabel(token);
		return lable;
	}

}
