package com.ndptest.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface ResultRepository extends JpaRepository<Result, Integer> {
    @Modifying
    @Transactional
    @Query(value="truncate result", nativeQuery = true)
    void truncateResultTable();
    Result findByNumber(int number);
    Result findByUsernameAndResponseTime(String username, String responseTime);
}
