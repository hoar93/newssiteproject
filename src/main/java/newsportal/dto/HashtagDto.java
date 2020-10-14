package newsportal.dto;

public class HashtagDto {
    private Long id;
    private String name;

    public HashtagDto(String name) {
        this.name = name;
    }

    public HashtagDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
