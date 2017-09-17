package hashtable;

public class OpenAdressTable <T1, T2> {

    private Data table[];
    private int sizeOfTable = 4;

    public OpenAdressTable() {
        this.table = new Data[sizeOfTable];
    }

    public OpenAdressTable(int sizeOfTable) {
        this.sizeOfTable = sizeOfTable;
        this.table = new Data[sizeOfTable];
    }

    void put(T1 key, T2 value) {
        int hashIndex = hashFunction(key); //индекс в таблице
        int freeIndex = -1; //индекс свободной ячейки
        int i = hashIndex;
        do {
            if (table[i] == null) { //если ячейка свободна
                if (freeIndex == -1) //первое найденное свободное место
                    freeIndex = i;
                i = (i+1)%table.length;
                continue;
            }
            if (table[i].isFree()) { //если ячейка была псевдоудалена
                if (freeIndex == -1)
                    freeIndex = i;
            }
            //такой ключ есть и ячейка занята, то просто перезапишем значение
            if (table[i].getKey().equals(key) && !table[i].isFree()) {
                table[i].setValue(value);
                return;
            }
            i = (i+1)%table.length;
        } while (i!= hashIndex);
        if (freeIndex != -1) { //если нашлась свободная ячейка
            if (table[freeIndex] == null)
                table[freeIndex] = new Data(key, value);
            else table[freeIndex].addData(key, value);
        }
    }

    T2 get(T1 key) {
        int hashIndex = hashFunction(key);
        if (table[hashIndex] == null)
            return null;
        if (table[hashIndex].getKey().equals(key))
            return (T2)table[hashIndex].getValue();
        int start = hashIndex+1;
        if (start == table.length)
            start = 0;
        for (int i=start;i!=hashIndex;i=(i+1)%table.length) {
            if (table[i] == null) //ячейка пуста
                continue;
            //ячейка свободна и ключи совпадают
            if (!table[i].isFree() && table[i].getKey().equals(key)) {
                return (T2)table[i].getValue();
            }
        }
        return null; //ничего не нашли
    }

    //вернет то, что удалили
    T2 remove(T1 key) {
        int hashIndex = hashFunction(key);
        if (table[hashIndex] == null)
            return null;
        if (table[hashIndex].getKey().equals(key)) {
            T2 value = (T2)table[hashIndex].getValue();
            table[hashIndex].changeFreedom();
            return value;
        }
        int start = hashIndex+1;
        if (start == table.length)
            start = 0;
        for (int i=start;i!=hashIndex;i=(i+1)%table.length) {
            if (table[i] == null) //ячейка пуста
                continue;
            //ячейка свободна и ключи совпадают
            if (!table[i].isFree() && table[i].getKey().equals(key)) {
                T2 value = (T2)table[i].getValue();
                table[i].changeFreedom();
                return value;
            }
        }
        return null; //ничего не нашли
    }

    private int hashFunction(T1 key) {
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