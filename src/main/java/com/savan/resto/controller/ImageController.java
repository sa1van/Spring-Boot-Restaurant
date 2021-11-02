package com.savan.resto.controller;

import com.savan.resto.entity.Image;
import com.savan.resto.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"},allowCredentials = "true",methods = {RequestMethod.GET,RequestMethod.POST})
@RequestMapping("image")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @PostMapping(value="/",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Long uploadImage(@RequestPart("file") MultipartFile multipartImage,
                     @RequestPart("imageDetail") String imageDetail,
                     HttpServletRequest request) throws Exception {
        Image dbImage = new Image();
        dbImage.setContent(multipartImage.getBytes());

        System.out.println(imageDetail);

        Cookie[] rawCookie = request.getCookies();
        System.out.println(rawCookie);

        if(rawCookie != null) {
            // key is always without spaces
            for(int i=0;i<rawCookie.length;i++)
            {
                System.out.println(rawCookie[i].getName());
                System.out.println(URLDecoder.decode(rawCookie[i].getValue(),"UTF-8"));
            }
        }

        return imageRepository.save(dbImage)
                .getId();
    }

    @GetMapping(value = "/getImage/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    ByteArrayResource downloadImage(@PathVariable Long imageId) {
        byte[] image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getContent();

        return new ByteArrayResource(image);
    }

    @GetMapping("/normalCookie")
    public String sayHi(HttpServletResponse response)
    {
        Cookie cookie = new Cookie("coo-kie", "cook-ie");
        cookie.setPath("/"); // "/" path is important else you may not be able to get cookie at all route levels.
        response.addCookie(cookie);
        // there is property for adding expiration time too
        return "Done";
    }

    @GetMapping("/httpOnly")
    public String settingOnly(HttpServletResponse response) throws UnsupportedEncodingException {

        // URL encoding must for spaces
        Cookie cookie = new Cookie("randomThing", URLEncoder.encode("something with spaces", "UTF-8"));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "Done adding httpOnly cookie";
    }

}
