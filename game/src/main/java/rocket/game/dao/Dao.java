package rocket.game.dao;

public interface Dao<T> {
	public void insert(T element);
	public void remove(T element);
	public T[] getAll();
	public int size();
}
