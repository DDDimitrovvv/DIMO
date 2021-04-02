package bg.softuni.model.entities;

import bg.softuni.model.entities.enums.ContactCategoryTypeEnum;
import bg.softuni.model.entities.enums.StoryTypeEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contacts")
public class ContactEntity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private ContactCategoryTypeEnum categoryTypeEnum;

    @Column(name = "message_text", nullable = false, unique = true, columnDefinition = "TEXT")
    private String messageText;

    @Column(name = "submitted_date_time")
    private LocalDateTime submittedDateTime;

    @ManyToOne
    private UserEntity userEntity;

    public ContactEntity() {
    }

    public ContactCategoryTypeEnum getCategoryTypeEnum() {
        return categoryTypeEnum;
    }

    public ContactEntity setCategoryTypeEnum(ContactCategoryTypeEnum categoryTypeEnum) {
        this.categoryTypeEnum = categoryTypeEnum;
        return this;
    }

    public String getMessageText() {
        return messageText;
    }

    public ContactEntity setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public LocalDateTime getSubmittedDateTime() {
        return submittedDateTime;
    }

    public ContactEntity setSubmittedDateTime(LocalDateTime submittedDateTime) {
        this.submittedDateTime = submittedDateTime;
        return this;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public ContactEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }
}
