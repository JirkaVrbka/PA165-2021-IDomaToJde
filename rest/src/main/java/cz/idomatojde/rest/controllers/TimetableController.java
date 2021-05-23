package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.timetable.AddTimetableDTO;
import cz.idomatojde.dto.timetable.CreateTimetableEntryDTO;
import cz.idomatojde.dto.timetable.MoveTimetableEntryDTO;
import cz.idomatojde.dto.timetable.TimetableDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.dto.user.UserDTO;
import cz.idomatojde.facade.TimetableFacade;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller responsible for all things concerning Timetables
 *
 * @author Michal Hazdra
 */
@Api(tags = "Timetables Endpoint")
@RestController
@RequestMapping("timetables")
public class TimetableController extends
        AuthBaseRESTController<TimetableFacade, AddTimetableDTO, TimetableDTO> {
    @Inject
    public TimetableController(UserFacade userFacade, TimetableFacade timetables) {
        super(userFacade, timetables);
    }

    @GetMapping("entry/{entryId}")
    ResponseEntity<TimetableEntryDTO> getEntryById(@RequestHeader(value = "token") String token, @PathVariable long entryId) {
        if (notAuthenticated(token)) return unauthorized(null);

        return ok(facade.getEntryById(entryId));
    }

    @GetMapping("forWeek/now")
    ResponseEntity<TimetableDTO> getForCurrentWeek(@RequestHeader(value = "token") String token) {
        if (notAuthenticated(token)) return unauthorized(null);

        UserDTO u = isAuthenticated(token);
        return ok(facade.getTimetableForCurrentWeek(u.getId()));
    }

    @GetMapping("forWeek/{year}/{week}")
    ResponseEntity<TimetableDTO> getForWeek(@RequestHeader(value = "token") String token, @PathVariable int year, @PathVariable int week) {
        if (notAuthenticated(token)) return unauthorized(null);

        UserDTO u = isAuthenticated(token);
        return ok(facade.getTimetableForDate(u.getId(), year, week));
    }

    @PutMapping("registerEntry")
    ResponseEntity<Void> registerEntry(@RequestHeader(value = "token") String token, CreateTimetableEntryDTO dto) {
        if (notAuthenticated(token)) return unauthorized();

        facade.registerEntry(dto);
        return ok().build();
    }

    @PostMapping("moveEntry")
    ResponseEntity<Void> moveEntry(@RequestHeader(value = "token") String token, MoveTimetableEntryDTO dto) {
        if (notAuthenticated(token)) return unauthorized();

        facade.moveTimetableEntry(dto);
        return ok().build();
    }
}