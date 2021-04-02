package bg.softuni.model.view;

import bg.softuni.model.entities.enums.ContactCategoryTypeEnum;

import java.time.LocalDateTime;

public class ContactViewModel {

    private long id;
    private String username;
    private String messageText;
    private LocalDateTime submittedDateTime;
    private ContactCategoryTypeEnum categoryTypeEnum;

    public ContactViewModel() {
    }

    public long getId() {
        return id;
    }

    public ContactViewModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ContactViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getMessageText() {
        return messageText;
    }

    public ContactViewModel setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public LocalDateTime getSubmittedDateTime() {
        return submittedDateTime;
    }

    public ContactViewModel setSubmittedDateTime(LocalDateTime submittedDateTime) {
        this.submittedDateTime = submittedDateTime;
        return this;
    }

    public ContactCategoryTypeEnum getCategoryTypeEnum() {
        return categoryTypeEnum;
    }

    public ContactViewModel setCategoryTypeEnum(ContactCategoryTypeEnum categoryTypeEnum) {
        this.categoryTypeEnum = categoryTypeEnum;
        return this;
    }
}
