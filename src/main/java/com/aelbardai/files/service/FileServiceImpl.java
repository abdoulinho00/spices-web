package com.aelbardai.files.service;

import com.aelbardai.files.domain.FileElement;
import com.aelbardai.files.repository.FileElementRepository;
import com.aelbardai.user.domain.User;
import com.aelbardai.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileServiceImpl implements FileService {

    @Value("${file-manager.base-folder}")
    private String baseFolder;
    private final FileElementRepository fileElementRepository;
    private final UserService userService;

    @Override
    public List<FileElement> getAllFiles() {
        return (List<FileElement>) fileElementRepository.findAll();
    }

    @Override
    public List<FileElement> getFilesByUser(Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return fileElementRepository.findByUserOrderByIdDesc(user.get());
        } else {
            return Collections.emptyList();
        }

    }

    @Override
    public List<FileElement> getFilesByUser(Long userId, Long parentId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return fileElementRepository.findByUserAndParentIdOrderByIdDesc(user.get(), parentId);
        } else {
            return Collections.emptyList();
        }

    }

    @Override
    public List<FileElement> getFilesByExtension(String extension) {
        return null;
    }

    @Override
    public List<FileElement> getFilesByUserAndExtension(Long userId, String extension) {
        return null;
    }

    @Override
    public FileElement getFile(Long fileId) {
        return fileElementRepository.findOne(fileId);
    }

    @Override
    public FileElement saveFile(FileElement fileElement, MultipartFile file) {
        log.info("trying to save file");
        fileElement.setSize(file.getSize());
        fileElement.setContentType(file.getContentType());
        fileElement.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));

        //code to get the extesion
        log.info("file category : {} , original name : {}", file.getContentType(), file.getOriginalFilename());
        if (!file.isEmpty() && checkIfParentExists(fileElement.getParentId())) {
            File savedfile = new File(String.format("%s/%d/%s", baseFolder, fileElement.getUser().getId(), file.getOriginalFilename()));
            log.info("savedfile : {}", savedfile.getAbsolutePath());
            try {
                savedfile = findFileName(savedfile);
                log.info("savedfile : {}", savedfile.getAbsolutePath());
                File parent = savedfile.getParentFile();
                if (!parent.exists() && !parent.mkdirs()) {
                    throw new IllegalStateException("Couldn't create dir: " + parent);
                }
                file.transferTo(savedfile);
                //save file elemnt in the db
                fileElement.setPath(savedfile.getAbsolutePath());
                fileElementRepository.save(fileElement);
            } catch (IOException e) {
                log.error("Couldn't write uploaded file ", e);
            }

        }
        return fileElement;
    }

    @Override
    public FileElement saveFile(FileElement fileElement) {
        if (fileElement != null && checkIfParentExists(fileElement.getParentId())) {
            FileElement existant = fileElementRepository.findFirstByUserAndParentIdAndName(fileElement.getUser(), fileElement.getParentId(), fileElement.getName());
            log.info("fileelemnt : {}" , existant);
            if (existant == null) {
                return fileElementRepository.save(fileElement);
            }
        }
        return null;
    }

    @Override
    public FileElement updateFile(FileElement fileElement) {
        return null;
    }

    @Override
    public Resource loadAsResource(Long fileId) {
        FileElement fileElement =fileElementRepository.findOne(fileId);
        if(fileElement != null && fileElement.getPath() !=null) {
            try {
                Resource resource = new UrlResource(fileElement.getPath());
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    log.error("Could not read file: {}" + fileElement.getPath());
                }
            } catch (MalformedURLException e) {
                log.error("Could not read file: {}" + fileElement.getPath());
            }
        }
        return null;
    }

    @Override
    public boolean deleteFile(FileElement fileElement) {
        boolean isDeleted = true;
        if (fileElement.getContentType().equals("dir")) {
            for (FileElement element : fileElementRepository.findByUserAndParentIdOrderByIdDesc(fileElement.getUser(), fileElement.getParentId())) {
                deleteFile(element);
            }
            fileElementRepository.delete(fileElement);
        } else {
            isDeleted &= new File(fileElement.getPath()).delete();
            fileElementRepository.delete(fileElement);
        }

        return isDeleted;
    }

    @Override
    public List<FileElement> getImages(){
        return fileElementRepository.findFileElementsByContentType("image");
    }

    private File findFileName(File file) {
        if (!file.exists()) {
            return file;
        } else {
            String absoluthPath = file.getAbsolutePath();
            String filename = String.format("%s%s", FilenameUtils.getFullPath(absoluthPath), FilenameUtils.getBaseName(absoluthPath));
            String extension = FilenameUtils.getExtension(absoluthPath);
            log.info("filename : {} , extension : {}", filename, extension);
            int i = 1;
            File fileTosave = new File(String.format("%s(%d).%s", filename, i, extension));
            while (fileTosave.exists()) {
                i++;
                fileTosave = new File(String.format("%s(%d).%s", filename, i, extension));
            }
            return fileTosave;
        }
    }

    private boolean checkIfParentExists(Long parentId){
        return parentId==0 || fileElementRepository.findOne(parentId) != null;
    }
}
