package com.aelbardai.files.repository;

import com.aelbardai.files.domain.FileElement;
import com.aelbardai.user.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;


@Repository
public interface FileElementRepository extends CrudRepository<FileElement ,Long>{
    List<FileElement> findByUserOrderByIdDesc(User user);
    List<FileElement> findByUserAndParentIdOrderByIdDesc(User user, Long parentId);
    List<FileElement> findByUserAndParentIdAndAndContentType(User user, Long parentId , String contentType);
    @Query("SELECT f FROM FileElement f WHERE f.contentType LIKE CONCAT('%',:contentType,'%')")
    List<FileElement> findFileElementsByContentType(@Param("contentType") String contentType);
    FileElement findFirstByUserAndParentIdAndName(User user , Long parentId, String name);
}
