package newsportal.dto;

public class HashtagDto {
    private String name;

    public HashtagDto(String name) {
        this.name = name;
    }

    public HashtagDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
