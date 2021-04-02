package bg.softuni.model.binding;

import bg.softuni.model.entities.enums.ContactCategoryTypeEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContactBindingModel {

    @NotNull(message = "Please select the type of your message!")
    private ContactCategoryTypeEnum categoryTypeEnum;

    @NotBlank(message = "The message cannot be empty!")
    @Size(min = 20, message = "The description must be at least twenty characters.")
    private String messageText;

    public ContactBindingModel() {
    }

    public ContactCategoryTypeEnum getCategoryTypeEnum() {
        return categoryTypeEnum;
    }

    public ContactBindingModel setCategoryTypeEnum(ContactCategoryTypeEnum categoryTypeEnum) {
        this.categoryTypeEnum = categoryTypeEnum;
        return this;
    }

    public String getMessageText() {
        return messageText;
    }

    public ContactBindingModel setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }
}
