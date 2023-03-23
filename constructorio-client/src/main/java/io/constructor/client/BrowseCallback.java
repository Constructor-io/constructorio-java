package io.constructor.client;

import io.constructor.client.models.BrowseResponse;

public interface BrowseCallback {
  public void onFailure(ConstructorException exception);

  public void onResponse(BrowseResponse response) throws ConstructorException;
}
