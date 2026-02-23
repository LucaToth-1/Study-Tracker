package com.luca.repository;

import com.luca.model.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudySessionRepo extends JpaRepository<StudySession, Long> {
    List<StudySession> findBySubject_Id(Long subjectId);
}