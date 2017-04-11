package vlad.fp.services.asynchronous.server;

import vlad.fp.lib.Task;
import vlad.fp.services.asynchronous.api.AccountServiceF;
import vlad.fp.services.model.Account;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.BrandID;
import vlad.fp.services.model.EmailAddress;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AccountServerF implements AccountServiceF {
  private final ConcurrentMap<AccountID, Account> accounts = new ConcurrentHashMap<>();

  @Override
  public Task<Account> getAccount(AccountID id) {
    return Task.now(accounts.computeIfAbsent(id, key ->
        new Account(id, new BrandID(UUID.randomUUID().toString()), new EmailAddress("fake", "mail.com"))));
  }
}
