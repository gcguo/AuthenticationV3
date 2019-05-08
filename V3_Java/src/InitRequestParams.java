public class InitRequestParams {

    private String SessionId;
    private String RefText;
    private int WorkMode;
    private int EvalMode;
    private float ScoreCoeff;

    public InitRequestParams(String sessionId, String refText, int workMode, int evalMode, float scoreCoeff) {
        SessionId = sessionId;
        RefText = refText;
        WorkMode = workMode;
        EvalMode = evalMode;
        ScoreCoeff = scoreCoeff;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getRefText() {
        return RefText;
    }

    public void setRefText(String refText) {
        RefText = refText;
    }

    public int getWorkMode() {
        return WorkMode;
    }

    public void setWorkMode(int workMode) {
        WorkMode = workMode;
    }

    public int getEvalMode() {
        return EvalMode;
    }

    public void setEvalMode(int evalMode) {
        EvalMode = evalMode;
    }

    public float getScoreCoeff() {
        return ScoreCoeff;
    }

    public void setScoreCoeff(float scoreCoeff) {
        ScoreCoeff = scoreCoeff;
    }
}
