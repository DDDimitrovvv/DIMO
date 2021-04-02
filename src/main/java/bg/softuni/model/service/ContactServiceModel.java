package bg.softuni.model.service;

import bg.softuni.model.entities.enums.ContactCategoryTypeEnum;

public class ContactServiceModel {

    private ContactCategoryTypeEnum categoryTypeEnum;
    private String messageText;

    public ContactServiceModel() {
    }

    public ContactCategoryTypeEnum getCategoryTypeEnum() {
        return categoryTypeEnum;
    }

    public ContactServiceModel setCategoryTypeEnum(ContactCategoryTypeEnum categoryTypeEnum) {
        this.categoryTypeEnum = categoryTypeEnum;
        return this;
    }

    public String getMessageText() {
        return messageText;
    }

    public ContactServiceModel setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }
}
