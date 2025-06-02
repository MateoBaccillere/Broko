package com.broko.app.transactions_service.persistence;

import com.broko.app.transactions_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
