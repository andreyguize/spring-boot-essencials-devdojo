package academy.devdojo.springboot.repository;

import academy.devdojo.springboot.domain.Anime;
import academy.devdojo.springboot.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when Successful")
    void save_PersistAnime_WhenSuccessful() {
        Anime animetoBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animetoBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();

        Assertions.assertThat(animeSaved.getId()).isNotNull();

        Assertions.assertThat(animeSaved.getName()).isEqualTo(animetoBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when Successful")
    void save_UpdateAnime_WhenSuccessful() {
        Anime animetoBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animetoBeSaved);

        animeSaved.setName("Overlord");

        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();

        Assertions.assertThat(animeUpdated.getId()).isNotNull();

        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when Successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Anime animetoBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animetoBeSaved);

        this.animeRepository.delete(animetoBeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns list of anime when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime animetoBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime animeSaved = this.animeRepository.save(animetoBeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertThat(animes)
                .isNotEmpty()
                .contains(animeSaved);
    }

    @Test
    @DisplayName("Find By Name returns empty list when no Anime is found")
    void findByName_EmptyList_WhenAnimeIsNotFound() {
        List<Anime> animes = this.animeRepository.findByName("anime");

        Assertions.assertThat(animes).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
        Anime anime = new Anime();
//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime))
                .withMessageContaining("The anime name cannot be empty");
    }
}