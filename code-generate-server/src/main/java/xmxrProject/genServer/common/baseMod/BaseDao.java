package xmxrProject.genServer.common.baseMod;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Aether 行米希尔
 * @Date 2020/7/7 21:59
 * @Version 1.0
 */
@Mapper
public interface BaseDao<E> extends $XMXR {
    /**
     * 添加单条数据
     *
     * @param e
     * @return
     */
    void insert(E e);

    /**
     * 添加多条数据
     *
     * @param es
     * @return
     */
    void insertList(List<E> es);

    /**
     * 查询一条数据
     *
     * @param e
     * @return
     */
    E select(E e);

    /**
     * 查询多条数据
     *
     * @param e
     * @return
     */
    List<E> selectList(E e);

    /**
     * 更新一条数据
     *
     * @param e
     * @return
     */
    Integer update(E e);

    /**
     * 删除一条数据
     *
     * @param e
     * @return
     */
    Integer delete(E e);

    /**
     * 删除全部数据
     *
     * @return
     */
    Integer deleteAll();
}
