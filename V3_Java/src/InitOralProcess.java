public class InitOralProcess {

    private String SessionId;
    private String RefText;
    private int WorkMode;
    private int EvalMode;
    private float ScoreCoeff;

    public InitOralProcess(String sessionId, String refText, int workMode, int evalMode, float scoreCoeff) {
        this.SessionId = sessionId;
        this.RefText = refText;
        this.WorkMode = workMode;
        this.EvalMode = evalMode;
        this.ScoreCoeff = scoreCoeff;
    }

    private String SoeAppId;

    private int IsLongLifeSession;

    private int StorageMode;

    public static int STORAGE_FALSE = 0;
    public static int STORAGE_TRUE = 1;

    private int SentenceInfoEnabled;

    private int ServerType;

    public static int SERVER_TYPE_ENGLISH = 0;
    public static int SERVER_TYPE_CHINESE = 1;

    private int IsAsync;

    private int TextMode;

    public static int TEXT_MODE_TEXT = 0;
    public static int TEXT_MODE_SECOND = 1;

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

    public String getSoeAppId() {
        return SoeAppId;
    }

    public void setSoeAppId(String soeAppId) {
        SoeAppId = soeAppId;
    }

    public int getIsLongLifeSession() {
        return IsLongLifeSession;
    }

    public void setIsLongLifeSession(int isLongLifeSession) {
        IsLongLifeSession = isLongLifeSession;
    }

    public int getStorageMode() {
        return StorageMode;
    }

    public void setStorageMode(int storageMode) {
        StorageMode = storageMode;
    }

    public int getSentenceInfoEnabled() {
        return SentenceInfoEnabled;
    }

    public void setSentenceInfoEnabled(int sentenceInfoEnabled) {
        SentenceInfoEnabled = sentenceInfoEnabled;
    }

    public int getServerType() {
        return ServerType;
    }

    public void setServerType(int serverType) {
        ServerType = serverType;
    }

    public int getIsAsync() {
        return IsAsync;
    }

    public void setIsAsync(int isAsync) {
        IsAsync = isAsync;
    }

    public int getTextMode() {
        return TextMode;
    }

    public void setTextMode(int textMode) {
        TextMode = textMode;
    }
}
