package hashtable;

public class HashTable {

    public static void main(String[] args) {
        OpenAdressTable myTable = new OpenAdressTable();
        myTable.put(131, "Thor");
        myTable.print();
        myTable.put(131, "Loki");
        myTable.print();
        myTable.put(136, "Tony Stark");
        myTable.print();
        myTable.put(137, "Bucky");
        myTable.print();
        myTable.put(89, "Odin");
        myTable.print();
        System.out.println("=====================");
        System.out.println("Get");
        System.out.println("Get500   -> " + myTable.get(500)); //ищем ключ, которого нет
        System.out.println("Get131   -> "+ myTable.get(131));
        System.out.println("Get137   -> " + myTable.get(137));
        System.out.println("Get137   -> " + myTable.get(138));
        System.out.println("Get1310   -> " + myTable.get(1310));
        System.out.println("Get89   -> " + myTable.get(89));
        System.out.println("+++++++++++++++++++");
        System.out.println("Remove54");
        myTable.remove(54);
        myTable.print();
        System.out.println("Remove89");
        myTable.remove(89);
        myTable.print();
    }

}