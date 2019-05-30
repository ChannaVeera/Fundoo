package com.BridgeIt.FundooApp.ElasticSearch;

import java.io.IOException;


import org.springframework.stereotype.Service;

import com.BridgeIt.FundooApp.Note.Model.Note;



@Service
public interface IServiceElasticSearch {
	public String createNote(Note note) throws IOException;
	public Note findById(String id) throws Exception;
	public String upDateNote(Note note) throws Exception ;
	public String deleteNote(String id) throws IOException ;
}