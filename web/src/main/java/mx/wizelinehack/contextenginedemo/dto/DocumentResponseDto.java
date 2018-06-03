package mx.wizelinehack.contextenginedemo.dto;

import java.io.Serializable;

/**
 * The type Document response dto.
 *
 * @author Armando Montoya, created on: 6/2/18
 */
public class DocumentResponseDto implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 6673882722946859111L;

    /**
     * The Id.
     */
    private long id;

    /**
     * The Subject.
     */
    private String subject;

    /**
     * The Link.
     */
    private String link;

    /**
     * Instantiates a new Document response dto.
     */
    public DocumentResponseDto() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * Gets link.
     *
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets link.
     *
     * @param link the link
     */
    public void setLink(final String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "DocumentResponseDto{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
