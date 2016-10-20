package com.cxtx.service.impl;

import com.cxtx.dao.ManagerDao;
import com.cxtx.entity.Manager;
import com.cxtx.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jinchuyang on 16/10/19.
 */
@Service("ManagerService")
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDao managerDao;
    @Override
    public Manager findByTelAndPassordAndAlive(String tel, String password) {
        if (tel == null) {
            return null;
        }
        return managerDao.findByTelAndPasswordAndAlive(tel,password,1);
    }

    @Override
    public Manager addManager(Manager manager) {
        if (manager == null){
            return  null;
        }
        manager.setAlive(1);
        return managerDao.save(manager);

    }
}
