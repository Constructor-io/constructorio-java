package io.constructor.client;

import java.util.ArrayList;
import java.util.List;

/** Constructor.io Next Quiz and Results Quiz request */
public class QuizRequest extends QuizRequestBase {
    private List<List<String>> answers;
    private String quizSessionId;

    /**
     * Creates a Next Quiz/Results Quiz request
     *
     * @param quizId the id of the quiz to request
     */
    public QuizRequest(String quizId) {
        super(quizId);
        this.answers = new ArrayList<>();
        this.quizSessionId = null;
    }

    /**
     * @param answers the list of answers in the format [ [1,2], [3,4] ] where [1,2] are the
     *     selected answers for the first question and [3,4] are the selected answers for the second
     *     question
     */
    public void setAnswers(List<List<String>> answers) {
        this.answers = answers;
    }

    /**
     * @return the list of answers for the quiz
     */
    public List<List<String>> getAnswers() {
        return answers;
    }

    /**
     * @param quizSessionId The quiz session id will be returned with the first request, and it
     *     should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-sessions
     */
    public void setQuizSessionId(String quizSessionId) {
        this.quizSessionId = quizSessionId;
    }

    /**
     * @return the quizSessionId. The quiz session id will be returned with the first request and it
     *     should be passed with subsequent requests. More information can be found:
     *     https://docs.constructor.io/rest_api/quiz/using_quizzes/#quiz-sessions
     */
    public String getQuizSessionId() {
        return quizSessionId;
    }
}
