package com.kk.api.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by msi- on 2018/2/8.
 */
public class SongException extends RuntimeException implements GraphQLError {

    private Map<String, Object> extensions = new HashMap<>();

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public SongException(String message, Integer invalidSongId) {
        super(message);
        extensions.put("invalidSongId", invalidSongId);
    }
}
