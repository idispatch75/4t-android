package com.forthune.client.data;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CompoundIteratorTest {
	@Test
	public void testIteration() {
		// root
		// |__ rootChildren1
		//     |__ item11
		//         |__ item11Children1
		//             |__ item
		//             |__ item
		//     |__ item
		// |__ rootChildren2
		//     |__ item  
		Item root = new Item("root");
		
		List<Item> rootChildren1 = new ArrayList<Item>();
		root.children.add(rootChildren1);
		Item item11 = new Item("item11");
		List<Item> item11Children1 = new ArrayList<Item>();
		item11.children.add(item11Children1);
		item11Children1.add(new Item("item111"));
		item11Children1.add(new Item("item112"));
		rootChildren1.add(item11);
		rootChildren1.add(new Item("item12"));
		
		List<Item> rootChildren2 = new ArrayList<Item>();
		root.children.add(rootChildren2);
		rootChildren2.add(new Item("item21"));

		int i = 0;
		for (Item item : root) {
			item.visited = true;
			System.out.println(item.id);
			i++;
		}
		
		assertThat(i, is(6));
		for (Item item : root) {
			assertThat(item.visited, is(true));
		}
	}
	
	private class Item implements Iterable<Item> {
		public boolean visited = false;
		private String id;
		public List<List<Item>> children = new ArrayList<List<Item>>();
		
		public Item(String id) {
			this.id = id;
		}
		
		public Iterator<Item> iterator() {
			Iterator<Iterator<? extends Iterable<Item>>> childIterators = null;
			
			if (this.children.size() > 0) {
				List<Iterator<? extends Iterable<Item>>> iters = new ArrayList<Iterator<? extends Iterable<Item>>>();
				for (List<Item> child : this.children) {
					iters.add(child.iterator());
				}
				childIterators = iters.iterator();
			}
			
			return new CompoundIterator<Item, Item>(this, childIterators);
		}
	}
}
