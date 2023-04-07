package io.constructor.client;

import io.constructor.client.models.SearchResponse;

public interface SearchCallback {
    public void onFailure(ConstructorException exception);

    public void onResponse(SearchResponse response) throws ConstructorException;
}
