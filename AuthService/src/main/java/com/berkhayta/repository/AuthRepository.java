package com.berkhayta.repository;

import com.berkhayta.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AuthRepository extends JpaRepository<Auth,Long> {
	/**
	 * Username daha önce alınmış mı kontrol sağlamak için:
	 * @param username
	 * @return
	 */
	Boolean existsByUsername(String username);
	/**
	 * email ve password vt'da kayıtlı mı kontrolü yapar.
	 */
	Optional<Auth> findOptionalByEmailAndPassword(String email,String password);
}