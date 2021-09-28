
package beans;

import lombok.Data;

@Data
public class BoardType {

    private String id;
    private String name;
    private String desc;
    private String descData;
    private Boolean closed;
    private String idMemberCreator;
    private String idOrganization;
    private Boolean pinned;
    private String url;
    private String shortUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
