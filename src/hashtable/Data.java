
package hashtable;

public class Data <T1, T2> {

    private T1 key;
    private T2 value;
    private boolean freedom; //свободна ли ячейка(псевдоудаление)

    public Data(T1 key, T2 value) {
        addData(key, value);
    }

    void addData(T1 key, T2 value) {
        this.key = key;
        this.value = value;
        this.freedom = false; //ячейка занята
    }

    public T1 getKey() {
        return key;
    }

    public T2 getValue() {
        return value;
    }

    void changeFreedom() {
        freedom = true; //ячейка свободна, функция для псевдоудаления
    }

    boolean isFree() {
        return freedom;
    }

    public void setValue(T2 value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String keyAndValue = key.toString() + " = " + value.toString();
        return keyAndValue;
    }



}
