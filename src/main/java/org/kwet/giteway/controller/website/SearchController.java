package org.kwet.giteway.controller.website;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.kwet.giteway.dao.GitSearchConnector;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.SearchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Search view Controller.
 * 
 * @author a.couette
 * 
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {

	private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);

	private static final int EXTRA_RESULT_START = 10;

	private static final int SUGGEST_COUNT = 5;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private GitSearchConnector gitSearchConnector;

	/**
	 * Handle the search form post request Redirects the user to the restful search URL
	 * 
	 * @param model
	 * @param keyword : the search keyword
	 * @param type : the search type (keyword or username)
	 * @return the redirect url
	 * @throws URISyntaxException 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String handleForm(Model model, @RequestParam String keyword, @RequestParam String type) throws URISyntaxException {
		if (LOG.isInfoEnabled()) {
			LOG.info("Handling search form request  keyword: " + keyword + " | type: " + type);
		}
		URI uri = new URI("redirect:/search/" + type + "/" + keyword);
		return uri.toASCIIString();
	}

	/**
	 * handleSearch restful get call to url : /search/{keyword} Sets as request attributes : 1. The
	 * repository list matching the keyword
	 * 
	 * @param model
	 * @param keyword : the search keyword
	 * @param type : the search type (keyword or username)
	 * @return the search view
	 */
	@RequestMapping(value = "/{type}/{keyword}", method = RequestMethod.GET)
	public String handleSearch(Model model, @PathVariable String type, @PathVariable String keyword) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Start handling restful search request with keyword: " + keyword + " | type: " + type);
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("type", type);

		List<Repository> repositories = null;
		switch (SearchType.getSearchType(type)) {
		case OWNER:
			repositories = gitSearchConnector.searchRepositoryByOwner(keyword);
			break;
		case KEYWORD:
			repositories = gitSearchConnector.searchRepositoryByKeyword(keyword);
			break;

		default:
			break;
		}

		if (repositories.isEmpty()) {
			model.addAttribute("noResult", true);
		} else {
			if (repositories.size() > EXTRA_RESULT_START) {
				repositories = repositories.subList(0, EXTRA_RESULT_START);
				model.addAttribute("extraReposAvailable", true);
			}
			model.addAttribute("repositories", repositories);
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("Done handling restful search request with keyword : " + keyword);
		}

		return "search";
	}

	/**
	 * handleSearch ajax call to url : /search/extra/{keyword}
	 * 
	 * @param model
	 * @param keyword : the search keyword
	 * @param type : the search type (keyword or username)
	 * @return the serialized extra repos
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/extra/{type}/{keyword}", method = RequestMethod.GET)
	@ResponseBody
	public String handleExtraSearch(Model model, @PathVariable String type, @PathVariable String keyword) throws IOException {
		if (LOG.isInfoEnabled()) {
			LOG.info("Handling restful extra search request with keyword : " + keyword);
		}

		List<Repository> repositories = null;
		switch (SearchType.getSearchType(type)) {
		case OWNER:
			repositories = gitSearchConnector.searchRepositoryByOwner(keyword);
			break;
		case KEYWORD:
			repositories = gitSearchConnector.searchRepositoryByKeyword(keyword);
			break;

		default:
			break;
		}
		if (repositories.size() > EXTRA_RESULT_START) {
			repositories = repositories.subList(EXTRA_RESULT_START, repositories.size());
			return objectMapper.writeValueAsString(repositories);
		}

		return "";
	}

	/**
	 * Handles Ajax request performed to make suggestions about what to search for
	 * 
	 * @param model
	 * @param term : a few letter that the user has typed in the search box
	 * @param type : the search type (keyword or username)
	 * @return a list of repository names starting with the keyword letters
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/autosuggest/{type}", method = RequestMethod.GET)
	public String handleAutosuggest(Model model, @PathVariable String type, @RequestParam String term) throws IOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Handling autocomplete with term : " + term);
		}

		List<String> suggestions = null;
		switch (SearchType.getSearchType(type)) {
		case OWNER:
			suggestions = gitSearchConnector.searchUserNames(term, SUGGEST_COUNT);
			break;
		case KEYWORD:
			suggestions = gitSearchConnector.searchRepositoryNames(term, SUGGEST_COUNT);
			break;

		default:
			break;
		}
		
		String result = objectMapper.writeValueAsString(suggestions);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Done handling autosuggest with term : " + term + ". returning : " + result);
		}

		return objectMapper.writeValueAsString(suggestions);
	}

}
