package org.kwet.giteway.controller;

import java.util.List;

import org.kwet.giteway.github.GitSearchConnector;
import org.kwet.giteway.model.RepositorySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private GitSearchConnector gitSearchConnector;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String handleForm(Model model, @RequestParam(required = false) String keyword) {
		return "redirect:/search/" + keyword;
	}

	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
	public String home(Model model, @PathVariable String keyword) {
		model.addAttribute("keyword", keyword);

		List<RepositorySearch> repositories = gitSearchConnector.searchRepositoryByKeyword(keyword);

		if (repositories.isEmpty()) {
			model.addAttribute("noResult", true);
		} else {
			model.addAttribute("repositories", repositories);
		}

		return "search";
	}

}
