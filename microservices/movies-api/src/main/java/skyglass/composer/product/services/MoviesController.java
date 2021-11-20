package skyglass.composer.product.services;

import static skyglass.composer.product.configuration.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import skyglass.composer.product.mapper.MovieMapper;
import skyglass.composer.product.model.Movie;
import skyglass.composer.product.rest.dto.AddCommentRequest;
import skyglass.composer.product.rest.dto.CreateMovieRequest;
import skyglass.composer.product.rest.dto.MovieDto;
import skyglass.composer.product.rest.dto.UpdateMovieRequest;
import skyglass.composer.product.service.MovieService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movies")
public class MoviesController {

	private final MovieService movieService;

	private final MovieMapper movieMapper;

	@GetMapping
	public List<MovieDto> getMovies() {
		return movieService.getMovies().stream()
				.map(movieMapper::toMovieDto)
				.collect(Collectors.toList());
	}

	@GetMapping("/{imdbId}")
	public MovieDto getMovie(@PathVariable String imdbId) {
		Movie movie = movieService.validateAndGetMovie(imdbId);
		return movieMapper.toMovieDto(movie);
	}

	@Operation(security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) })
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public MovieDto createMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest) {
		Movie movie = movieMapper.toMovie(createMovieRequest);
		movie = movieService.saveMovie(movie);
		return movieMapper.toMovieDto(movie);
	}

	@Operation(security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) })
	@PutMapping("/{imdbId}")
	public MovieDto updateMovie(@PathVariable String imdbId, @Valid @RequestBody UpdateMovieRequest updateMovieRequest) {
		Movie movie = movieService.validateAndGetMovie(imdbId);
		movieMapper.updateMovieFromDto(updateMovieRequest, movie);
		movie = movieService.saveMovie(movie);
		return movieMapper.toMovieDto(movie);
	}

	@Operation(security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) })
	@DeleteMapping("/{imdbId}")
	public MovieDto deleteMovie(@PathVariable String imdbId) {
		Movie movie = movieService.validateAndGetMovie(imdbId);
		movieService.deleteMovie(movie);
		return movieMapper.toMovieDto(movie);
	}

	@Operation(security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) })
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{imdbId}/comments")
	public MovieDto addMovieComment(@PathVariable String imdbId, @Valid @RequestBody AddCommentRequest addCommentRequest, Principal principal) {
		Movie movie = movieService.validateAndGetMovie(imdbId);
		Movie.Comment comment = new Movie.Comment(principal.getName(), addCommentRequest.getText(), LocalDateTime.now());
		movie.getComments().add(0, comment);
		movie = movieService.saveMovie(movie);
		return movieMapper.toMovieDto(movie);
	}

}
