package xmxrProject.genServer.mods.usermod.service;

import xmxrProject.genServer.common.baseMod.BaseService;
import xmxrProject.genServer.mods.usermod.dao.UserDao;
import xmxrProject.genServer.mods.usermod.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Aether 行米希尔
 * @Date 2020/7/11 16:47
 */
@Service
@Transactional(readOnly = true)
public class UserService extends BaseService<UserDao, User> {

}
