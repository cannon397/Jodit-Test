package com.cannon.jodittest.controller;

import com.cannon.jodittest.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class JoditController {
    private final String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/uploads"; // 업로드할 디렉토리 경로
    @Autowired
    private PostRepository postRepository;
    @GetMapping(value = "/")
    public ModelAndView index() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("main"); //jsp(html)로 갈때는 setViewName // class로 갈때는 setView
        return mav;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(MultipartHttpServletRequest request) {
        Map<String, MultipartFile> fileMap = request.getFileMap();

        if (fileMap.isEmpty()) {
            return ResponseEntity.ok(createErrorResponse("No file uploaded"));
        }

        List<String> fileNames = new ArrayList<>();
        String baseUrl = "http://localhost:8080/uploads/";

        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            for (MultipartFile file : fileMap.values()) {
                if (file.isEmpty()) {
                    continue;
                }

                String fileName = file.getOriginalFilename();
                String filePath = uploadDir + File.separator + fileName;

                file.transferTo(new File(filePath));

                fileNames.add(fileName);
            }

            ObjectNode response = objectMapper.createObjectNode();
            response.put("error", 0);
            response.put("msg", "Files uploaded successfully");
            response.set("files", objectMapper.valueToTree(fileNames));
            response.put("path", uploadDir);
            response.put("baseurl", baseUrl);

            return ResponseEntity.ok(objectMapper.writeValueAsString(response));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(createErrorResponse("File upload failed: " + e.getMessage()));
        }
    }

    private String createErrorResponse(String message) {
        ObjectNode errorResponse = objectMapper.createObjectNode();
        errorResponse.put("error", 1);
        errorResponse.put("message", message);
        return errorResponse.toString();
    }

    // URL 인코딩을 위한 유틸리티 메서드
    private String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.toString()).replace("+", "%20");
        } catch (IOException e) {
            return url; // 인코딩에 실패하면 원래 문자열 반환
        }
    }
}
