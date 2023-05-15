import java.util.Iterator;
import java.util.NoSuchElementException;

public interface IList<TKey, TValue> extends Iterable<TKey> {
    void add(TKey key, TValue value);
    void addAt(int index, TKey key, TValue value) throws NoSuchElementException;
    void clear();
    boolean containsKey(TKey key);
    boolean containsValue(TValue key);
    TKey getKey(int index) throws NoSuchElementException;
    TValue getValue(int index) throws NoSuchElementException;
    void setKey(int index, TKey key) throws NoSuchElementException;
    void setValue(int index, TValue value) throws NoSuchElementException;
    int indexOfKey(TKey key);
    int indexOfValue(TValue value);
    boolean isEmpty();
    TKey removeAt(int index) throws NoSuchElementException;
    boolean remove(TKey key);
    int size();
    Iterator<TKey> iterator();
}
