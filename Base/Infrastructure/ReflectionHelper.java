package Base.Infrastructure;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mateusz on 16/01/2016.
 * Helper class used to get all (own and inherited) fields from object.
 */
public class ReflectionHelper {
    public static List<Field> getAllFields(Class<?> type) {
        return getAllFields(type, new ArrayList<>());
    }

    private static List<Field> getAllFields(Class<?> type, List<Field> fields) {
        if (type.getSuperclass() != null)
            fields = getAllFields(type.getSuperclass(), fields);

        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        return fields;
    }
}