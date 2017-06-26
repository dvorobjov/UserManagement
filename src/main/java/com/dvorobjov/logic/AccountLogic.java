package com.dvorobjov.logic;

import com.dvorobjov.TechnicalException;
import com.dvorobjov.model.Account;
import com.dvorobjov.storage.AccountStorage;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Dmytro Vorobiov on 14.06.2017.
 */
@Validated
@Service
public class AccountLogic {
    @Autowired
    AccountStorage storage;

    public Account create(@Valid @NotEmpty String name, @Valid @NotEmpty String surname, @Valid @NotEmpty String email) {
        Account account = new Account(name, surname, email);
        return storage.save(account);
    }

    public Account read(@Valid @NotNull Long accountId) {
        return storage.load(accountId);
    }

    public Account update(@Valid @NotEmpty Long accountId, @Valid @NotEmpty String name, @Valid @NotEmpty String surname, @Valid @NotEmpty @Email String email) {
        Account account = new Account(accountId, name, surname, email);
        return storage.save(account);
    }

    public void delete(@Valid @NotNull Long accountId) {
        storage.delete(accountId);
    }

    public List<Account> find(@Valid @Email String emailPattern) {
        return storage.find(emailPattern);
    }

}
