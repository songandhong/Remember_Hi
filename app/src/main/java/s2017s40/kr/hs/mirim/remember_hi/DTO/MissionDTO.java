package s2017s40.kr.hs.mirim.remember_hi.DTO;

public class MissionDTO {
    private String missionAlarm;          // 미션 알람 시간
    private int missionComple;              // 미션 완료 확인
    private String missionTitle;            // 미션 제목

    public MissionDTO() {}

    public MissionDTO(String StringTitle, String missionAlarm_H , int missionComple) {
        super();
        this.missionTitle = StringTitle;
        this.missionAlarm = missionAlarm_H;
        this.missionComple = missionComple;
    }

    public void setmissionAlarm(String missionAlarm) {
        this.missionAlarm = missionAlarm;
    }

    public String getmissionAlarm() {
        return missionAlarm;
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