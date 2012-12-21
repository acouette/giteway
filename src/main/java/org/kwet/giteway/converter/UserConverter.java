package org.kwet.giteway.dao.converter;

import java.util.Map;

import org.kwet.giteway.model.User;

import ch.lambdaj.function.convert.Converter;

public class UserConverter implements Converter<Map<String,Object>, User>{

		@Override
		public User convert(Map<String, Object> from) {
			
			User user = new User();
			user.setLogin((String)from.get("login"));
			user.setAvatarUrl((String)from.get("avatar_url"));
			return user;
		}
		
	}