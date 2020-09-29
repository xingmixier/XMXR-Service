package xmxrProject.genServer.common.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class C_ListUtil {
    public static <E> List<E> toArrayList(E... es) {
        List<E> list = new ArrayList<E>();
        for (E e : es) {
            list.add(e);
        }
        return list;
    }

    public static <E> List<E> toLinkedList(E... es) {
        List<E> list = new LinkedList<>();
        for (E e : es) {
            list.add(e);
        }
        return list;
    }



}
