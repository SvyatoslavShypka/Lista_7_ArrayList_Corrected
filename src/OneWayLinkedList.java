import java.util.Iterator;
import java.util.NoSuchElementException;

public class OneWayLinkedList<TKey, TValue> implements IList<TKey, TValue> {

    Element head = null;

    public class Element{

        private TKey key;
        private TValue value;
        private Element next;

        Element(TKey key, TValue value){
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public TValue getValue() {
            return value;
        }

        public void setValue(TValue value) {
            this.value = value;
        }

        public TKey getKey() {
            return key;
        }

        public void setKey(TKey key) {
            this.key = key;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }
    }

    @Override
    public void add(TKey key, TValue value) {
        Element newElement = new Element(key, value);
        if (isEmpty()) {
            head = newElement;
        }
        else {
            Element lastElement = head;
            while (lastElement.getNext() != null) {
                lastElement = lastElement.getNext();
            }
            lastElement.setNext(newElement);
        }
    }

    @Override
    public void addAt(int index, TKey key, TValue value) throws NoSuchElementException {
        Element newElement = new Element(key, value);
        if (index == 0) {
            newElement.setNext(head);
            head = newElement;
        }
        else {
            Element previousElement = head;
            Element actElem = head;
            while (index > 0 && actElem != null) {
                index--;
                previousElement = actElem;
                actElem = actElem.getNext();
            }
            if (actElem == null) {
                throw new NoSuchElementException();
            } else {
                previousElement.setNext(newElement);
                newElement.setNext(actElem);
            }
        }

    }

    @Override
    public void clear() {
        head = null;
    }

    @Override
    public boolean containsKey(TKey key) {
        return indexOfKey(key) >= 0;
    }

    @Override
    public boolean containsValue(TValue value) {
        return indexOfValue(value) >= 0;
    }

    @Override
    public TKey getKey(int index) throws NoSuchElementException {
        Element actElem = head;
        while(index>0 && actElem != null){
            index--;
            actElem = actElem.getNext();
        }
        if(actElem == null) {
            throw new NoSuchElementException();
        }
        else {
            return actElem.getKey();
        }
    }

    @Override
    public TValue getValue(int index) throws NoSuchElementException {
        Element actElem = head;
        while(index>0 && actElem != null){
            index--;
            actElem = actElem.getNext();
        }
        if(actElem == null) {
            throw new NoSuchElementException();
        }
        else {
            return actElem.getValue();
        }
    }

    @Override
    public void setKey(int index, TKey key) throws NoSuchElementException {
        Element actElem = head;
        while(index>0 && actElem != null){
            index--;
            actElem = actElem.getNext();
        }
        if(actElem == null) {
            throw new NoSuchElementException();
        }
        else {
            actElem.setKey(key);
        }
    }

    @Override
    public void setValue(int index, TValue value) throws NoSuchElementException {
        Element actElem = head;
        while(index>0 && actElem != null){
            index--;
            actElem = actElem.getNext();
        }
        if(actElem == null) {
            throw new NoSuchElementException();
        }
        else {
            actElem.setValue(value);
        }
    }

    @Override
    public int indexOfKey(TKey key) {
        int pos = 0;
        Element actElem = head;
        while(actElem != null) {
            if(actElem.getKey().equals(key)) {
                return pos;
            }
            pos++;
            actElem = actElem.getNext();
        }
        return -1;
    }

    @Override
    public int indexOfValue(TValue value) {
        int pos = 0;
        Element actElem = head;
        while(actElem != null) {
            if(actElem.getValue().equals(value)) {
                return pos;
            }
            pos++;
            actElem = actElem.getNext();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public TKey removeAt(int index) throws NoSuchElementException {
        if(head == null) throw new NoSuchElementException();
        TKey result;
        if(index == 0) {
            result = head.getKey();
            head = head.getNext();
            return result;
        }
        Element actElem = head;
        Element previous = head;
        while(index > 0 && actElem.getNext() != null) {
            index--;
            previous = actElem;
            actElem = actElem.getNext();
        }
        if(index != 0) {
            throw new NoSuchElementException();
        }
        result = actElem.getKey();
        previous.setNext(actElem.getNext());
        return result;
    }

    @Override
    public boolean remove(TKey key) {
            if(head == null) return false;
            if(head.getKey().equals(key)){
                head=head.getNext();
                return true;
            }
            Element actElem = head;
            while(actElem.getNext() != null && !actElem.getNext().getKey().equals(key)) {
                actElem = actElem.getNext();
            }
            if(actElem.getNext() == null) {
                return false;
            }
            actElem.setNext(actElem.getNext().getNext());
            return true;
    }

    @Override
    public int size() {
        int pos = 0;
        Element actElem = head;
        while(actElem != null)
        {
            pos++;
            actElem = actElem.getNext();
        }
        return pos;
    }

    @Override
    public Iterator<TKey> iterator() {
        return new OneWayLinkedListIterator();
    }

    private class OneWayLinkedListIterator implements Iterator<TKey> {

        Element actElem;

        public OneWayLinkedListIterator() {
            actElem = head;
        }

        @Override
        public boolean hasNext() {
            return actElem != null;
        }

        @Override
        public TKey next() {
            if (hasNext() == false) throw new NoSuchElementException();
            TKey currentValue = actElem.getKey();
            actElem = actElem.getNext();
            return currentValue;
        }
    }
}
