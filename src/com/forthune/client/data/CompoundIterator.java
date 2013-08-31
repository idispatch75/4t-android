package com.forthune.client.data;

import java.util.Iterator;

/**
 * Allows to iterate inside nested iterables.
 * Just for fun.
 * 
 * @author Guillaume Durand
 *
 * @param <T>
 * 					The type to return when calling {@link #next()}, which is returned by each iterable.
 * @param <U>
 * 					The type of the container of iterables. It will be the first returned item when calling {@link #next()}.
 * 					Must extend the returned type T.
 */
final class CompoundIterator<T, U extends T> implements Iterator<T> {
	private T self;
	private Iterator<Iterator<? extends Iterable<T>>> listOfLists;
	private boolean selfDone = false;
	private Iterator<? extends Iterable<T>> currentList;
	private Iterator<T> currentIterable;
	
	// for optimization : if 1, then we have a next, if 2, we don't have a next, else we must recompute hasNext
	private int hasNextStatus = 0; 
	
	/**
	 * @param self
	 * 					the iterator container.
	 * @param listOfLists
	 * 					an iterator on the iterators on each iterable type contained by <code>self</code>. WTF !?
	 */
	public CompoundIterator(U self, Iterator<Iterator<? extends Iterable<T>>> listOfLists) {
		this.self = self;
		this.listOfLists = listOfLists;
	}

	public boolean hasNext() {
		// if we have valid stored status, return it now
		if (this.hasNextStatus == 1) {
			return true;
		} else if (this.hasNextStatus == 2) {
			return false;
		}
		
		// if we have returned self yet, then we have next
		if (!this.selfDone) {
			this.hasNextStatus = 1;
			
		// if we have non empty lists
		} else if (this.listOfLists != null && this.currentIterable != null) {
			// if current iterable has next, we have next
			if (this.currentIterable.hasNext()) {
				this.hasNextStatus = 1;
				
			// if an iterable of the current list has next, we have next
			} else if (hasNextIterable()) {
				this.hasNextStatus = 1;
				
			// find an iterable in the next lists
			} else {
				while (this.listOfLists.hasNext()) {
					this.currentList = this.listOfLists.next();
					
					if (hasNextIterable()) {
						this.hasNextStatus = 1;
						break;
					}
				}
			}
		}
		
		if (this.hasNextStatus != 1) {
			this.hasNextStatus = 2;
		}
		
		return this.hasNextStatus == 1 ;
	}
	
	/**
	 * Returns <code>true</code> if the current list has an iterable which has next.
	 */
	private boolean hasNextIterable() {
		while (this.currentList.hasNext()) {
			this.currentIterable = this.currentList.next().iterator();

			if (this.currentIterable.hasNext()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * {@link #hasNext()} must be called before each call to {@link #next()}.
	 * 
	 * @see java.util.Iterator#next()
	 */
	public T next() {
		this.hasNextStatus = 0; // clear the hasNext status
		
		// first access: return self
		if (!this.selfDone) {
			// init the current iterable. Warning we may not have one and currentIterable will be null.
			if (this.listOfLists != null) {
				while (this.currentIterable == null && this.listOfLists.hasNext()) {
					this.currentList = this.listOfLists.next();
					while (this.currentIterable == null && this.currentList.hasNext()) {
						this.currentIterable = this.currentList.next().iterator();
					}
				}
			}
			
			// return self
			this.selfDone = true;
			return this.self;
			
		} else {
			return this.currentIterable.next();
		}
	}

	@Deprecated
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
