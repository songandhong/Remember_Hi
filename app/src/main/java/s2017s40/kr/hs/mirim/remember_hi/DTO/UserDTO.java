package s2017s40.kr.hs.mirim.remember_hi.DTO;

public class UserDTO {
    private String name;
    private String email;
    private int age;
    private  String gender;
    String phoneNum;
    UserDTO(){ }
    UserDTO(String name, String email, int age, String gender, String phoneNum){
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
