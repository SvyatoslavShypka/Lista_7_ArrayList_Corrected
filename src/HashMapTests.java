import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

// Proszę nie modyfikować tego pliku!
public class HashMapTests {

    @org.junit.jupiter.api.Test
    public void rehashTest() {
        var hashMap = createTestHashMap(3);
        hashMap.rehash(key -> key % 2);
        assertEquals(3, hashMap.elements());
        assertEquals(5, hashMap.size());
        assertEquals("Value1", hashMap.get(1));
        assertEquals("Value2", hashMap.get(2));
        assertEquals("Value3", hashMap.get(3));
    }

    @org.junit.jupiter.api.Test
    public void addKey_DoesntExist() throws DuplicateKeyException {
        var hashMap = createTestHashMap(3);
        hashMap.add(4, "four");
        assertEquals("four", hashMap.get(4));
        assertEquals(4, hashMap.elements());
    }

    @org.junit.jupiter.api.Test
    public void addKey_AlreadyExists() {
        var hashMap = createTestHashMap(3);
        assertThrows(DuplicateKeyException.class, () -> hashMap.add(3, "three"));
    }

    @org.junit.jupiter.api.Test
    public void addKey_LoadFactorExceeded() throws DuplicateKeyException {
        var hashMap = new HashMap<Integer, String>(5, 0.7, key -> key);
        hashMap.add(1, "one");
        hashMap.add(2, "two");
        hashMap.add(3, "three");
        assertEquals(3, hashMap.elements());
        assertEquals(5, hashMap.size());

        hashMap.add(4, "four");
        assertEquals(4, hashMap.elements());
        assertEquals(10, hashMap.size());
    }

    @org.junit.jupiter.api.Test
    public void clear_NonEmptyHashMap() {
        var hashMap = createTestHashMap(3);
        hashMap.clear();
        assertEquals(0, hashMap.elements());
        assertDoesNotThrow(() -> hashMap.add(1, "one"));
    }

    @org.junit.jupiter.api.Test
    public void clear_AlreadyEmptyHashMap() {
        var hashMap = createTestHashMap(0);
        hashMap.clear();
        assertEquals(0, hashMap.elements());
    }

    @org.junit.jupiter.api.Test
    public void containsKey_KeyExists() {
        var hashMap = createTestHashMap(3);
        assertTrue(hashMap.containsKey(2));
    }

    @org.junit.jupiter.api.Test
    public void containsKey_KeyDoesntExist() {
        var hashMap = createTestHashMap(3);
        assertFalse(hashMap.containsKey(4));
    }

    @org.junit.jupiter.api.Test
    public void containsValue_ValueExists() {
        var hashMap = createTestHashMap(3);
        assertTrue(hashMap.containsValue("Value2"));
    }

    @org.junit.jupiter.api.Test
    public void containsValue_ValueDoesntExist() {
        var hashMap = createTestHashMap(3);
        assertFalse(hashMap.containsValue("Value4"));
    }

    @org.junit.jupiter.api.Test
    public void elements_NotEmptyHashMap() {
        var hashMap = createTestHashMap(3);
        assertEquals(3, hashMap.elements());
    }

    @org.junit.jupiter.api.Test
    public void elements_EmptyHashMap() {
        var hashMap = createTestHashMap(0);
        assertEquals(0, hashMap.elements());
    }

    @org.junit.jupiter.api.Test
    public void get_KeyExists() {
        var hashMap = createTestHashMap(3);
        assertEquals("Value3", hashMap.get(3));
    }

    @org.junit.jupiter.api.Test
    public void get_KeyDoesntExist() {
        var hashMap = createTestHashMap(3);
        assertThrows(NoSuchElementException.class, () -> hashMap.get(4));
    }

    @org.junit.jupiter.api.Test
    public void put_KeyDoesntExist() {
        var hashMap = createTestHashMap(3);
        hashMap.put(4, "four");
        assertEquals("four", hashMap.get(4));
        assertEquals(4, hashMap.elements());
    }

    @org.junit.jupiter.api.Test
    public void put_KeyAlreadyExists() {
        var hashMap = createTestHashMap(3);
        hashMap.put(2, "two");
        assertEquals("two",
                hashMap.get(2));
        assertEquals(3, hashMap.elements());
    }

    @org.junit.jupiter.api.Test
    public void remove_KeyExists() {
        var hashMap = createTestHashMap(3);
        assertEquals("Value1", hashMap.remove(1));
        assertEquals(2, hashMap.elements());
    }

    @org.junit.jupiter.api.Test
    public void remove_KeyDoesntExist() {
        var hashMap = createTestHashMap(3);
        assertEquals(null, hashMap.remove(4));
        assertEquals(3, hashMap.elements());
    }

    private HashMap<Integer, String> createTestHashMap(int elements) {
        var hashMap = new HashMap<Integer, String>(5, 0.7, key -> key);

        try {
            for (var i = 1; i <= elements; i++) {
                hashMap.add(i, "Value" + i);
            }
        } catch (Exception exception) {
            fail(exception);
        }

        return hashMap;
    }
}
