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
public class SearchController {

	private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);

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
	@RequestMapping(value = "/search", method = RequestMethod.POST)
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
	 * @return the string
	 */
	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
	public String handleSearch(Model model, @PathVariable String keyword) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Start handling restful search request with keyword : " + keyword);
		}

		model.addAttribute("keyword", keyword);

		List<Repository> repositories = gitSearchConnector.searchRepositoryByKeyword(keyword);

		if (repositories.isEmpty()) {
			model.addAttribute("noResult", true);
		} else {
			model.addAttribute("repositories", repositories);
		}
		if (LOG.isInfoEnabled()) {
			LOG.info("Done handling restful search request with keyword : " + keyword);
		}

		return "search";
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
	@RequestMapping(value = "/autocomplete/{keyword}", method = RequestMethod.GET)
	public String handleAutocomplete(Model model, @PathVariable String keyword) throws IOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("Handling autocomplete with keyword : " + keyword);
		}

		List<String> repositories = gitSearchConnector.searchRepositoryNames(keyword, 5);
		String result = objectMapper.writeValueAsString(repositories);

		if (LOG.isDebugEnabled()) {
			LOG.debug("Done handling autocomplete with keyword : " + keyword + ". returning : " + result);
		}

		return objectMapper.writeValueAsString(repositories);
	}

}
