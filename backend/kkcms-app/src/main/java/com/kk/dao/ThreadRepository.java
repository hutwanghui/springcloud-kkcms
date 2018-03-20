package com.kk.dao;

import com.kk.entity.Thread;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by msi- on 2018/3/1.
 */
public interface ThreadRepository extends MongoRepository<Thread, String> {
}
