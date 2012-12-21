package org.kwet.giteway.dao.converter;

import java.util.Map;

import org.kwet.giteway.model.Repository;

import ch.lambdaj.function.convert.Converter;

public class RepositoryConverter implements Converter<Map<String, Object>, Repository> {

	@Override
	public Repository convert(Map<String, Object> from) {

		Repository repository = new Repository();
		repository.setName((String) from.get("name"));
		repository.setDescription((String) from.get("description"));
		repository.setOwner((String)from.get("username"));
		return repository;
		
	}
	
}