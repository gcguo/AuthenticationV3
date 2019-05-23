public class TransmitOralProcess {

    private int SeqId;

    private int IsEnd;

    private int VoiceFileType;

    public static int VOICE_FILE_RAW = 1;
    public static int VOICE_FILE_WAV = 2;
    public static int VOICE_FILE_MP3 = 3;

    private int VoiceEncodeType;

    public static int VOICE_ENCODE_PCM = 1;

    private String UserVoiceData;

    private String SessionId;

    public TransmitOralProcess(int seqId, int isEnd, int voiceFileType, int voiceEncodeType, String userVoiceData, String sessionId) {
        SeqId = seqId;
        IsEnd = isEnd;
        VoiceFileType = voiceFileType;
        VoiceEncodeType = voiceEncodeType;
        UserVoiceData = userVoiceData;
        SessionId = sessionId;
    }

    public int getSeqId() {
        return SeqId;
    }

    public void setSeqId(int seqId) {
        SeqId = seqId;
    }

    public int getIsEnd() {
        return IsEnd;
    }

    public void setIsEnd(int isEnd) {
        IsEnd = isEnd;
    }

    public int getVoiceFileType() {
        return VoiceFileType;
    }

    public void setVoiceFileType(int voiceFileType) {
        VoiceFileType = voiceFileType;
    }

    public int getVoiceEncodeType() {
        return VoiceEncodeType;
    }

    public void setVoiceEncodeType(int voiceEncodeType) {
        VoiceEncodeType = voiceEncodeType;
    }

    public String getUserVoiceData() {
        return UserVoiceData;
    }

    public void setUserVoiceData(String userVoiceData) {
        UserVoiceData = userVoiceData;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    private String SoeAppId;

    private int IsLongLifeSession;

    private int IsQuery;

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

    public int getIsQuery() {
        return IsQuery;
    }

    public void setIsQuery(int isQuery) {
        IsQuery = isQuery;
    }
}
