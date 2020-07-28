package xmxrProject.genServer.common.baseMod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Aether 行米希尔
 * @Date 2020/7/7 21:59
 * @Version 1.0
 */
@Service
@Transactional(readOnly = true)
public class BaseService<D extends BaseDao<E>, E extends BaseEntity<E>> extends BaseClass{


    /**
     * dao 对象
     */
    @Autowired
    private D baseDao;


    /**
     * 添加单条数据
     *
     * @param e
     * @return
     */
    @Transactional(readOnly = false)
    public void insert(E e) {
        baseDao.insert(e);
    }

    /**
     * 添加多条数据
     *
     * @param es
     * @return
     */
    @Transactional(readOnly = false)
    public void insertList(List<E> es) {
        baseDao.insertList(es);
    }

    /**
     * 查询一条数据
     *
     * @param e
     * @return
     */
    public E select(E e) {
        return baseDao.select(e);
    }

    /**
     * 查询多条数据
     *
     * @param e
     * @return
     */
    public List<E> selectList(E e) {
        return baseDao.selectList(e);
    }

    /**
     * 更新一条数据
     *
     * @param e
     * @return
     */
    @Transactional(readOnly = false)
    public Integer update(E e) {
        return baseDao.update(e);
    }

    /**
     * 删除一条数据
     *
     * @param e
     * @return
     */
    @Transactional(readOnly = false)
    public Integer delete(E e) {
        return baseDao.delete(e);
    }

    /**
     * 删除全部数据
     *
     * @return
     */
    @Transactional(readOnly = false)
    public Integer deleteAll() {
        return baseDao.deleteAll();
    }

}
