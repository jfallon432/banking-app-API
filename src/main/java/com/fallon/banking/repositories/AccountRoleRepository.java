package com.fallon.banking.repositories;

import com.fallon.banking.models.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {

    @Query(nativeQuery = true, value = "select * from account_roles ar where ar.user_id = ?1")
    List<AccountRole> getAccountRoleByUserId(int id);
}
