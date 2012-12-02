package org.kwet.giteway.controller.ws;

import org.kwet.giteway.model.CommitterActivities;
import org.kwet.giteway.model.Timeline;
import org.kwet.giteway.service.StatisticsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The RestController handles restful calls. It provides application statisctics throw a WS API : If
 * client requests Application/xml as response type, the service will provide an xml response If
 * client requests Application/json as a response type, the service will provide a json response
 * 
 * @author a.couette
 * 
 */
@Controller
public class RestController {

	@Autowired
	private StatisticsCalculator statisticsCalculator;

	/**
	 * Returns the timeline data stats.
	 * 
	 * @param owner the repository owner
	 * @param name the repository name
	 * @return the time line data as json or xml
	 */
	@RequestMapping("/timeline/{owner}/{name}")
	@ResponseBody
	public Timeline getTimeLine(@PathVariable String owner, @PathVariable String name) {
		return statisticsCalculator.getTimeLine(owner,name);
	}

	/**
	 * Gets the committer activities stats.
	 * 
	 * @param owner the repository owner
	 * @param name the repository name
	 * @return the committer activities as json or xml
	 */
	@RequestMapping("/activity/{owner}/{name}")
	@ResponseBody
	public CommitterActivities getCommitterActivities(@PathVariable String owner, @PathVariable String name) {
		return statisticsCalculator.calculateActivity(owner,name);
	}

}
