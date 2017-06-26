package com.dvorobjov.controller;

import com.dvorobjov.logic.AccountLogic;
import com.dvorobjov.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Dmytro Vorobiov on 13.06.2017.
 */
@RestController
public class AccountController {
    @Autowired
    AccountLogic businessHandler;

    @RequestMapping(method = RequestMethod.GET, path = "/account")
    public Account read(@RequestParam(value = "id") Long id) {
        return businessHandler.read(id);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/account")
    public Account create(@RequestParam(value = "name") String name, @RequestParam(value = "surname") String surname, @RequestParam(value = "email") String email) {
        return businessHandler.create(name, surname, email);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/account")
    public Account update(@RequestParam(value = "id") Long id, @RequestParam(value = "name") String name, @RequestParam(value = "surname") String surname,
                       @RequestParam(value = "email") String email) {
        return businessHandler.update(id, name, surname, email);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/account")
    public void delete(@RequestParam(value = "id") Long id) {
        businessHandler.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/account/find")
    public List<Account> find(@RequestParam(value = "emailPattern") String emailPattern) {
        return businessHandler.find(emailPattern);
    }
}
