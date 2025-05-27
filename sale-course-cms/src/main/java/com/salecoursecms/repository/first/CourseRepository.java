package com.salecoursecms.repository.first;

import com.salecoursecms.entity.first.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,Long> {
    @Query("select u from CourseEntity u " +
            "where (:search is null or :search = '' or" +
            " LOWER(u.nameCourse) like concat('%', LOWER(:search), '%') or" +
            " LOWER(u.description) like concat('%', LOWER(:search), '%') or" +
            " LOWER(u.image) like concat('%', LOWER(:search), '%'))" +
            "and u.status <> 2 ")
    Page<CourseEntity> findAllWithPagin(String search , Pageable pageable);
}
