package com.project.board.main.api.repository.admin;

import com.project.board.main.api.domain.admin.BoardMainAdmin;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface BoardMainAdminRepository extends JpaRepository<BoardMainAdmin, Long> {
    BoardMainAdmin findBoardMainAdminByAdminID(String adminID);

    @Query(value = "update BoardMainAdmin set lastDate = :lastDate where adminUUID = :adminUUID")
    @Modifying
    @Transactional
    void updateBoardMainAdminByAdminUUIDForLogin(UUID adminUUID, LocalDateTime lastDate);
}
