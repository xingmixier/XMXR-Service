package xmxrProject.genServer.common.baseMod;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Aether 行米希尔
 * @Date 2020/7/7 21:59
 * @Version 1.0
 */
public class BaseEntity<E> extends BaseClass {

    protected Integer pkId;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public String toString() {
            try {
                return this.println();
            } catch (IllegalAccessException e) {
                return this.getClass().getName() + "@" + Integer.toHexString(this.hashCode());
            }
    }
}
