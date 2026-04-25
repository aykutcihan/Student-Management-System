package com.project.schoolmanagment.repository;

import com.project.schoolmanagment.entity.concretes.EducationTerm;
import com.project.schoolmanagment.entity.enums.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface EducationTermRepository extends JpaRepository<EducationTerm,Long> {

	@Query("SELECT (count (e) > 0) FROM EducationTerm e WHERE e.term=?1 AND EXTRACT(YEAR FROM e.startDate) = ?2 ")
	boolean existsByTermAndYear(Term term, int year);


	@Query("SELECT educationTerm from EducationTerm educationTerm WHERE educationTerm.id=?1")
	EducationTerm findByIdEquals(Long id);




}
