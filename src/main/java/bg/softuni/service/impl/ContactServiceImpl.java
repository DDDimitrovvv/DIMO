package bg.softuni.service.impl;

import bg.softuni.model.entities.ContactEntity;
import bg.softuni.model.service.ContactServiceModel;
import bg.softuni.model.view.ContactViewModel;
import bg.softuni.repository.ContactRepository;
import bg.softuni.service.ContactService;
import bg.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private final UserService userService;
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public ContactServiceImpl(UserService userService, ContactRepository contactRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addContactMessage(ContactServiceModel contactServiceModel) throws Exception {
        ContactEntity contactEntity = modelMapper.map(contactServiceModel, ContactEntity.class);
        contactEntity.
                setUserEntity(userService.getCurrentUser()).
                setSubmittedDateTime(LocalDateTime.now());
        contactRepository.save(contactEntity);
    }

    @Override
    public List<ContactViewModel> getAllMessages() {
        List<ContactViewModel> contactViewModelList = new ArrayList<>();

        if (contactRepository.count() > 0) {
            List<ContactEntity> contactEntityList = contactRepository.listAllContactsMessagesSortedByDate();
            for ( ContactEntity contactEntity : contactEntityList ){
                ContactViewModel contactViewModel = modelMapper.map(contactEntity, ContactViewModel.class);
                contactViewModel.
                        setUsername(contactEntity.getUserEntity().getUsername()).
                        setCategoryTypeEnum(contactEntity.getCategoryTypeEnum());
                contactViewModelList.add(contactViewModel);
            }
        }
        return contactViewModelList;
    }

    @Override
    public ContactViewModel getMessageById(Long id) {
        ContactViewModel contactViewModel = new ContactViewModel();

        if (contactRepository.count() > 0) {
            ContactEntity contactEntity = contactRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found message with " + id + "!"));
            contactViewModel = modelMapper.map(contactEntity, ContactViewModel.class);
            contactViewModel.
                    setUsername(contactEntity.getUserEntity().getUsername()).
                    setCategoryTypeEnum(contactEntity.getCategoryTypeEnum());
        }
        return contactViewModel;
    }
}
