package vlad.fp.services.synchronous.server;

import vlad.fp.services.model.Account;
import vlad.fp.services.model.AccountID;
import vlad.fp.services.model.BrandID;
import vlad.fp.services.model.EmailAddress;
import vlad.fp.services.synchronous.api.AccountService;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AccountServer implements AccountService {
  private final ConcurrentMap<AccountID, Account> accounts = new ConcurrentHashMap<>();
  @Override
  public Account getAccount(AccountID id) {
    return accounts.computeIfAbsent(id, key ->
        new Account(id, new BrandID(UUID.randomUUID().toString()), new EmailAddress("fake", "mail.com")));
  }
}
