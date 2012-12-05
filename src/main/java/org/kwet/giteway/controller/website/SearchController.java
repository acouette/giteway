package org.kwet.giteway.controller.website;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.kwet.giteway.dao.GitSearchConnector;
import org.kwet.giteway.model.Repository;
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
@RequestMapping(value="/search")
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
	 * @return the redirect url
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String handleForm(Model model, @RequestParam String keyword) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Handling search form request with keyword : " + keyword);
		}
		return "redirect:/search/" + keyword;
	}

	/**
	 * handleSearch restful get call to url : /search/{keyword} Sets as request attributes : 1. The
	 * repository list matching the keyword
	 * 
	 * @param model
	 * @param keyword : the search keyword
	 * @return the search view
	 */
	@RequestMapping(value = "/{keyword}", method = RequestMethod.GET)
	public String handleSearch(Model model, @PathVariable String keyword) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Start handling restful search request with keyword : " + keyword);
		}

		model.addAttribute("keyword", keyword);

		List<Repository> repositories = gitSearchConnector.searchRepositoryByKeyword(keyword);

		if (repositories.isEmpty()) {
			model.addAttribute("noResult", true);
		} else {
			if(repositories.size()>EXTRA_RESULT_START){
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
	 * @return the serialized extra repos
	 * @throws IOException
	 */
	@RequestMapping(value = "/extra/{keyword}", method = RequestMethod.GET)
	@ResponseBody
	public String handleExtraSearch(Model model, @PathVariable String keyword) throws IOException {
		if (LOG.isInfoEnabled()) {
			LOG.info("Handling restful rextra search request with keyword : " + keyword);
		}
		List<Repository> repositories = gitSearchConnector.searchRepositoryByKeyword(keyword);

		if (repositories.size()>EXTRA_RESULT_START) {
			repositories = repositories.subList(EXTRA_RESULT_START, repositories.size());
			return objectMapper.writeValueAsString(repositories);
		}

		return "";
	}

	/**
	 * Handles Ajax request performed to make suggestions about what to search for
	 * 
	 * @param model
	 * @param keyword : a few letter that the user has typed in the search box
	 * @return a list of repository names starting with the keyword letters
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/autosuggest/{keyword}", method = RequestMethod.GET)
	public String handleAutosuggest(Model model, @PathVariable String keyword) throws IOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Handling autocomplete with keyword : " + keyword);
		}

		List<String> repositories = gitSearchConnector.searchRepositoryNames(keyword, SUGGEST_COUNT);
		String result = objectMapper.writeValueAsString(repositories);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Done handling autosuggest with keyword : " + keyword + ". returning : " + result);
		}

		return objectMapper.writeValueAsString(repositories);
	}

}
