package myhashtable;

public class MyHashTable {

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
        System.out.println("Get500");
        System.out.println(myTable.get(500)); //ищем ключ, которого нет
        System.out.println("Get131");
        System.out.println(myTable.get(131));
        System.out.println("+++++++++++++++++++");
        System.out.println("Remove54");
        myTable.remove(54);
        myTable.print();
        System.out.println("Remove89");
        myTable.remove(89);
        myTable.print();
        System.out.println("=======================");
        myTable.put(99, "Steve Rogers");
        myTable.print();
        myTable.put(187, "Black Widow");
        myTable.print();
        myTable.put(17, "Clint Barton");
        myTable.print();
        myTable.put(147, "Nick Fury");
        myTable.print();
    }
    
}
