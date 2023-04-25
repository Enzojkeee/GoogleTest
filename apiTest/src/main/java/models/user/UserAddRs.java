package models.user;

/**
 * Модель для хранения данных пользователя
 */
public class UserAddRs {
    private String firstName;
    private Double money;
    private String sex;
    private Integer id;
    private Integer age;
    private String secondName;

    public UserAddRs setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserAddRs setMoney(Double money) {
        this.money = money;
        return this;
    }

    public UserAddRs setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public UserAddRs setId(Integer id) {
        this.id = id;
        return this;
    }

    public UserAddRs setAge(Integer age) {
        this.age = age;
        return this;
    }

    public UserAddRs setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Double getMoney() {
        return money;
    }

    public String getSex() {
        return sex;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }

    public String getSecondName() {
        return secondName;
    }

    @Override
    public String toString() {
        return
                "UserAddRs{" +
                        "firstName = '" + firstName + '\'' +
                        ",money = '" + money + '\'' +
                        ",sex = '" + sex + '\'' +
                        ",id = '" + id + '\'' +
                        ",age = '" + age + '\'' +
                        ",secondName = '" + secondName + '\'' +
                        "}";
    }
}
