package skyglass.composer.product.mapper;

import skyglass.composer.product.model.Movie;
import skyglass.composer.product.rest.dto.CreateMovieRequest;
import skyglass.composer.product.rest.dto.MovieDto;
import skyglass.composer.product.rest.dto.UpdateMovieRequest;
import skyglass.composer.product.service.UserExtraService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = UserExtraService.class
)
public abstract class MovieMapper {

    @Autowired
    protected UserExtraService userExtraService;

    public abstract Movie toMovie(CreateMovieRequest createMovieRequest);

    public abstract void updateMovieFromDto(UpdateMovieRequest updateMovieRequest, @MappingTarget Movie movie);

    public abstract MovieDto toMovieDto(Movie movie);

    @Mapping(
            target = "avatar",
            expression = "java( userExtraService.getUserExtra(comment.getUsername()).isPresent() ? userExtraService.getUserExtra(comment.getUsername()).get().getAvatar() : comment.getUsername() )"
    )
    public abstract MovieDto.CommentDto toMovieDtoCommentDto(Movie.Comment comment);

}