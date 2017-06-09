package rocket.game.dao;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dao<T> {
	private List<T> elements = new ArrayList<>();
	private int capacity;

	public Dao(int capacity) {
		this.capacity = capacity;
	}

	public void add(T element) {
		if (elements.size() > capacity && capacity != -1) {
			throw new BufferOverflowException();
		}
		elements.add(element);
	}

	public void remove(T element) {
		elements.remove(element);
	}

	public void expand(int expansion) {
		this.capacity += expansion;
	}

	public void resize(int newCapacity) {
		if (newCapacity < size()) {
			throw new IllegalArgumentException(
					"Cannot resize to " + newCapacity + " as there are currently " + size() + " elements in dao");
		}
		this.capacity = newCapacity;
	}

	public Iterator<T> iterator() {
		return elements.iterator();
	}
	
	public T get(int index) {
		return elements.get(index);
	}
	
	public int capacity() {
		return capacity;
	}

	public int size() {
		return elements.size();
	}
}
