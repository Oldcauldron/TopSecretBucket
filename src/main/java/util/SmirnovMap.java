package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class SmirnovMap<K, V> extends HashMap<K, V> {
    final Method[] methods = HashMap.class.getDeclaredMethods();
    final Field[] declaredFields = HashMap.class.getDeclaredFields();
    final Class<?>[] declaredClasses = HashMap.class.getDeclaredClasses();
    Method smirnovHashmethod = null;
    Field smirnovTableField = null;
    Class<?> node = null;
    //Class[] smirnovNode = new Class[1];

    public void getBucket(K key, boolean showAll) throws InvocationTargetException, IllegalAccessException {
        if (!checkKey(key)) return;
        reflUtil();
        Entry[] nodesTable = (Entry[]) smirnovTableField.get(this);
        int lengthTable = nodesTable.length;
        long numFreeBuckets = Arrays.stream(nodesTable).filter(Objects::isNull).count();
        int hash = (int)smirnovHashmethod.invoke(this, key);
        int bucket = (lengthTable-1) & hash;
        if (showAll) for (int i = 0; i < nodesTable.length; i++) {System.out.println(i + " bucket: " + nodesTable[i]);}
        String buff = ((bucket<10)?"   " : (bucket>9&bucket<100) ?"  " : " ");

        String idealBucket = String.format(
        "          super secret map layer          \n" +
        "******************************************\n" +
        "              top secret                  \n" +
        "        bucket with your key:             \n" +
        "                                          \n" +
        "            \\           /                \n" +
        "             \\         /                 \n" +
        "              \\  %d%s /                  \n" +
        "               \\-----/                   \n" +
        "                \\___/                    \n" +
        " free buckets: %d ; all buckets: %d       \n" +
        " =========================================\n"
        , bucket, buff, numFreeBuckets, lengthTable);
        System.out.println(idealBucket);
    }

    void reflUtil() {
        smirnovHashmethod = Arrays.stream(methods).peek(x -> x.setAccessible(true)).filter(x -> x.getName().equals("hash")).findFirst().get();
        smirnovTableField = Arrays.stream(declaredFields).peek(x -> x.setAccessible(true)).filter(x -> x.getName().equals("table")).findFirst().get();
        node = Arrays.stream(declaredClasses).filter(x -> x.getName().equals("java.util.HashMap$Node")).findFirst().get();
    }
    boolean checkKey(K key) {
        if (this.containsKey(key)) return true;
        System.out.println("This key does not exist :(");
        return false;
    }

}
//final Field field = Arrays.stream(node.getDeclaredFields()).peek(x -> x.setAccessible(true)).filter(x -> x.getName().equals("next")).findFirst().get();
