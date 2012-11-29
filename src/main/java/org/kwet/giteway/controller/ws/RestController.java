package org.kwet.giteway.controller.ws;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.kwet.giteway.dao.GitRepositoryConnector;
import org.kwet.giteway.model.Commit;
import org.kwet.giteway.model.CommitterActivities;
import org.kwet.giteway.model.Timeline;
import org.kwet.giteway.service.StatisticsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// TODO: Auto-generated Javadoc
/**
 * The Class RestController.
 * 
 * @author Antoine Couette
 *
 */
@Controller
public class RestController {

	@Autowired
	private GitRepositoryConnector gitRepositoryConnector;

	@Autowired
	private StatisticsCalculator statisticsCalculator;

	/**
	 * Gets the time line data.
	 *
	 * @param owner the owner
	 * @param name the name
	 * @return the time line data
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/timeline/{owner}/{name}")
	public @ResponseBody
	Timeline getTimeLineData(@PathVariable String owner, @PathVariable String name) throws JsonGenerationException,
			JsonMappingException, IOException {

		List<Commit> commits = gitRepositoryConnector.findCommits(owner, name);
		return new Timeline(statisticsCalculator.getTimeLine(commits));
	}

	/**
	 * Gets the committer activities.
	 *
	 * @param owner the owner
	 * @param name the name
	 * @return the committer activities
	 * @throws JsonGenerationException the json generation exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/activity/{owner}/{name}")
	public @ResponseBody
	CommitterActivities getCommitterActivities(@PathVariable String owner, @PathVariable String name) throws JsonGenerationException,
			JsonMappingException, IOException {

		List<Commit> commits = gitRepositoryConnector.findCommits(owner, name);
		return new CommitterActivities(statisticsCalculator.calculateActivity(commits));
	}

}
