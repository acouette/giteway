package org.kwet.giteway.controller.website;

import java.util.List;

import org.kwet.giteway.dao.GitSearchConnector;
import org.kwet.giteway.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchController.
 * 
 * @author Antoine Couette
 *
 */
@Controller
public class SearchController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(SearchController.class);

	
	@Autowired
	private GitSearchConnector gitSearchConnector;

	/**
	 * Handle form.
	 *
	 * @param model the model
	 * @param keyword the keyword
	 * @return the string
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String handleForm(Model model, @RequestParam(required = false) String keyword) {
		return "redirect:/search/" + keyword;
	}

	/**
	 * Home.
	 *
	 * @param model the model
	 * @param keyword the keyword
	 * @return the string
	 */
	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
	public String home(Model model, @PathVariable String keyword) {
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
