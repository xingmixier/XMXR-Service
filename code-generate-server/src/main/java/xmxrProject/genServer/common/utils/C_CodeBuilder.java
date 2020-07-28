package xmxrProject.genServer.common.utils;


import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import static xmxrProject.genServer.common.utils.C_StringUtil.trimText;
import static xmxrProject.genServer.common.utils.C_StringBuilderUtil.replace;

/**
 * @Aether 行米希尔
 * @Date 2020/7/7 22:21
 * @Version 1.0
 * 根据模板生成文件 支持 list 迭代 与 if 判断 但不支持 嵌套
 */

public class C_CodeBuilder {

    private final String TAG_START = "${";
    private final String TAG_END = "}";
    private final String LIST_START = "$LIST{";
    private final String LIST_END = "}LIST$";
    private final String IF_START = "$IF{";
    private final String IF_END = "}IF$";
    private StringBuilder bodyText;
    private StringBuilder pText;
    private Map<String, Object> result;
    private List<String> judgeSymbols = new ArrayList<>();


    /**
     * 初始化构造方法构造代码生成器对象
     *
     * @param template 代码模板
     * @param map      数据集合
     */
    public C_CodeBuilder(StringBuilder template, Map<String, Object> map) {
        if (template == null | template.length() == 0) {
            throw new RuntimeException("模板为空");
        }
        bodyText = template;
        result = map;
        initJudgeSymbols();
    }

    /**
     * 初始化判断符号集合
     */
    private void initJudgeSymbols() {
        judgeSymbols.add("<");
        judgeSymbols.add(">");
        judgeSymbols.add("=");
        judgeSymbols.add("!=");
        judgeSymbols.add("<=");
        judgeSymbols.add(">=");
        judgeSymbols.add("eq");
    }

    /**
     * 获取集合对应属性
     *
     * @param fieldName
     * @return
     */
    private Object getFiled(String fieldName) {
        try {
            String[] fieldNames = fieldName.split("\\.");
            Class cls;
            Object o;
            if (fieldNames.length == 1) {
                o = result.get(fieldName);
                if (o == null) {
                    return fieldName;
                }
                return o;
            }
            o = result.get(fieldNames[0]);
            checkO(o, fieldNames[0]);
            for (int i = 1; i < fieldNames.length; i++) {
                cls = o.getClass();
                Field f = cls.getDeclaredField(fieldNames[i]);
                f.setAccessible(true);
                o = f.get(o);
                if (i < fieldNames.length - 1) {
                    checkO(o, fieldNames[i]);
                }
            }
            return o;
        } catch (NoSuchFieldException e) {
            System.out.println("不匹配的属性名");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("非法获取属性");
            e.printStackTrace();
        }
        return fieldName;
    }

    /**
     * 生成代码字符串
     *
     * @return
     * @throws IOException
     */
    public String generateCode() throws IOException {
        writeAllList();
        writeAllIf();
        writeALlField();
        return bodyText.toString();
    }

    /**
     * 生成 全部 list 标签代码
     */
    private void writeAllList() {
        int start = -1;
        while ((start = bodyText.indexOf(LIST_START)) != -1) {
            int end = end = bodyText.indexOf(LIST_END);
            pText = new StringBuilder(bodyText.subSequence(start, end + LIST_END.length()));
            writeList(start, end + LIST_END.length());
        }
    }

    /**
     * 生成 带个 list 标签代码
     *
     * @param pTextStart
     * @param pTextEnd
     */
    private void writeList(int pTextStart, int pTextEnd) {
        String text = pText.substring(LIST_START.length(), pText.length() - LIST_END.length());
        final String listName;
        final String itemName;
        String[] texts = text.split(":");
        String[] regs = texts[0].split("as");
        listName = regs[0].trim();
//        itemName = regs[1].trim();// 未使用  嵌套时 使用
        List list = (List) result.get(listName);
        int nextTextStart = text.indexOf(':');
        StringBuilder nextText = new StringBuilder(text.substring(nextTextStart + 1));
        StringBuilder newNextText = new StringBuilder();
        ;
        for (int index = 0; index < list.size(); index++) {
            StringBuilder newText = new StringBuilder(nextText);
            Object item = list.get(index);
            int fieldStart = -1;
            while ((fieldStart = newText.indexOf(TAG_START)) != -1) {
                int fieldEnd = newText.indexOf(TAG_END);
                String fieldText = newText.substring(fieldStart + TAG_START.length(), fieldEnd);
                String fieldTextKey = TAG_START + fieldText + TAG_END;
                String fieldTextValue;
                String[] strings = fieldText.split("\\.");
                if (strings.length == 1) {
                    fieldTextValue = (String) result.get(fieldTextKey);
                } else {
                    fieldTextValue = getListFieldTextValue(item, strings);
                }
                replace(newText, fieldTextKey, fieldTextValue);
            }
            newNextText.append(trimText(newText.toString())).append("\r\n");
        }
        replace(bodyText, pText.toString(), newNextText.toString().trim());
    }


    /**
     * 获取 list 标签 迭代 对象 属性值
     *
     * @param item
     * @param strings
     * @return
     */
    private String getListFieldTextValue(Object item, String[] strings) {
        Class cls = item.getClass();
        Field field = null;
        for (int i = 1; i < strings.length; i++) {
            String fieldName = strings[i];
            try {
                field = cls.getDeclaredField(fieldName);
                field.setAccessible(true);
                item = field.get(item);
                cls = item.getClass();
            } catch (NoSuchFieldException e) {
                System.out.println("属性名不对");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                System.out.println("属性非法");
                e.printStackTrace();
            }
        }
        return item.toString();
    }


    /**
     * 生成 所有 if标签 代码
     */
    private void writeAllIf() {
        int start = -1;
        while ((start = bodyText.indexOf(IF_START)) != -1) {
            int end = end = bodyText.indexOf(IF_END);
            pText = new StringBuilder(bodyText.subSequence(start, end + IF_END.length()));
            writeIf(start, end + IF_END.length());
        }
    }

    /**
     * 生成 单个 if标签 代码
     *
     * @param start
     * @param end
     */
    private void writeIf(int start, int end) {
        String text = pText.substring(IF_START.length(), pText.length() - IF_END.length());
        String[] strings = text.split(":");
        String expression = strings[0].trim();
        String nextText = trimText(strings[1]);
        String[] expressionStrs;
        int index = 0;
        while (((expressionStrs = expression.split(judgeSymbols.get(index))).length == 1)) index++;
        String field1 = expressionStrs[0].trim();
        String field2 = expressionStrs[1].trim();
        String judgeSymbol = judgeSymbols.get(index);
        boolean flag = getBoolean(field1, field2, judgeSymbol);
        if (flag) {
            replace(bodyText, pText.toString(), nextText);
        } else {
            replace(bodyText, pText.toString(), "");
        }

    }

    /**
     * 获取 if标签 中表达式 boolean值
     *
     * @param field1
     * @param field2
     * @param judgeSymbol
     * @return
     */
    private boolean getBoolean(String field1, String field2, String judgeSymbol) {
        double d1;
        double d2;
        Object o1;
        Object o2;
        switch (judgeSymbol) {
            case "<":
                d1 = Double.parseDouble(field1);
                d2 = Double.parseDouble(field2);
                return d1 < d2;
            case ">":
                d1 = Double.parseDouble(field1);
                d2 = Double.parseDouble(field2);
                return d1 > d2;
            case "<=":
                d1 = Double.parseDouble(field1);
                d2 = Double.parseDouble(field2);
                return d1 <= d2;
            case ">=":
                d1 = Double.parseDouble(field1);
                d2 = Double.parseDouble(field2);
                return d1 >= d2;
            case "=":
                if (field1.equals("null")) {
                    o2 = getFiled(field2);
                    return null == o2;
                }
                if (field2.equals("null")) {
                    o1 = getFiled(field1);
                    return o1 == null;
                }
                d1 = Double.parseDouble(field1);
                d2 = Double.parseDouble(field2);
                return d1 == d2;
            case "!=":
                if (field1.equals("null")) {
                    o2 = getFiled(field2);
                    return null != o2;
                }
                if (field2.equals("null")) {
                    o1 = getFiled(field1);
                    return o1 != null;
                }
                d1 = Double.parseDouble(field1);
                d2 = Double.parseDouble(field2);
                return d1 != d2;
            case "eq":
                o1 = getFiled(field1);
                o2 = getFiled(field2);
                return o1.equals(o2);
        }
        return false;
    }


    /**
     * 检查对象是否为null
     *
     * @param o
     * @param fieldName
     */
    private void checkO(Object o, String fieldName) {
        if (o == null) {
            throw new RuntimeException("fieldName " + fieldName + " 不存在");
        }
    }


    /**
     * 生成 所有 属性标签
     */
    private void writeALlField() {
        int start = -1;
        while ((start = bodyText.indexOf(TAG_START)) != -1) {
            int end = end = bodyText.indexOf(TAG_END);
            pText = new StringBuilder(bodyText.subSequence(start, end + TAG_END.length()));
            writeField(start, end + TAG_END.length());
        }
    }

    /**
     * 生成 单个 属性标签
     *
     * @param start
     * @param end
     */
    private void writeField(int start, int end) {
        String text = pText.substring(TAG_START.length(), pText.length() - TAG_END.length());
        Object o = getFiled(text);
        replace(bodyText, pText.toString(), o == null ? "" : o.toString());
    }


}
