package mx.wizelinehack.contextenginedemo.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The type New document request dto.
 *
 * @author Armando Montoya, created on: 6/2/18
 */
public class NewDocumentRequestDto implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 2207838341438909393L;

    /**
     * The Subject.
     */
    @NotNull
    private String subject;

    /**
     * The Components.
     */
    @NotNull
    private String components;

    /**
     * The Process.
     */
    @NotNull
    private String process;

    /**
     * The Stamp.
     */
    private String stamp;

    /**
     * Instantiates a new New document request dto.
     */
    public NewDocumentRequestDto() {
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
     * Gets components.
     *
     * @return the components
     */
    public String getComponents() {
        return components;
    }

    /**
     * Sets components.
     *
     * @param components the components
     */
    public void setComponents(final String components) {
        this.components = components;
    }

    /**
     * Gets process.
     *
     * @return the process
     */
    public String getProcess() {
        return process;
    }

    /**
     * Sets process.
     *
     * @param process the process
     */
    public void setProcess(final String process) {
        this.process = process;
    }

    /**
     * Gets stamp.
     *
     * @return the stamp
     */
    public String getStamp() {
        return stamp;
    }

    /**
     * Sets stamp.
     *
     * @param stamp the stamp
     */
    public void setStamp(final String stamp) {
        this.stamp = stamp;
    }

    @Override
    public String toString() {
        return "NewDocumentRequestDto{" +
                "subject='" + subject + '\'' +
                ", components='" + components + '\'' +
                ", process='" + process + '\'' +
                ", stamp='" + stamp + '\'' +
                '}';
    }
}
