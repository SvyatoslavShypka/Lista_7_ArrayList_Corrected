import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class HashMap<TKey, TValue> {
    // initial capacity
    private final int initialSize;
    // load_factor is used to perform calculations and guess if automatic increase is required
    // current_size should be increment when current_no_elements x load_factor becomes large
    private double loadFactor;

    private Function<TKey, Integer> hashFunction;
    // The arrayList where the nodes containing Key-Value pairs are stored
    ArrayList<MapNode<TKey, TValue> > arrayList;

    // Quantity of pairs stored
    int elementsQuantity;

    // Actual size of the arrayList
    int current_size;

    public void rehash(Function<TKey, Integer> newHashFunction) {
        // TODO: Zmień obecną funkcję hashującą na nową (wymaga przeliczenia dla wszystkich par klucz-wartość).
        ArrayList<MapNode<TKey, TValue>> temp = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {
            // Initialised to null
            MapNode<TKey, TValue> head = arrayList.get(i);
            while(head != null) {
                temp.add(head);
                head = head.next;
            }
            arrayList.set(i, null);
        }
        elementsQuantity = 0;
        hashFunction = newHashFunction;
        for (int i = 0; i < temp.size(); i++) {
            try {
                this.add(temp.get(i).key, temp.get(i).value);
            } catch (DuplicateKeyException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class MapNode<TKey, TValue> {
        TKey key;
        TValue value;
        MapNode<TKey, TValue> next;

        public MapNode(TKey key, TValue value)
        {
            this.key = key;
            this.value = value;
            next = null;
        }
    }

    private void increaseAndReHashMap() {
        System.out.println("\n***Rehashing Started***\n");

        // The present arrayList is made temp
        ArrayList<MapNode<TKey, TValue>> temp = arrayList;

        current_size *= 2;
        // New arrayList of double the old size is created
        arrayList = new ArrayList<MapNode<TKey, TValue> >(current_size);

        for (int i = 0; i < current_size; i++) {
            // Initialised to null
            arrayList.add( null);
        }
        // Now size is made zero
        // and we loop through all the nodes in the original arrayList(temp)
        // and insert it into the new list
        elementsQuantity = 0;

        for (int i = 0; i < temp.size(); i++) {

            // head of the chain at that index
            MapNode<TKey, TValue> head = temp.get(i);

            while (head != null) {

                // calling the insert function for each node in temp
                // as the new list is now the bucketArray
                try {
                    this.add(head.key, head.value);
                } catch (DuplicateKeyException e) {
                    throw new RuntimeException(e);
                }
                head = head.next;
            }
        }
        System.out.println("\n***Rehashing Ended***\n");
    }


    public HashMap(int initialSize, double loadFactor, Function<TKey, Integer> hashFunction) {
        // TODO: Zainicjuj nową instancję klasy HashMap według podanych parametrów.
        //    InitialSize - początkowy rozmiar HashMap
        //    LoadFactor - stosunek elementów do rozmiaru HashMap po przekroczeniu którego należy podwoić rozmiar HashMap.
        //    HashFunction - funkcja, według której liczony jest hash klucza.
        //       Przykład użycia:   int hash = hashFunction.apply(key);

        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.hashFunction = hashFunction;

        elementsQuantity = 0;
        current_size = initialSize;

        arrayList = new ArrayList<>(initialSize);

        for (int i = 0; i < current_size; i++) {
            // Initialising to null
            arrayList.add(null);
        }
        System.out.println("HashMap created");
        System.out.println("Number of pairs in the Map: " + elementsQuantity);
        System.out.println("Size of Map: " + current_size);
        System.out.println("Default Load Factor : " + loadFactor + "\n");
    }

    public void add(TKey key, TValue value) throws DuplicateKeyException {
        // TODO: Dodaj nową parę klucz-wartość. Rzuć wyjątek DuplicateKeyException, jeżeli dany klucz już istnieje w HashMap.
        if (containsKey(key)) throw new DuplicateKeyException();
        // Getting the index at which it needs to be inserted
        int position = calculatePosition(key);

        // The first node at that index
        MapNode<TKey, TValue> head = new MapNode<>(key, value);
        head.next = arrayList.get(position);
        arrayList.set(position, head);

        System.out.println("Pair(" + key + ", " + value + ") inserted successfully.\n");

        // Incrementing size
        // as new K-V pair is added to the map
        elementsQuantity++;

        System.out.println("Current Load factor = " + loadFactor);

        // If the load factor is exceeded, rehashing is done
        if (((1.0 * elementsQuantity) / current_size) > loadFactor) {
            System.out.println("LoadFactor is greater than " + loadFactor);
            System.out.println("Therefore Rehashing will be done.\n");

            // Rehash
            increaseAndReHashMap();

            System.out.println("New Size of Map: " + current_size + "\n");
        }
        System.out.println("Number of pairs in the Map: " + current_size);
        System.out.println("Size of Map: " + current_size + "\n");
    }

    public void clear() {
        // TODO: Wyczyść zawartość HashMap.
        elementsQuantity = 0;
        current_size = initialSize;
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.set(i, null);
        }
    }

    public boolean containsKey(TKey key) {
        // TODO: Sprawdź, czy HashMap zawiera już dany klucz.
        int position = calculatePosition(key);
        MapNode<TKey, TValue> head = arrayList.get(position);
        while(head != null) {
            if (head.key.equals(key)) return true;
            head = head.next;
        }
        return false;
    }

    public boolean containsValue(TValue value) {
        MapNode<TKey, TValue> head;
        // TODO: Sprawdź, czy HashMap zawiera już daną wartość.
        for (int i = 0; i < arrayList.size(); i++) {
            head = arrayList.get(i);
            while(head != null) {
                if (head.value.equals(value)) return true;
                head = head.next;
            }
        }
        return false;
    }

    public int elements() {
        // TODO: Zwróć liczbę par klucz-wartość przechowywaną w HashMap.
        return elementsQuantity;
    }

    public TValue get(TKey key) throws NoSuchElementException {
        // TODO: Pobierz wartość powiązaną z danym kluczem. Rzuć wyjątek NoSuchElementException, jeżeli dany klucz nie istnieje.
        //Get actual index of the key
        if (!containsKey(key)) throw new NoSuchElementException();
        int position = calculatePosition(key);
        MapNode<TKey,TValue> temp = arrayList.get(position);
        //Search for key in list
        while(temp != null){
            if(temp.key.equals(key)){
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
    }

    public void put(TKey key, TValue value) {
        // TODO: Przypisz daną wartość do danego klucza.
        //   Jeżeli dany klucz już istnieje, nadpisz przypisaną do niego wartość.
        //   Jeżeli dany klucz nie istnieje, dodaj nową parę klucz-wartość.
        // calculate position to insert the new element
        int position = calculatePosition(key);

        // The first node at that index
        MapNode<TKey, TValue> head = arrayList.get(position);

        // First, loop through all the nodes present at that index
        // to check if the key already exists
        while (head != null) {
            // If already present the value is updated
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }
        try {
            add(key, value);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Pair(" + key + ", " + value + ") inserted successfully.\n");
        System.out.println("Number of pairs in the Map: " + current_size);
        System.out.println("Size of Map: " + current_size + "\n");
    }

    public TValue remove(TKey key) {
        // TODO: Usuń parę klucz-wartość, której klucz jest równy podanej wartości.
        int position = calculatePosition(key);
        MapNode<TKey, TValue> head = arrayList.get(position);
        MapNode<TKey, TValue> previous = head;
        while (head != null) {
        if (head.key.equals(key)) {
            arrayList.set(position, head.next);
            elementsQuantity--;
            return head.value;
        }
        previous = head;
        head = head.next;
        }
        return null;
    }

    public int size() {
        // TODO: Zwróć obecny rozmiar HashMap.
        return current_size;
    }

    private int calculatePosition(TKey key) {
        return (hashFunction.apply(key) % current_size);
    }
}
