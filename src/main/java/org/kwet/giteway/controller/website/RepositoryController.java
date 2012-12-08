package org.kwet.giteway.controller.website;

import java.io.IOException;
import java.util.List;

import org.kwet.giteway.dao.GitRepositoryConnector;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Repository view controller
 * 
 * @author a.couette
 * 
 */
@Controller
@RequestMapping(value = "/repos")
public class RepositoryController {

	private static final Logger LOG = LoggerFactory.getLogger(RepositoryController.class);

	@Autowired
	private GitRepositoryConnector gitRepositoryConnector;


	/**
	 * Handles http restful get requests from /repository/{owner}/{name}. 
	 * Sets as request attributes :
	 * 	1. General repository information
	 *  2. Collaborator list
	 * 
	 * @param model
	 * @param owner the repository owner
	 * @param name the repository name
	 * @throws IOException
	 */
	@RequestMapping(value = "/{owner}/{name}", method = RequestMethod.GET)
	public String getRepositoryStats(Model model, @PathVariable String owner, @PathVariable String name) throws IOException {

		if(LOG.isInfoEnabled()){
			LOG.info("Start handling repository stats request : Owner : ["+owner+"], Name : ["+name+"]");
		}
		
		// find the repository
		Repository repository = gitRepositoryConnector.find(owner, name);
		model.addAttribute("repository", repository);

		// populate the collaborators
		List<User> collaborators = gitRepositoryConnector.findCollaborators(repository);
		model.addAttribute("collaborators", collaborators);

		if(LOG.isInfoEnabled()){
			LOG.info("Done handling repository stats request : Owner : ["+owner+"], Name : ["+name+"]");
		}
		
		return "repository";
	}
}
