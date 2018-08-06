import java.util.LinkedList;
import java.util.Queue;

public class TwoProbeChainMap<Key, Value> implements  Map<Key, Value> {
    private int N;
    private int M;
    private LinkedList<Entry>[] st;
    private int DEFAULT_M_SIZE = 997;


    public TwoProbeChainMap(int M){
        this.M = M;
        st = new LinkedList[M];
        for(int i = 0; i < M; i++){
            st[i] = new LinkedList<Entry>();
        }
        N = 0;
    }
    public TwoProbeChainMap(){

        this.M = DEFAULT_M_SIZE;
        st = new LinkedList[M];
        for(int i = 0; i < M; i++){
            st[i] = new LinkedList<Entry>();
        }
        N = 0;

    }

    private int hash(Key k){
        return (k.hashCode() & 0x7fffffff) % M;
    }

    private int hash2(Key k){
        return (((k.hashCode() & 0x7fffffff) % M) * 31) % M;
        
    }
    /**
     * Puts a key-value pair into the map.
     *
     * @param key Key to use.
     * @param val Value to associate.
     */
    @Override
    public void put(Key key, Value val) {
        boolean added = false;
        if(!contains(key)) {
            if (st[hash(key)].size() < st[hash2(key)].size()) {
                for (Entry entry : st[hash(key)]) {
                    if (key.hashCode() == entry.key.hashCode()) {
                        entry.value = val;
                        added = true;
                    }
                }
                if (!added) {
                    st[hash(key)].add(new Entry(key, val));
                    N++;
                }
            } else {
                for (Entry entry : st[hash2(key)]) {
                    if (key.hashCode() == entry.key.hashCode()) {
                        entry.value = val;
                        added = true;
                    }
                }
                if (!added) {
                    st[hash2(key)].add(new Entry(key, val));
                    N++;
                }
            }
        }else{
            for (Entry entry : st[hash(key)]) {
                if (key.hashCode() == entry.key.hashCode()) {
                    entry.value = val;
                    added = true;
                }
            }
            if(!added){
                for (Entry entry : st[hash2(key)]) {
                    if (key.hashCode() == entry.key.hashCode()) {
                        entry.value = val;
                    }
                }
            }
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

        for(Entry entry : st[hash(key)]){
            if(key.hashCode() == entry.key.hashCode()){
                return (Value) entry.value;
            }
        }
        for(Entry entry: st[hash2(key)]){
            if(key.hashCode() == entry.key.hashCode()){
                return (Value) entry.value;
            }
        }
        return null;
    }

    /**
     * Removes a key (and its associated value) from the map.
     *
     * @param key Key to use.
     */
    @Override
    public void remove(Key key) {
        boolean removed = false;
        for(int i = 0; i < st[hash(key)].size(); i++){
            if(key.hashCode() == st[hash(key)].get(i).key.hashCode()){
                st[hash(key)].remove(i);
                removed = true;
                N--;
                break;
            }
        }
        if(!removed){
            for(int i = 0; i < st[hash2(key)].size(); i++){
                if(key.hashCode() == st[hash2(key)].get(i).key.hashCode()){
                    st[hash2(key)].remove(i);
                    removed = true;
                    N--;
                    break;
                }
            }
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
        for(Entry entry : st[hash(key)]){
            if(key.hashCode() == entry.key.hashCode()){
                return true;
            }
        }
        for(Entry entry: st[hash2(key)]){
            if(key.hashCode() == entry.key.hashCode()){
                return true;
            }
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
        return size() < 1;
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
            for(Entry e: st[i]){
                keys.add((Key) e.key);
            }
        }
        return keys;
    }
}
