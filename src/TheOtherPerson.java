import java.util.Objects;

/**
 * @author yinchao
 * @date 2020/3/11 16:59
 */
public class TheOtherPerson {
    private int id;
    private String name;
    private int age;

    public TheOtherPerson(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TheOtherPerson)) return false;
        TheOtherPerson that = (TheOtherPerson) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

