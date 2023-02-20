package digital.dac.klaudiusz.movie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.*;

import static digital.dac.klaudiusz.movie.Information.formatSize;
import static java.lang.String.format;

@Component
@Slf4j
class Storage {

    private final Map<String, Movie> movieLibrary = new HashMap<>();

    public void put(String name, byte[] bytes) {
        var length = bytes.length;
        var byteBuffer = ByteBuffer.allocateDirect(length);
        byteBuffer.put(bytes);
        byteBuffer.position(0);

        movieLibrary.put(name, new Movie(byteBuffer, length));
        log.info(format("Added a new movie '%s' with size %s", name, formatSize(length)));
    }

    public Optional<Movie> pull(String name) {
        return Optional.of(movieLibrary.get(name));
    }

    public Set<String> getMovieNames() {
        return movieLibrary.keySet();
    }

    public Long getTotalNoHeapMemoryUsage() {
        return movieLibrary.values().stream()
                .map(Movie::size)
                .map(Long::valueOf)
                .reduce(0L, Long::sum);
    }


}
