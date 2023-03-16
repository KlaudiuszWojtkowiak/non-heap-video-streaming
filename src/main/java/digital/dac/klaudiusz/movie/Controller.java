package digital.dac.klaudiusz.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(value="/movie")
@RequiredArgsConstructor
@Slf4j
class Controller {

    private final Storage storage;

    @SuppressWarnings("java:S1215")
    @PostMapping()
    String saveMovie(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException {
        //by default this way of reading a MultipartFile is limited by int size. If case of very large files, tou may face "Required array length is too large" error
        storage.put(name, file);
        log.info(Information.getMemoryInformation(storage.getTotalNoHeapMemoryUsage()));
        return "Video saved successfully.";
    }

    @GetMapping("{name}")
    ResponseEntity<Resource> getMovieByName(@PathVariable String name) {
        return storage.pull(name)
                .map(Movie::movieByteBuffer)
                .map(ByteBuffer::slice)
                .map(ByteBufferBackedInputStream::new)
                .map(InputStreamResource::new)
                .<ResponseEntity<Resource>>map(resource ->
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .body(resource))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("titles")
    Set<String> getMovieNames(){
        return storage.getMovieNames();
    }

}
