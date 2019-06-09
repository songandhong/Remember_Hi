package s2017s40.kr.hs.mirim.remember_hi.DTO;

public class UserDTO {
    private String name;
    private String birth;
    private int age;
    private String phoneNum;
    private String pname;
    private String pphoneNum;

    public UserDTO(){ }
    public UserDTO(String name, String birth, int age, String phoneNum, String pname, String pphoneNum){
        this.age = age;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.name = name;
        this.pname = pname;
        this.pphoneNum = pphoneNum;
    }

    public void setPphoneNum(String pphoneNum) {
        this.pphoneNum = pphoneNum;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPphoneNum() {
        return pphoneNum;
    }

    public String getPname() {
        return pname;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
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
