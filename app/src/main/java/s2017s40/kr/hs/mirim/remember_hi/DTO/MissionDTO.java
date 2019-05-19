package s2017s40.kr.hs.mirim.remember_hi.DTO;

public class MissionDTO {
    private String missionAlarm;          // 미션 알람 시간
    private Boolean missionComple;              // 미션 완료 확인
    private String missionTitle;            // 미션 제목

    public MissionDTO() {}

    public MissionDTO(String StringTitle, String missionAlarm_H , Boolean missionComple) {
        super();
        this.missionTitle = StringTitle;
        this.missionAlarm = missionAlarm_H;
        this.missionComple = missionComple;
    }

    public void setMissionTitle(String missionTitle) {
        this.missionTitle = missionTitle;
    }

    public void setMissionComple(Boolean missionComple) {
        this.missionComple = missionComple;
    }

    public void setMissionAlarm(String missionAlarm) {
        this.missionAlarm = missionAlarm;
    }

    public String getMissionTitle() {
        return missionTitle;
    }

    public String getMissionAlarm() {
        return missionAlarm;
    }

    public Boolean getMissionComple() {
        return missionComple;
    }
}