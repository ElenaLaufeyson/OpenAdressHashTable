package myhashtable;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OpenAdressTable<T1,T2> implements Map<T1,T2>, HFunction<T1> {

    private Data<T1,T2> table[];
    private int sizeOfTable = 4;
    private int freePlaces;
    private int howEnlarge = 2; //на сколько увеличить таблицу

    public OpenAdressTable() {
        this.table = new Data[sizeOfTable];
        this.freePlaces = sizeOfTable;
    }

    public OpenAdressTable(int sizeOfTable) {
        this.sizeOfTable = sizeOfTable;
        this.table = new Data[sizeOfTable];
        this.freePlaces = sizeOfTable;
    }
    
    public int keyIndex(T1 key) {
        return keyIndex(key, table);
    }
    
    private int keyIndex(T1 key, Data tab[]) {
        int hashIndex = hashFunction(key);
        if (tab[hashIndex] == null)
            return -1; //нет такого индекса
        int i = hashIndex;
        do {
            if (tab[i] == null) {
                i=(i+1)%tab.length;
                continue;
            }
            if (!tab[i].isFree() && tab[i].getKey().equals(key))
                return i;
            i=(i+1)%tab.length;
        } while (i!=hashIndex);
        return -1; //нет такого индекса в таблице
    }
    
    @Override
    public T2 put(T1 key, T2 value) {
        T2 oldValue;
        oldValue = put(key, value, table);
        if (freePlaces < 0) { //мест нет
            sizeOfTable = table.length + howEnlarge; //увеличиваем таблицу
            freePlaces = sizeOfTable; //создаем новую пустую увеличенную таблицу
            Data newTable[] = new Data[sizeOfTable];
            for (int i=0;i<table.length;i++) { //из старой таблицы пихаем все в новую
                put(table[i].getKey(),table[i].getValue(), newTable);
            }
            oldValue = put(key, value, newTable);
            table = newTable;
            return oldValue;
        }
        return oldValue;
    }
    
    private T2 put(T1 key, T2 value, Data tab[]) {
        int keyInd = keyIndex(key, tab);
        if (keyInd != -1) { //в таблице есть такой ключ
            T2 oldValue = (T2)tab[keyInd].getValue();
            table[keyInd].setValue(value);
            return oldValue;
        }
        //такого ключа нет => ищем первое свободное место
        int hashIndex = hashFunction(key);
        int i = hashIndex;
        do {
            if (tab[i] == null) {
                tab[i] = new Data(key, value);
                freePlaces--;
                return null;
            }
            if (tab[i].isFree()) {
                tab[i].addData(key, value);
                freePlaces--;
                return null;
            }
            i = (i+1)%tab.length;
        } while (i!=hashIndex);
        freePlaces--;
        return null;
    }

    @Override
    public T2 get(Object key) {
        int keyInd = keyIndex((T1)key);
        if (keyInd == -1)
            return null;
        return table[keyInd].getValue();
    } 
    
    @Override
    public T2 remove(Object key) {
        int keyInd = keyIndex((T1)key);
        if (keyInd == -1)
            return null;
        table[keyInd].changeFreedom();
        freePlaces++;
        return table[keyInd].getValue();
    }

    @Override
    public int size() {
        return table.length;
    }
    
    @Override
    public boolean isEmpty() {
        for (int i=0;i<table.length;i++) {
            if (table[i] != null || !table[i].isFree())
                return false;
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i=0;i<table.length;i++) {
            if (table[i] != null)
                table[i].changeFreedom();
        }
        freePlaces = table.length;
    }

    @Override
    public boolean containsKey(Object key) {
        if (keyIndex((T1)key) == -1)
            return false;
        return true;
    }
    
    
    @Override
    public boolean containsValue(Object value) {
       for (int i=0;i<table.length;i++) {
           if (table[i] == null || table[i].isFree())
               continue;
           if (table[i].getValue().equals((T2)value))
               return true;
       }
       return false;
    }
    
    @Override
    public Set<T1> keySet() {
        Set<T1> allKeys = new LinkedHashSet<>();
        for (int i=0;i<table.length;i++) {
            if (table[i] == null || table[i].isFree())
                continue;
            allKeys.add(table[i].getKey());
        }
        return allKeys;
    }

    
    @Override
    public Set<Entry<T1, T2>> entrySet() {
        Set<Entry<T1,T2>> allData = new LinkedHashSet<>();
        for (int i=0;i<table.length;i++) {
            if (table[i] == null || table[i].isFree())
                continue;
            allData.add(new Data(table[i].getKey(), table[i].getValue()));
        }
        return allData;
    }
    
    @Override
    public void putAll(Map<? extends T1, ? extends T2> m) {
        for (Map.Entry entry: m.entrySet()) {
            Data<T1,T2> dat = (Data)entry;
            T1 key = dat.getKey();
            T2 value = dat.getValue();
            put(key, value);
        }
    }
    
    @Override
    public Collection<T2> values() {
        List list = new LinkedList();
        for (int i=0;i<table.length;i++) {
            if (table[i] == null || table[i].isFree())
                continue;
            list.add(table[i].getValue());
        }
        return (Collection)list;
    }
    
    @Override
    public int hashFunction(T1 key) {
        int i = key.hashCode()%sizeOfTable; //индексы в диапазоне от 0 до размера таблицы-1
        return i;
    }
    
    void print() {
        int hashIndex;
        for (int i=0;i<table.length;i++) {
            if (table[i] == null)
                System.out.println("null");
            else if (table[i].isFree()) {
                hashIndex = hashFunction((T1)table[i].getKey());
                System.out.println(hashIndex + "   [" + i + "]    free");
            }
            else {
              hashIndex = hashFunction((T1)table[i].getKey());
              System.out.println(hashIndex + "   [" + i + "]   " + table[i]);
            }
        }
        System.out.println("");            
    }
    
}
