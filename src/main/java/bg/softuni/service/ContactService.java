package bg.softuni.service;

import bg.softuni.model.service.ContactServiceModel;
import bg.softuni.model.view.ContactViewModel;

import java.util.List;

public interface ContactService {
    void addContactMessage(ContactServiceModel contactServiceModel) throws Exception;

    List<ContactViewModel> getAllMessages();

    ContactViewModel getMessageById(Long id);
}
