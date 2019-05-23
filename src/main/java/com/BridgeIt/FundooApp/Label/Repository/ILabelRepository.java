package com.BridgeIt.FundooApp.Label.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.BridgeIt.FundooApp.Label.Model.Label;
//import com.BridgeIt.FundooApp.Note.Model.Note;
@Repository
public interface ILabelRepository  extends MongoRepository<Label, String >{
	Optional<Label> findByLabelId(String noteId);
	Optional<Label> findByLabelIdAndUserId(String lableId,String userId);
	List<Label> findByUserId(String userId);
}
