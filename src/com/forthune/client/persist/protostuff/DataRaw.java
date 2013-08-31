package com.forthune.client.persist.protostuff;

import com.dyuproject.protostuff.Tag;
import com.forthune.client.data.Data;
import com.forthune.client.data.User;
import com.google.common.collect.ImmutableMap;

/**
 * @author Guillaume Durand
 *
 */
final class DataRaw {
	@Tag(1)
	UserRaw[] users;
	
	public DataRaw() {
		
	}
	
	public DataRaw(Data data) {
		this.users = new UserRaw[data.getUsers().size()];
		int i = 0;
		for (User user : data.getUsers().values()) {
			this.users[i] = new UserRaw(user);
			i++;
		}
	}
	
	public Data getFinal() {
		Data data = new Data();
		if (this.users != null) {
			ImmutableMap.Builder<String, User> builder = ImmutableMap.<String, User>builder();
			for (int i = 0; i < this.users.length; i++) {
				User user = this.users[i].getFinal();
				builder.put(user.getId(), user);
			}
			data.setUsers(builder.build());
		}

		return data;
	}
}