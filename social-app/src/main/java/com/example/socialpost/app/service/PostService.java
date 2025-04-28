package com.example.socialpost.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.socialpost.app.model.Post;
import com.example.socialpost.app.repo.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Value("${upload.path}")
	private String uploadPath;

	public Post createPost(String content, MultipartFile image) {
		Post post = new Post();
		post.setContent(content);

		if (image != null && !image.isEmpty()) {
			try {
				// Create directory if it doesn't exist
				Path uploadDir = Paths.get(uploadPath);
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}

				// Generate unique filename
				String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
				String fileName = System.currentTimeMillis() + "_" + originalFilename;

				// Save file
				Path targetLocation = uploadDir.resolve(fileName);
				Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

				// Set the relative URL (not full path)
				post.setImageUrl("/uploads/" + fileName);

			} catch (IOException e) {
				throw new RuntimeException("Failed to store image: " + e.getMessage(), e);
			}
		}

		return postRepository.save(post);
	}

	public List<Post> getAllPosts() {
		return postRepository.findAllByOrderByCreatedAtDesc();
	}
}