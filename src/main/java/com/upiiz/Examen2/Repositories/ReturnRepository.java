package com.upiiz.Examen2.Repositories;

import com.upiiz.Examen2.Entities.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Long> {
}
