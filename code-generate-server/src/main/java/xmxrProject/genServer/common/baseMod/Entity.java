package xmxrProject.genServer.common.baseMod;

import xmxrProject.genServer.$XMXR;
import xmxrProject.genServer.common.utils.C_ClassUtil;
import xmxrProject.genServer.mods.usermod.entity.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: Entoty.java
 * @Author: 行米希尔
 * @Date: 2020/8/13 18:49
 * @Description:
 */
public  class Entity extends BaseEntity<Entity> {
    HashMap<String, Object> fields = new HashMap<>();
    HashMap<String, EntityMethod> methods = new HashMap<>();

    public Entity set(String fieldName, Object value) {
        fields.put(fieldName, value);
        return this;
    }

    public Object get(String fieldName) {
        return fields.get(fieldName);
    }


    public Entity setMethod(String methodName, EntityMethod method) {
        methods.put(methodName, method);
        return this;
    }

    public Object invoke(String methodName,Object...objects){
        return methods.get(methodName).method(objects);
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Entity e = new Entity();
        EntityMethod em = new EntityMethod() {
            @Override
            public Object method(Object... objects) {
                System.out.println("invoke this method");
                return null;
            }
        };
        e.setMethod("sout",em);
        e.invoke("sout");
        e.set("id",1);
        e.set("name","zs");
        e.set("pwd","123");
        System.out.println(e);
        e.c_get("");
    }

    public <E extends BaseEntity<E>>E toObject(E entity) throws NoSuchFieldException, IllegalAccessException {
        String fieldName;
        Object fieldValue;
        for(Map.Entry<String ,Object> e : fields.entrySet()){
            fieldName = e.getKey();
            fieldValue = e.getValue();
            entity.c_set(fieldName,fieldValue);
        }
        return entity;
    }

    public <E>E toObject(E entity) throws NoSuchFieldException, IllegalAccessException {
        String fieldName;
        Object fieldValue;
        for(Map.Entry<String ,Object> e : fields.entrySet()){
            fieldName = e.getKey();
            fieldValue = e.getValue();
            C_ClassUtil.setFieldValue(entity,fieldName,fieldValue);
        }
        return entity;
    }

    @Override
    public String toString() {
        return fields.toString();
    }


}//end
interface EntityMethod{
    Object method(Object... objects);
}
