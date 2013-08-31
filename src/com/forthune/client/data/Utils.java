package com.forthune.client.data;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

final class Utils {
	/**
	 * Return a new map containing the specified map with the item added to it.
	 * Adding an item already in the map has no effect.
	 */
	static <T extends AbstractItem> ImmutableMap<String, T> addItem(ImmutableMap<String, T> map, T item) {
		if (!map.containsKey(item.getId())) {
			return ImmutableMap.<String, T>builder().putAll(map).put(item.getId(), item).build();
		} else {
			return map; // TBD return a new map anyway ?
		}
	}
	
	static <T extends AbstractItem> ImmutableMap<String, T> removeItem(ImmutableMap<String, T> map, T item) {
		ImmutableMap.Builder<String, T> builder = ImmutableMap.<String, T>builder();
		for (Map.Entry<String, T> entry : map.entrySet()) {
			if (!entry.getKey().equals(item.getId())) {
				builder.put(entry);
			}
		}
		
		return builder.build();
	}
}
