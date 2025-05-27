package com.salecoursecms.repository.first;

import com.salecoursecms.entity.first.CourseSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseSessionRepository extends JpaRepository<CourseSessionEntity, Long> {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Modifying
    @Query(value = "DELETE FROM SC_COURSE_SESSION WHERE COURSE_ID = :courseId", nativeQuery = true)
    void deleteCourseSessionByCourseId(@Param("courseId") Long courseId);

    @Query("select c from CourseSessionEntity c where c.courseId = :courseId and c.status = 1 ")
    List<CourseSessionEntity> findByCourseId(@Param("courseId") Long courseId);

}
