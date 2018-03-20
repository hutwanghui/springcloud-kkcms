package com.kk.dao;

import com.kk.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by msi- on 2018/3/1.
 */
public interface UserRepository extends MongoRepository<User, String> {
}
