package com.berkhayta.controller;

import com.berkhayta.constant.EndPoints;
import com.berkhayta.dto.request.PostSaveRequestDto;
import com.berkhayta.dto.request.PostUpdateRequestDto;
import com.berkhayta.dto.response.PostFindAllResponseDto;
import com.berkhayta.entity.Post;
import com.berkhayta.exception.ErrorType;
import com.berkhayta.exception.PostServiceException;
import com.berkhayta.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoints.POST)
public class PostController {
	private final PostService postService;
	
	@PostMapping(EndPoints.SAVE)
	public ResponseEntity<String> save(@RequestBody PostSaveRequestDto dto){
		return ResponseEntity.ok(postService.save(dto));
	}
	
	@GetMapping(EndPoints.FINDALLUSERPOSTBYTOKEN)
	public ResponseEntity<List<PostFindAllResponseDto>> findAllUserPostByToken(String token){
		List<PostFindAllResponseDto> allPostByToken = postService.findAllUserPostByToken(token);
		
		return ResponseEntity.ok(allPostByToken);
	}
	
	@GetMapping(EndPoints.FINDALL)
	public ResponseEntity<List<PostFindAllResponseDto>> findAllByToken(String token){
		List<PostFindAllResponseDto> allPostByToken = postService.findAllByToken(token);
		return ResponseEntity.ok(allPostByToken);
	}
	@DeleteMapping(EndPoints.DELETE)
	public ResponseEntity<String> deletePost(@RequestParam String token,String postId){
		 postService.deletePost(token,postId);
		 return ResponseEntity.ok("Post deleted successfully");
	}
	@PutMapping(EndPoints.UPDATE)
	public ResponseEntity<String> updatePost(@RequestBody PostUpdateRequestDto dto){
		postService.updatePost(dto);
		return ResponseEntity.ok("Post updated successfully");
	}
}