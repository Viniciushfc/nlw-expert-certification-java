package com.viniciushfc.nlwexpert.certification_nlw.modules.students.repositories;

import com.viniciushfc.nlwexpert.certification_nlw.modules.students.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    Optional<StudentEntity> findByEmail(String email);
}
