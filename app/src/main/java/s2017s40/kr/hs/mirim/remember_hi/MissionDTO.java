package s2017s40.kr.hs.mirim.remember_hi;

public class MissionDTO {
    private String missionAlarm_H;          // 미션 알람 시간
    private String missionAlarm_M;          // 미션 알람 분
    private int missionComple;              // 미션 완료 확인
    private String missionTitle;            // 미션 제목

    public MissionDTO() {}

    public MissionDTO(String StringTitle, String missionAlarm_H , String missionAlarm_M, int missionComple) {
        super();
        this.missionTitle = StringTitle;
        this.missionAlarm_H = missionAlarm_H;
        this.missionAlarm_M = missionAlarm_M;
        this.missionComple = missionComple;
    }

    public void setMissionAlarm_M(String missionAlarm_M) {
        this.missionAlarm_M = missionAlarm_M;
    }

    public void setMissionAlarm_H(String missionAlarm_H) {
        this.missionAlarm_H = missionAlarm_H;
    }

    public String getMissionAlarm_M() {
        return missionAlarm_M;
    }

    public String getMissionAlarm_H() {
        return missionAlarm_H;
    }

    public String getMissionTitle() {
        return missionTitle;
    }
    public void setMissionTitle(String StringTitle) {
        this.missionTitle = StringTitle;
    }
    public int getMissionComple() {
        return missionComple;
    }
    public void setMissionComple(int missionComple) {
        this.missionComple = missionComple;
    }
}