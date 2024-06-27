package ghojeong.image.repository;

import ghojeong.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, String> {

    @Query("SELECT i FROM Image i "
            + " WHERE i.image = :image ")
    Optional<Image> findBy(@Param("image") String image);
}
