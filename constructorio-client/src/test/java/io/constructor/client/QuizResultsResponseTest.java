package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.constructor.client.models.QuizResultsResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class QuizResultsResponseTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void createQuizResultsResponseShouldReturnAResult() throws Exception {
    String string = Utils.getTestResource("response.finalizequiz.json");
    QuizResultsResponse response = ConstructorIO.createQuizResultsResponse(string);

    assertNotNull("result exists", response.getResult());
    assertNotNull("filter_expression exists", response.getResult().getFilterExpression());
    assertNotNull(
        "filter_expression and exists", response.getResult().getFilterExpression().get("and"));
    assertEquals("results_url exists", "BROWSE_URL", response.getResult().getResultsUrl());
    assertEquals("version_id exists", "string", response.getVersionId());
  }
}
