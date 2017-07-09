package com.aelbardai.web;


import com.aelbardai.files.domain.FileElement;
import com.aelbardai.files.service.FileService;
import com.aelbardai.files.service.MultipartFileSender;
import com.aelbardai.user.domain.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springfox.documentation.spi.service.contexts.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@Slf4j
@RequestMapping("/profile")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfileController {

    private final FileService fileService;

    @GetMapping("/myfiles")
    public ModelAndView showProfileFiles(Authentication authentication, @RequestParam(value="parentId" , defaultValue = "0") Long parentId){

        log.info("trying to show content of folder");
        if(authentication.isAuthenticated()) {
            CurrentUser user = (CurrentUser) authentication.getPrincipal();
            log.info("user id : {}" , user.getId());
            return new ModelAndView("profile/myfiles" , "files" , fileService.getFilesByUser(user.getId(),parentId));
        }
        else{
            log.warn("Should never reach this code");
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping({"/",""})
    public ModelAndView viewProfile(){

        return new ModelAndView("profile/view" , "user" , SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @PostMapping("/upload")
    public ModelAndView uploadFile(Authentication authentication ,@RequestParam("file") MultipartFile file ,FileElement fileElement, RedirectAttributes redirectAttributes){
        fileElement.setUser(((CurrentUser)authentication.getPrincipal()).getUser());
        fileService.saveFile(fileElement ,file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return new ModelAndView("redirect:/profile/myfiles");
    }

    @GetMapping("/upload")
    public ModelAndView uploadFileForm(@RequestParam(value= "parentId" , required = false) String parentId){
        FileElement fileElement = new FileElement();
        if(parentId != null){
            try{
                fileElement.setParentId(Long.parseLong(parentId));
            }
            catch(NumberFormatException ex){
                log.error("cannot parse '{}' to Long ", parentId);
                log.debug("",ex);
                fileElement.setParentId(0L);
            }
        }
        else{
            log.info("hey null");
            fileElement.setParentId(0L);
        }
        return new ModelAndView("profile/uploadFile" , "fileElement" , fileElement);
    }

    @PostMapping("/myfiles/create")
    public ModelAndView createDirectory(Authentication authentication, @RequestParam("name") String name ,@RequestParam(value = "parentId", defaultValue = "0") Long parentId){
        log.info("trying to create new folder");
        FileElement fileElement = FileElement.builder()
                .contentType("dir").name(name)
                .parentId(parentId)
                .user(((CurrentUser)authentication.getPrincipal()).getUser())
                .build();
        fileService.saveFile(fileElement);
        return new ModelAndView("redirect:/profile/myfiles");
    }

    @GetMapping("/view/{id}")
    @ResponseBody
    public FileSystemResource serveFile(HttpServletRequest request, HttpServletResponse response , @PathVariable("id") Long fileId) {

        FileElement fileElement = fileService.getFile(fileId);
        if(fileElement != null && fileElement.getPath() != null) {
            File file = new File(fileElement.getPath());
            if(file.exists() && file.canRead()) {
                try {
                    MultipartFileSender.fromPath(Paths.get(fileElement.getPath()))
                            .with(request)
                            .with(response)
                            .withContentType(fileElement.getContentType())
                            .serveResource();

                    /*response.setContentType(fileElement.getContentType());
                    //response.setHeader("Content-Disposition", String.format("inline; filename=%s.%s",fileElement.getName(),fileElement.getExtension()));
                    //return new FileSystemResource(file);
                    response.setContentLengthLong(fileElement.getSize());
                    response.getOutputStream().write(Files.readAllBytes(Paths.get(fileElement.getPath())));*/
                }catch(IOException ex){
                    log.error("couldn't write in output stream");
                    return null;
                }
                catch(Exception ex){
                    log.error("something wrong with my file");
                }
            }
            else{
                return null;
            }
        }
        else{
            log.error("Resource returned is null ");
            return null;
        }
        return null;
    }

}
