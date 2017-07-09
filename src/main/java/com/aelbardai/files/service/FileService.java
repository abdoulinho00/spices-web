package com.aelbardai.files.service;


import com.aelbardai.files.domain.FileElement;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {
    List<FileElement> getAllFiles();
    List<FileElement> getFilesByUser(Long userId);
    List<FileElement> getFilesByUser(Long userId, Long parentId);
    List<FileElement> getFilesByExtension(String extension);
    List<FileElement> getFilesByUserAndExtension(Long userId, String extension );
    List<FileElement> getImages();
    FileElement getFile(Long fileId);
    FileElement saveFile(FileElement fileElement , MultipartFile file);
    FileElement saveFile(FileElement fileElement);
    FileElement updateFile(FileElement fileElement);
    Resource loadAsResource(Long fileId);
    boolean deleteFile(FileElement fileElement);
}
