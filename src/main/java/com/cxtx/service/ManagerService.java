package com.cxtx.service;

import com.cxtx.entity.Manager;

/**
 * Created by jinchuyang on 16/10/19.
 */
public interface ManagerService {
    Manager findByTelAndPassordAndAlive(String tel, String password);

    Manager addManager(Manager manager);
}
