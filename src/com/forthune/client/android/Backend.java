package com.forthune.client.android;

import com.forthune.client.data.Data;
import com.forthune.client.data.User;
import com.google.common.base.Optional;

public interface Backend {
	public Data getData();
	public Optional<User> getCurrentUser();
}
