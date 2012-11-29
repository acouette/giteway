package org.kwet.giteway.controller.website;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.kwet.giteway.dao.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.TimelineChunk;
import org.kwet.giteway.model.User;
import org.kwet.giteway.service.StatisticsCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author Antoine Couette
 *
 */
@Controller
@RequestMapping(value = "/repository")
public class RepositoryController {

	private static final Logger logger = LoggerFactory.getLogger(RepositoryController.class);

	
	@Autowired
	private GitRepositoryConnector gitRepositoryConnector;

	
	@Autowired
	private StatisticsCalculator statisticsCalculator;
	
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Handles http get requests from /repository/{owner}/{name}.
	 *
	 * @param model the model
	 * @param owner the owner
	 * @param name the name
	 * @return the string
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/{owner}/{name}", method = RequestMethod.GET)
	public String search(Model model, @PathVariable String owner, @PathVariable String name) throws JsonGenerationException,
			JsonMappingException, IOException {

		// Repository
		Repository repository = gitRepositoryConnector.find(owner, name);
		model.addAttribute("repository", repository);

		// Collaborators
		List<User> collaborators = gitRepositoryConnector.findCollaborators(owner,name);
		model.addAttribute("collaborators", collaborators);

		List<Commit> commits = gitRepositoryConnector.findCommits(owner,name);

		// Timeline
		List<TimelineChunk> timelineChunks = statisticsCalculator.getTimeLine(commits);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		objectMapper.writeValue(outputStream, timelineChunks);
		String timelineJson = new String(outputStream.toByteArray());
		model.addAttribute("timelineData", timelineJson);
		if(logger.isDebugEnabled()){
			logger.debug("timelineJson : " + timelineJson);
		}

		// Committers activity
		List<CommitterActivity> committerActivities = statisticsCalculator.calculateActivity(commits);
		outputStream = new ByteArrayOutputStream();
		objectMapper.writeValue(outputStream, committerActivities);
		String committerActivityJson = new String(outputStream.toByteArray());
		model.addAttribute("committerActivities", committerActivityJson);
		if(logger.isDebugEnabled()){
			logger.debug("committerActivityJson : " + committerActivityJson);
		}

		return "repository";
	}

}
