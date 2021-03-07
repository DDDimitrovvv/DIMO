package bg.softuni.service.impl;

import bg.softuni.model.entities.CategoryEntity;
import bg.softuni.model.entities.enums.CategoryEnum;
import bg.softuni.repository.CategoryRepository;
import bg.softuni.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() {

        if (categoryRepository.count() == 0) {
            List<CategoryEntity> categoryEntities = new ArrayList<>();

            for ( CategoryEnum categoryEnum : CategoryEnum.values() ){
                CategoryEntity categoryEntity = new CategoryEntity();

                switch (categoryEnum) {
                    case SPEAKERS:
                        categoryEntity
                                .setCategoryEnum(CategoryEnum.SPEAKERS)
                                .setDescription("A loudspeaker is an electroacoustic transducer;a device which converts an electrical audio signal into a corresponding sound. The most widely used type of speaker is the dynamic speaker. The sound source (e.g., a sound recording or a microphone) must be amplified or strengthened with an audio power amplifier before the signal is sent to the speaker. The dynamic speaker was invented in 1925 by Edward W. Kellogg and Chester W. Rice issued as US Patent 1,707,570. Apr 2, 1929. The dynamic speaker operates on the same basic principle as a dynamic microphone, but in reverse, to produce sound from an electrical signal. When an alternating current electrical audio signal is applied to its voice coil, a coil of wire suspended in a circular gap between the poles of a permanent magnet, the coil is forced to move rapidly back and forth due to Faraday's law of induction, which causes a diaphragm (usually conically shaped) attached to the coil to move back and forth, pushing on the air to create sound waves. Besides this most common method, there are several alternative technologies that can be used to convert an electrical signal into sound.");
                        break;

                    case RECEIVERS:
                        categoryEntity
                                .setCategoryEnum(CategoryEnum.RECEIVERS)
                                .setDescription("An audio/video receiver (AVR) is a consumer electronics component used in a home theater. Its purpose is to receive audio and video signals from a number of sources, and to process them and provide power amplifiers to drive loudspeakers and route the video to displays such as a television, monitor or video projector. Inputs may come from a satellite receiver, radio, DVD players, Blu-ray Disc players, VCRs or video game consoles, among others. The AVR source selection and settings such as volume, are typically set by a remote controller.");
                        break;

                    case ACCESSORIES:
                        categoryEntity
                                .setCategoryEnum(CategoryEnum.ACCESSORIES)
                                .setDescription("Audio equipment refers to devices that reproduce, record, or process sound. This includes microphones, radio receivers, AV receivers, CD players, tape recorders, amplifiers, mixing consoles, effects units, and loudspeakers. Audio equipment is widely used in many different scenarios, such as concerts, bars, meeting rooms and the home where there is a need to reproduce, record and enhance sound volume.");
                        break;

                    default:
                        categoryEntity
                                .setCategoryEnum(CategoryEnum.HOME_CINEMA)
                                .setDescription("Home cinema, also called home theaters or theater rooms, are home entertainment audio-visual systems that seek to reproduce a movie theater experience and mood using consumer electronics-grade video and audio equipment that is set up in a room or backyard of a private home. In the 1980s, home cinemas typically consisted of a movie pre-recorded on a LaserDisc or VHS tape; a LaserDisc or VHS player; and a heavy, bulky large-screen cathode ray tube TV set, although sometimes CRT projectors were used instead. In the 2000s, technological innovations in sound systems, video player equipment and TV screens and video projectors have changed the equipment used in home cinema set-ups and enabled home users to experience a higher-resolution screen image, improved sound quality and components that offer users more options (e.g., many of the more expensive Blu-ray players in 2016 can also \"stream\" movies and TV shows over the Internet using subscription services such as Netflix). The development of Internet-based subscription services means that 2016-era home theatre users do not have to commute to a video rental store as was common in the 1980s and 1990s (nevertheless, some movie enthusiasts buy DVD or Blu-ray discs of their favourite content)");
                        break;

                }

                categoryEntities.add(categoryEntity);
            }
            categoryRepository.saveAll(categoryEntities);
        }
    }
}
