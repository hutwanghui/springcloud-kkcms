package com.kk.api.task;

import com.kk.api.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Created by msi- on 2018/2/19.
 */
@Component
public class TaksDemo3 {
    @Autowired
    private ISongService songService;

}
