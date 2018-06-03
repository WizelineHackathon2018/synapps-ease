package mx.wizelinehack.contextenginedemo.domain.document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Document.
 *
 * @author Armando Montoya, created on: 6/2/18
 */
@Entity
@Table(name = "document")
public class Document {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The Subject.
     */
    @Column(name = "subject")
    private String subject;

    /**
     * The Url.
     */
    @Column(name = "url")
    private String url;

    /**
     * Instantiates a new Document.
     */
    public Document() {
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
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
