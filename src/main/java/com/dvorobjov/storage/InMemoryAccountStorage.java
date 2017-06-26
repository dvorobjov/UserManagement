package com.dvorobjov.storage;

import com.dvorobjov.TechnicalException;
import com.dvorobjov.model.Account;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Dmytro Vorobiov on 14.06.2017.
 */
@Component
public class InMemoryAccountStorage implements AccountStorage {
    private final AtomicLong index = new AtomicLong();
    private final ConcurrentHashMap<Long, Account> map = new ConcurrentHashMap<Long, Account>();

    @PostConstruct
    public void init() {
    }

    public Account load(long id) {
        return map.get(id);
    }

    public Account save(Account account) {
        if (account == null) {
            throw new TechnicalException("Input parameter is null");
        }
        Account storedAccount = account.cloneInstance();
        if (!account.getId().isPresent()) {
            //existing item
            long id = index.incrementAndGet();
            storedAccount.setId(id);
            //I have no requirements to check for duplicates, if I get it I need to change key generation and need to use putIfAbsent map method
            // and throw BusinessException in case of duplicates detection
            map.put(id, storedAccount);
        } else {
            //new item
            map.put(storedAccount.getId().getAsLong(), storedAccount);
        }

        return storedAccount;
    }

    @Override
    public List<Account> find(String emailPattern) {
        return map.values().stream().parallel().filter(account -> account.getEmail().contains(emailPattern)).collect(Collectors.toList());
    }

    @Override
    public void delete(long id) {
        map.remove(id);
    }

}
