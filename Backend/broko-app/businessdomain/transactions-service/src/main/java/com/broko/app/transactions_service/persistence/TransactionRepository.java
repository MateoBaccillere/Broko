package com.broko.app.transactions_service.persistence;

import com.broko.app.transactions_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
