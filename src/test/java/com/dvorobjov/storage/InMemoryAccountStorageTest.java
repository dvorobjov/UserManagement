package com.dvorobjov.storage;

import com.dvorobjov.TechnicalException;
import com.dvorobjov.model.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by Dmytro Vorobiov on 15.06.2017.
 */
public class InMemoryAccountStorageTest {
    private static final String TEST_NAME = "TEST_NAME";
    private static final String TEST_SURNAME = "TEST_SURNAME";
    private static final String TEST_EMAIL = "TEST_EMAIL";
    private static final int SAVE_AND_FIND_COUNTER = 100;
    private static final String UPDATE_PREFIX = "UPDATED_";

    private InMemoryAccountStorage storage;

    @Before
    public void setUp() {
        storage = new InMemoryAccountStorage();
    }

    @After
    public void tearDown() {

    }

    @Test (expected = TechnicalException.class)
    public void testSaveNull() {
        storage.save(null);
    }

    @Test
    public void testLoadNotExistAccount() {
        assertNull(storage.load(11111));
    }

    @Test
    public void testSaveAndLoad() {
        Account inputAccount = new Account(TEST_NAME, TEST_SURNAME, TEST_EMAIL);

        Account savedAccount = storage.save(inputAccount);
        assertNotNull(savedAccount);
        assertTrue(savedAccount.getId().isPresent());
        assertEquals(inputAccount.getName(), savedAccount.getName());
        assertEquals(inputAccount.getSurname(), savedAccount.getSurname());
        assertEquals(inputAccount.getEmail(), savedAccount.getEmail());

        Account loadAccount = storage.load(savedAccount.getId().getAsLong());
        assertNotNull(loadAccount);
        assertTrue(loadAccount.getId().isPresent());
        assertEquals(inputAccount.getName(), loadAccount.getName());
        assertEquals(inputAccount.getSurname(), loadAccount.getSurname());
        assertEquals(inputAccount.getEmail(), loadAccount.getEmail());
    }

    @Test
    public void testSaveAndFind() {
        List<Account> inputAccountsList = Stream.iterate(999999, n -> n + 1).limit(SAVE_AND_FIND_COUNTER).map(id -> new Account(TEST_NAME + id, TEST_SURNAME + id, TEST_EMAIL + id)).collect(Collectors.toList());

        inputAccountsList.stream().parallel().forEach(storage::save);

        List<Account> foundAccountsListByCommonPattern = storage.find(TEST_EMAIL);
        assertNotNull(foundAccountsListByCommonPattern);
        assertEquals(SAVE_AND_FIND_COUNTER, foundAccountsListByCommonPattern.size());

        inputAccountsList.stream().parallel().forEach(inputAccount -> {
            List<Account> foundAccountListBySpecificEmail = storage.find(inputAccount.getEmail());
            assertNotNull(foundAccountListBySpecificEmail);
            assertEquals(1, foundAccountListBySpecificEmail.size());

            Account foundAccount = foundAccountListBySpecificEmail.get(0);
            assertEquals(inputAccount.getName(), foundAccount.getName());
            assertEquals(inputAccount.getSurname(), foundAccount.getSurname());
            assertEquals(inputAccount.getEmail(), foundAccount.getEmail());
        });
    }

    @Test
    public void testFindWithNullPattern() {
        assertEquals(storage.find(null).size(), 0);
    }

    @Test
    public void testSaveAndDelete() {
        Account savedAccount = storage.save(new Account(TEST_NAME, TEST_SURNAME, TEST_EMAIL));
        assertNotNull(savedAccount);
        long accountId = savedAccount.getId().getAsLong();

        storage.delete(accountId);
        assertNull(storage.load(accountId));
    }

    @Test
    public void testSaveUpdateAndLoad() {
        Account inputAccount = new Account(TEST_NAME, TEST_SURNAME, TEST_EMAIL);

        Account savedAccount = storage.save(inputAccount);

        assertNotNull(savedAccount);
        assertTrue(savedAccount.getId().isPresent());

        inputAccount.setId(savedAccount.getId());
        inputAccount.setName(UPDATE_PREFIX + TEST_NAME);
        inputAccount.setSurname(UPDATE_PREFIX + TEST_SURNAME);
        inputAccount.setEmail(UPDATE_PREFIX + TEST_EMAIL);

        Account updatedAccount = storage.save(inputAccount);

        assertNotNull(updatedAccount);
        assertTrue(updatedAccount.getId().isPresent());
        assertEquals(savedAccount.getId(), updatedAccount.getId());
        assertEquals(inputAccount.getName(), updatedAccount.getName());
        assertEquals(inputAccount.getSurname(), updatedAccount.getSurname());
        assertEquals(inputAccount.getEmail(), updatedAccount.getEmail());

        Account loadAccount = storage.load(savedAccount.getId().getAsLong());
        assertNotNull(loadAccount);
        assertTrue(loadAccount.getId().isPresent());
        assertEquals(inputAccount.getName(), loadAccount.getName());
        assertEquals(inputAccount.getSurname(), loadAccount.getSurname());
        assertEquals(inputAccount.getEmail(), loadAccount.getEmail());
    }
}
