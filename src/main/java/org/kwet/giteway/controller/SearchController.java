package org.kwet.giteway.controller;

import java.util.List;
import java.util.Locale;

import org.kwet.giteway.data.GitSearchConnector;
import org.kwet.giteway.model.RepositorySearch;
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
 * Handles requests for the application home page.
 */
@Controller
public class SearchController {

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private GitSearchConnector gitSearchConnector;
	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String handleForm(Model model, @RequestParam(required=false) String keyword) {
		return "redirect:/search/"+keyword;
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
	public String home(Model model, @PathVariable String keyword) {
		if(!keyword.isEmpty()){
			List<RepositorySearch> repositories = gitSearchConnector.searchRepositoryByKeyword(keyword);
			model.addAttribute("repositories", repositories);
			model.addAttribute("keyword", keyword);
		}
		return "home";
	}

}
