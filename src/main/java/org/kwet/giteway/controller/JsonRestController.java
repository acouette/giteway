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
import org.kwet.giteway.model.TimelineData;
import org.kwet.giteway.service.StatisticsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rest")
public class JsonRestController {

	@Autowired
	private GitRepositoryConnector gitRepositoryConnector;

	@Autowired
	private StatisticsCalculator statisticsCalculator;

	@RequestMapping("/timeline/{owner}/{name}/json")
	public @ResponseBody
	String getTimeLineData(@PathVariable String owner, @PathVariable String name) throws JsonGenerationException, JsonMappingException,
			IOException {

		List<Commit> commits = gitRepositoryConnector.findCommits(owner, name);

		// Timeline
		List<TimelineData> timelineDatas = statisticsCalculator.getTimeLine(commits);
		ObjectMapper objectMapper = new ObjectMapper();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		objectMapper.writeValue(outputStream, timelineDatas);
		String timelineJson = new String(outputStream.toByteArray());
		return timelineJson;
		//TODO : manage json return code
	}

	@RequestMapping("/committers/activities/{owner}/{name}/json")
	public @ResponseBody
	String getCommitterActivities(@PathVariable String owner, @PathVariable String name) throws JsonGenerationException, JsonMappingException,
			IOException {

		List<Commit> commits = gitRepositoryConnector.findCommits(owner, name);

		// Committers activity
		List<CommitterActivity> committerActivities = statisticsCalculator.calculateActivity(commits);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(outputStream, committerActivities);
		String committerActivityJson = new String(outputStream.toByteArray());
		return committerActivityJson;

		//TODO : manage json return code
	}

}
