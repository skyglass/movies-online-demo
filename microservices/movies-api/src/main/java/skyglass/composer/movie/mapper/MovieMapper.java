package skyglass.composer.movie.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import skyglass.composer.movie.model.Movie;
import skyglass.composer.movie.rest.dto.CreateMovieRequest;
import skyglass.composer.movie.rest.dto.MovieDto;
import skyglass.composer.movie.rest.dto.UpdateMovieRequest;
import skyglass.composer.movie.service.UserExtraService;

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