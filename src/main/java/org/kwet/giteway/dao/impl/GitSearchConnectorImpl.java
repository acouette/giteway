package org.kwet.giteway.dao.impl;

import static ch.lambdaj.Lambda.convert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.kwet.giteway.dao.GitSearchConnector;
import org.kwet.giteway.dao.converter.RepositoryConverter;
import org.kwet.giteway.model.Repository;
import org.springframework.stereotype.Component;

/**
 * The Class GitSearchConnectorImpl.
 * 
 * @author a.couette
 * 
 */
@Component
public class GitSearchConnectorImpl extends AbstractGitConnector implements GitSearchConnector {

	private static final String GET_REPOSITORIES_BY_KEYWORD = buildUrl("/legacy/repos/search/{keyword}");

	/**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<Repository> searchRepositoryByKeyword(String keyword) {
		Validate.notEmpty(keyword, "Keyword must be null and not empty");
		Map<String,List<Map<String, Object>>> reposMap = readValue(getGitHttpClient().executeGetRequest(GET_REPOSITORIES_BY_KEYWORD, keyword),Map.class);
		return convert(reposMap.get("repositories"), new RepositoryConverter());

	}

	/**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> searchRepositoryNames(String keyword, int limit) {
		Validate.notEmpty(keyword, "Keyword must be null and not empty");
		Map<String,List<Map<String, Object>>> reposMap = readValue(getGitHttpClient().executeGetRequest(GET_REPOSITORIES_BY_KEYWORD, keyword),Map.class);
		List<Repository> repoList = convert(reposMap.get("repositories"), new RepositoryConverter());
		
		List<String> res = new ArrayList<>();
		int i = 0;
		for (Repository gr : repoList) {
			if(i==limit){
				break;
			}
			if(gr.getName().startsWith(keyword) && !res.contains(gr.getName())){
				res.add(gr.getName());
				i++;
			}
		}
		Collections.sort(res);
		return res;
	}

}
