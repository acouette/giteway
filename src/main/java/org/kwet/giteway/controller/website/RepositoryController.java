package org.kwet.giteway.controller.website;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

/**
 * Repository view controller
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

	private static int COMMIT_LIMIT = 100;

	/**
	 * Handles http restful get requests from /repository/{owner}/{name}. 
	 * Sets as request attributes :
	 * 	1. General repository information
	 *  2. Collaborator list
	 *  3. TimelineData
	 *  4. Committers Activity
	 * 
	 * @param model
	 * @param owner the repository owner
	 * @param name the repository name
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/{owner}/{name}", method = RequestMethod.GET)
	public String search(Model model, @PathVariable String owner, @PathVariable String name) throws JsonGenerationException, JsonMappingException,
			IOException {

		// find the repository
		Repository repository = gitRepositoryConnector.find(owner, name);
		model.addAttribute("repository", repository);

		// find the collaborators
		List<User> collaborators = gitRepositoryConnector.findCollaborators(owner, name);
		model.addAttribute("collaborators", collaborators);

		//find the commits
		List<Commit> commits = gitRepositoryConnector.findCommits(owner, name, COMMIT_LIMIT);
		model.addAttribute("commitCount", commits.size());
		
		// Calculate Timeline stats
		List<TimelineChunk> timelineChunks = statisticsCalculator.getTimeLine(commits);
		model.addAttribute("timelineChunks", buildJsonString(timelineChunks));
		double chunkDuration = statisticsCalculator.getChunkDurationInDays(timelineChunks.get(0));
		String chunkDurationFormatted = String.format(Locale.ENGLISH,"%4.1f", chunkDuration);
		model.addAttribute("chunkDuration", chunkDurationFormatted);

		// Calculate Committers activity stats
		List<CommitterActivity> committerActivities = statisticsCalculator.calculateActivity(commits);
		model.addAttribute("committerActivities", buildJsonString(committerActivities));

		return "repository";
	}
	
	
	//Builds a json string from a serializable object
	private String buildJsonString(Object object) throws JsonGenerationException, JsonMappingException, IOException{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		objectMapper.writeValue(outputStream, object);
		String result = new String(outputStream.toByteArray());
		if (logger.isDebugEnabled()) {
			logger.debug("Generated Json : " + result);
		}
		return result;
	}

}
