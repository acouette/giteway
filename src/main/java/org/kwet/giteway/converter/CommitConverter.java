package org.kwet.giteway.dao.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.kwet.giteway.model.Commit;

import ch.lambdaj.function.convert.Converter;

public class CommitConverter implements Converter<Map<String, Object>, Commit> {

	@SuppressWarnings("unchecked")
	@Override
	public Commit convert(Map<String, Object> from) {
		try {
			Commit commit = new Commit();
			Map<String, Object> commitDetail = (Map<String, Object>) from.get("commit");
			commit.setMessage((String) commitDetail.get("message"));
			Map<String, Object> commitDateWrapper = (Map<String, Object>) commitDetail.get("committer");
			if (commitDateWrapper != null) {
				commit.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse((String) commitDateWrapper.get("date")));
			}
			Map<String, Object> committer = (Map<String, Object>) from.get("committer");
			commit.setCommiter(new UserConverter().convert(committer));
			return commit;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}