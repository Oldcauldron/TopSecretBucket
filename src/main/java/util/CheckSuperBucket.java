package util;

import java.lang.reflect.InvocationTargetException;

public class CheckSuperBucket {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        SmirnovMap<String, String> map = new SmirnovMap<>();
        map.put("One", "1");
        map.put("Two", "2");
        map.put("Three", "3");
        for (int i = 0; i < 500; i++) {
            map.put(String.valueOf(i), i + "Second");
        }
        map.getBucket("Two", true);
        map.getBucket("One", false);

    }
}
