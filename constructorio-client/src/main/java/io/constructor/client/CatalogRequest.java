package io.constructor.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Constructor.io Catalog Request */
public class CatalogRequest {
    public enum OnMissing {
        FAIL,
        IGNORE,
        CREATE,
    }

    private Map<String, File> files;
    private String section;
    private List<String> notificationEmails;
    private OnMissing onMissing;
    private Boolean force;

    /**
     * Creates a catalog request
     *
     * @param files the files to be uploaded
     * @param section the autocomplete section to upload the files to
     */
    public CatalogRequest(Map<String, File> files, String section) {
        if (files == null) {
            throw new IllegalArgumentException("files is required");
        }
        if (section == null) {
            throw new IllegalArgumentException("section is required");
        }

        this.files = files;
        this.section = section;
        this.onMissing = OnMissing.FAIL;
        this.notificationEmails = new ArrayList<String>();
    }

    /**
     * @param onMissing Either "FAIL", "CREATE", "IGNORE", indicating what to do when a missing item
     *     is uploaded for patch requests. "FAIL" will fail the patch catalog request if an item
     *     does not exist. "CREATE" will create the item if it does not exist. "IGNORE" will ignore
     *     items that do not exist. Defaults to "FAIL". Only applicable to patchCatalog requests.
     */
    public void setOnMissing(OnMissing onMissing) {
        this.onMissing = onMissing;
    }

    /**
     * @return the onMissing strategy
     */
    public OnMissing getOnMissing() {
        return onMissing;
    }

    /**
     * @param files the files to be uploaded (items, variations, item_groups)
     */
    public void setFiles(Map<String, File> files) {
        this.files = files;
    }

    /**
     * @return the files to be uploaded
     */
    public Map<String, File> getFiles() {
        return files;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the notification email
     */
    public String getSection() {
        return section;
    }

    /**
     * @param email the email address to receive a notification in case the task fails
     * @deprecated Use {@link #setNotificationEmails(List)} instead to support multiple emails.
     */
    @Deprecated
    public void setNotificationEmail(String email) {
        this.notificationEmails.clear();
        if (email != null) {
            this.notificationEmails.add(email);
        }
    }

    /**
     * @param emails list of email addresses where you'd like to receive notifications in case the
     *     task fails
     */
    public void setNotificationEmails(List<String> emails) {
        this.notificationEmails.clear();
        if (emails != null) {
            this.notificationEmails.addAll(emails);
        }
    }

    /**
     * @return the first notification email, or null if none set
     * @deprecated Use {@link #getNotificationEmails()} instead to support multiple emails.
     */
    @Deprecated
    public String getNotificationEmail() {
        if (notificationEmails != null && !notificationEmails.isEmpty()) {
            return notificationEmails.get(0);
        }
        return null;
    }

    /**
     * @return the list of notification emails, or null if none set
     */
    public List<String> getNotificationEmails() {
        if (notificationEmails.isEmpty()) {
            return null;
        }
        return notificationEmails;
    }

    /**
     * @param force true if the catalog should be processed even if it will invalidate a large
     *     number of existing items
     */
    public void setForce(Boolean force) {
        this.force = force;
    }

    /**
     * @return the force flag
     */
    public Boolean getForce() {
        return force;
    }
}
