package org.kwet.giteway.controller.website;

import java.util.List;

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

/**
 * The Search view Controller.
 * 
 * @author Antoine Couette
 *
 */
@Controller
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	
	@Autowired
	private GitSearchConnector gitSearchConnector;

	/**
	 * Handle the search form post request
	 * Redirects the user to the restful search URL
	 *
	 * @param model
	 * @param keyword : the search keyword
	 * @return the redirect url
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String handleForm(Model model, @RequestParam String keyword) {
		logger.debug("Handlind form request with keyword : "+keyword);
		return "redirect:/search/" + keyword;
	}

	/**
	 * handleSearch restful get call to url : /search/{keyword}
	 * Sets as request attributes :
	 * 	1. The repository list matching the keyword
	 *
	 * @param model
	 * @param keyword : the search keyword
	 * @return the string
	 */
	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
	public String handleSearch(Model model, @PathVariable String keyword) {
		model.addAttribute("keyword", keyword);

		List<Repository> repositories = gitSearchConnector.searchRepositoryByKeyword(keyword);

		if (repositories.isEmpty()) {
			model.addAttribute("noResult", true);
		} else {
			model.addAttribute("repositories", repositories);
		}

		return "search";
	}

}
