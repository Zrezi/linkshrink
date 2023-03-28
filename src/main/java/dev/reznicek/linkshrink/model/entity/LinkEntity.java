package dev.reznicek.linkshrink.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "link")
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 2048)
    private String decoded;

    @Column(length = 255)
    private String encoded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDecoded() {
        return decoded;
    }

    public void setDecoded(String decoded) {
        this.decoded = decoded;
    }

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkEntity linkEntity = (LinkEntity) o;
        return Objects.equals(id, linkEntity.id) && Objects.equals(decoded, linkEntity.decoded) && Objects.equals(encoded, linkEntity.encoded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, decoded, encoded);
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", decoded='" + decoded + '\'' +
                ", encoded='" + encoded + '\'' +
                '}';
    }

}
