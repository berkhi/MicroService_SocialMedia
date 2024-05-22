package com.berkhayta.repository;

import com.berkhayta.entity.Post;
import com.berkhayta.entity.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {
	//List<Post> findAllByUserId(String userId);
	
	List<Post> findAllByStatus(EStatus status);
	List<Post> findAllByStatusAndUserId(EStatus status,String userId);
}