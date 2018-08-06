import java.util.LinkedList;
import java.util.Queue;
public class LinearProbingMap<Key, Value> implements Map<Key, Value> {
   
    private int M;
    private int DEFAULT_M_VALUE = 997;
    private int N;
    private Entry<Key, Value>[] st;


    public LinearProbingMap(){
        M = DEFAULT_M_VALUE;
        st = new Entry[M];
    }
    private void expandCapacity(){
        Entry[] temp = new Entry[M * 2];
        for(int i = 0; i < st.length; i ++){
            temp[i] = st[i];
        }
        st = temp;
    }
    private int hash(Key k, int i){

        return ((k.hashCode() & 0x7ffffff) + i) % M;
    }

    /**
     * Puts a key-value pair into the map.
     *
     * @param key Key to use.
     * @param val Value to associate.
     */
    @Override
    public void put(Key key, Value val) {
        if(!contains(key)){
            int coll = 1;
            int hashed = hash(key, coll);
            while(null != st[hashed]){
                if(coll == M){
                    coll = 0;
                }
                hashed = hash(key, coll++);
            }
            st[hashed] = new Entry(key, val);
            N++;
        }else{
            st[hash(key, 1)] = new Entry(key, val);
        }
    }

    /**
     * Gets the value paired with a key. If the key doesn't exist in map,
     * returns null.
     *
     * @param key Key to use.
     * @return Value associated with key.
     */
    @Override
    public Value get(Key key) {
        Entry e = st[hash(key, 1)];
        if(null == e){
            return null;
        }else{
            return (Value) e.value;
        }
    }

    /**
     * Removes a key (and its associated value) from the map.
     *
     * @param key Key to use.
     */
    @Override
    public void remove(Key key) {
        Entry e = st[hash(key, 1)];
        if(null != e){
            st[hash(key,1)] = null;
            N--;
        }
    }

    /**
     * Checks if the map contains a particular key.
     *
     * @param key True if map contains key, false otherwise.
     * @return Result of check.
     */
    @Override
    public boolean contains(Key key) {
        Entry e = st[hash(key, 1)];
        if(null != e){
            return true;
        }
        return false;
    }

    /**
     * Returns true if there are no key-vale pairs stored in the map, and false
     * otherwise.
     *
     * @return True if map is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return N < 1;
    }

    /**
     * Returns the number of key-value pairs in the map.
     *
     * @return Number of key-value pairs.
     */
    @Override
    public int size() {
        return N;
    }

    /**
     * Returns an Iterable object containing all keys in the table. Keys not
     * guaranteed to be in any particular order.
     *
     * @return Iterable object containing all keys.
     */
    @Override
    public Iterable<Key> keys() {
        Queue<Key> keys = new LinkedList<Key>();
        for(int i = 0; i < M; i++){
            if(null != st[i]){
                keys.add(st[i].key);
            }
        }
        return keys;
    }
}
