package s2017s40.kr.hs.mirim.remember_hi.DTO;

public class UserDTO {
    private String name;
    private String birth;
    private int age;
    private  String gender;
    private String phoneNum;

    public UserDTO(){ }
    public UserDTO(String name, String birth, int age, String gender, String phoneNum){
        this.age = age;
        this.birth = birth;
        this.gender = gender;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public String getBirth() {
        return birth;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
