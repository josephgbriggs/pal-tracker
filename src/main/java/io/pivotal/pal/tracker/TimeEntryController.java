package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TimeEntryController {
    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
//        ResponseEntity resp = new ResponseEntity(
//                timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
        TimeEntry createdEntry = timeEntryRepository.create(timeEntryToCreate);

        return ResponseEntity.created(
                URI.create(Long.toString(timeEntryToCreate.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(createdEntry);
    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if (timeEntry == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(timeEntry);
        }
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok().body(timeEntryRepository.list());
    }

    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry updatedTimeEntry = timeEntryRepository.update(timeEntryId, expected);
        return (updatedTimeEntry == null) ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(updatedTimeEntry);
    }

    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return ResponseEntity.noContent().build();
    }

}
