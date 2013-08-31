package com.forthune.client.persist.protostuff;

import com.dyuproject.protostuff.Tag;
import com.forthune.client.data.AbstractItem;

/**
 * @author Guillaume Durand
 *
 */
abstract class ItemRaw {
	@Tag(1)
	String id;

	@Tag(2)
	long revision;
	
	public ItemRaw(AbstractItem item) {
		this.id = item.getId();
		this.revision = item.getRevision();
	}
}