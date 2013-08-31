package com.forthune.client.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

final class TestUtils {
	static void assertConstructor(AbstractItem item, AbstractItem newItem, boolean full) {
		assertThat(item.isDirty(), is(!full));
		assertThat(item.isNew(), is(!full));
		
		// 2 new items built with the same id (full constructor) have the same id
		// 2 new items have different ids
		assertThat(newItem.getId(), full ? is(item.getId()) : not(item.getId()));
	}
}
