package com.BridgeIt.FundooApp.Note.Servise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BridgeIt.FundooApp.Note.Model.Note;
import com.BridgeIt.FundooApp.Utility.ITokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SeviceElasticSearchImpl implements IServiceElasticSearch {
	String INDEX = "es";
	String TYPE = "createnote";
	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ITokenGenerator tokenservice;

	@Override
	public String createNote(Note note) throws IOException {
		@SuppressWarnings("unchecked")
		Map<String, Object> documentMappper = objectMapper.convertValue(note, Map.class);

		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, note.getNoteId()).source(documentMappper);
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		return indexResponse.getResult().name();
	}

	@Override
	public Note findById(String id) throws Exception {

		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);

		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();

		return objectMapper.convertValue(resultMap, Note.class);

	}

	@Override
	public String upDateNote(Note note) throws Exception {
		Note notes = findById(note.getNoteId());

		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, notes.getNoteId());

		@SuppressWarnings("unchecked")
		Map<String, Object> mapDoc = objectMapper.convertValue(note, Map.class);
		updateRequest.doc(mapDoc);

		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
		return updateResponse.getResult().name();
	}

	@Override
	public String deleteNote(String id) throws IOException {

		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);

		return response.getResult().name();
	}

	private List<Note> getSearchResult(SearchResponse searchResponse) {
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		List<Note> notesDoc = new ArrayList<Note>();
		if (searchHits.length > 0) {
			Arrays.stream(searchHits)
					.forEach(hit -> notesDoc.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class)));
		}

		return notesDoc;

	}

	@Override
	public List<Note> searchByTitle(String title, String token) throws IOException {
		String userId = tokenservice.verifyToken(token);
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("*", title))
				.filter(QueryBuilders.termsQuery("userId", userId));
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryBuilder);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = null;

		response = client.search(searchRequest, RequestOptions.DEFAULT);

		return getSearchResult(response);
	}

}
