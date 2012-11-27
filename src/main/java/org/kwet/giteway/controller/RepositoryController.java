package org.kwet.giteway.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.kwet.giteway.github.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivity;
import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.TimelineData;
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
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/repository")
public class RepositoryController {

	private static final Logger logger = LoggerFactory.getLogger(RepositoryController.class);

	@Autowired
	private GitRepositoryConnector gitRepositoryConnector;

	@Autowired
	private StatisticsCalculator statisticsCalculator;

	@RequestMapping(value = "/{owner}/{name}", method = RequestMethod.GET)
	public String home(Model model, @PathVariable String owner, @PathVariable String name) throws JsonGenerationException,
			JsonMappingException, IOException {

		// Repository
		Repository repository = gitRepositoryConnector.find(owner, name);
		model.addAttribute("repository", repository);

		// Collaborators
		List<User> collaborators = gitRepositoryConnector.findCollaborators(repository);
		model.addAttribute("collaborators", collaborators);

		List<Commit> commits = gitRepositoryConnector.findCommits(repository);

		// Timeline
		List<TimelineData> timelineDatas = statisticsCalculator.getTimeLine(commits);
		ObjectMapper objectMapper = new ObjectMapper();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		objectMapper.writeValue(outputStream, timelineDatas);
		String timelineJson = new String(outputStream.toByteArray());
		model.addAttribute("timelineData", timelineJson);
		logger.debug("timelineJson : " + timelineJson);

		// Committers activity
		List<CommitterActivity> committerActivities = statisticsCalculator.calculateActivity(commits);
		statisticsCalculator.concatLittleCommiters(committerActivities);
		outputStream = new ByteArrayOutputStream();
		objectMapper.writeValue(outputStream, committerActivities);
		String committerActivityJson = new String(outputStream.toByteArray());
		model.addAttribute("committerActivities", committerActivityJson);
		logger.debug("committerActivityJson : " + committerActivityJson);

		return "repository";
	}

}
