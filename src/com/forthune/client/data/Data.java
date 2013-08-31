package com.forthune.client.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableMap;

/**
 * The container of all the application data.
 * 
 * @author Guillaume Durand
 *
 */
public class Data extends AbstractPersistable {
	private ImmutableMap<String, User> users;
	
	public Data() {
		this.users = ImmutableMap.<String, User>builder().build();
	}

	public ImmutableMap<String, User> getUsers() {
		return this.users;
	}

	public void setUsers(ImmutableMap<String, User> users) {
		this.users = users;
		setDirty();
	}
	
	public void addUser(User user) {
		this.users = Utils.addItem(this.users, user);
		setDirty();
	}
	
	public void removeUser(User user) {
		this.users = Utils.removeItem(this.users, user);
		setDirty();
	}

	public void accept(DataVisitor visitor) {
    if(visitor.visit(this)) {
	  	for (User user : this.users.values()) {
				user.accept(visitor);
			}
    }
	}

	public Iterator<Persistable> iterator() {
		List<Iterator<? extends Iterable<Persistable>>> iters = new ArrayList<Iterator<? extends Iterable<Persistable>>>();
		iters.add(this.users.values().iterator());
		return new CompoundIterator<Persistable, AbstractPersistable>(this, iters.iterator());
	}
}
