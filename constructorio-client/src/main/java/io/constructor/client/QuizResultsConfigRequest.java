package io.constructor.client;

/** Constructor.io Quiz results configuration request */
public class QuizResultsConfigRequest extends QuizRequestBase {

    /**
     * Creates a Quiz Results Config request
     *
     * @param quizId the id of the quiz to request
     */
    public QuizResultsConfigRequest(String quizId) {
        super(quizId);
    }
}
