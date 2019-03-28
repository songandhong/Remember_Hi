package s2017s40.kr.hs.mirim.remember_hi;

public class DiaryDTO {
    private String diaryContent;
    private String diaryDate;
    private String diaryFeel;
    private String diaryKey1;
    private String diaryKey2;
    private String diaryKey3;
    private String diaryWeather;
    public DiaryDTO() { }
    public DiaryDTO(String diaryContent, String diaryDate, String diaryFeel, String diaryKey1, String diaryKey2, String diaryKey3, String diaryWeather) {
        this.diaryContent = diaryContent;
        this.diaryDate = diaryDate;
        this.diaryFeel = diaryFeel;
        this.diaryKey1 = diaryKey1;
        this.diaryKey2 = diaryKey2;
        this.diaryKey3 = diaryKey3;
        this.diaryWeather = diaryWeather;
    }

    public String getDiaryContent() {
        return diaryContent;
    }
    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }
    public String getDiaryDate() {
        return diaryDate;
    }
    public void setDiaryDate(String diaryDate) {
        this.diaryDate = diaryDate;
    }
    public String getDiaryFeel() {
        return diaryFeel;
    }
    public void setDiaryFeel(String diaryFeel) {
        this.diaryFeel = diaryFeel;
    }
    public String getDiaryKey1() {
        return diaryKey1;
    }
    public void setDiaryKey1(String diaryKey1) {
        this.diaryKey1 = diaryKey1;
    }
    public String getDiaryKey2() {
        return diaryKey2;
    }
    public void setDiaryKey2(String diaryKey2) {
        this.diaryKey2 = diaryKey2;
    }
    public String getDiaryKey3() {
        return diaryKey3;
    }
    public void setDiaryKey3(String diaryKey3) {
        this.diaryKey3 = diaryKey3;
    }
    public String getDiaryWeather() {
        return diaryWeather;
    }
    public void setDiaryWeather(String diaryWeather) {
        this.diaryWeather = diaryWeather;
    }
}
