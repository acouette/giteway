package org.kwet.giteway.service;

import java.util.Map;

import org.kwet.giteway.model.Repository;
import org.kwet.giteway.model.User;

public interface StatisticsCalculator{
	
	Map<User,Double> calculateActivity(Repository repository);
	
	
	
}