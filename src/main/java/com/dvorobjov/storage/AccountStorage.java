package com.dvorobjov.storage;

import com.dvorobjov.model.Account;

import java.util.List;

/**
 * Created by Dmytro Vorobiov on 14.06.2017.
 */
public interface AccountStorage {

    /**
     * Load account record by id
     * @param id
     * @return account found or null if found nothing
     */
    public Account load(long id);

    /**
     * Create or update account in the storage
     * @param account
     * @return true if operation successful and false if not
     */
    public Account save(Account account);

    /**
     * Return accounts with emails match <code>emailPattern</code>. If no accounts math return empty list
     * @param emailPattern <code>java.util.regex.Pattern</code> statement
     * @return list of accounts. If no accounts math return empty list
     */
    public List<Account> find(String emailPattern);


    /**
     * Delete account record by id
     * @param id
     */
    public void delete(long id);
}
