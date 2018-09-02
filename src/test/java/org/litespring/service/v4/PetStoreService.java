package org.litespring.service.v4;

import org.litespring.dao.v2.ItemDao;
import org.litespring.dao.v4.AccountDao;
import org.litespring.stereotype.Autowired;
import org.litespring.stereotype.Component;

@Component("petStore")
public class PetStoreService {


    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ItemDao itemDao;


    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }
}
