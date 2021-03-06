package cz.idomatojde.rest.controllers;

import cz.idomatojde.dto.offer.OfferDTO;
import cz.idomatojde.dto.timetable.AddTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.ChangeTextTimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableChatMessageDTO;
import cz.idomatojde.dto.timetable.TimetableEntryDTO;
import cz.idomatojde.facade.OfferFacade;
import cz.idomatojde.facade.TimetableChatMessageFacade;
import cz.idomatojde.facade.TimetableFacade;
import cz.idomatojde.facade.UserFacade;
import cz.idomatojde.rest.controllers.base.AuthBaseRESTController;
import cz.idomatojde.rest.controllers.base.AuthState;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller responsible for all things concerning TimetableChatMessages
 *
 * @author Michal Hazdra
 */
@Api(tags = "TimetableChatMessages Endpoint")
@RestController
@RequestMapping("api/timetableChatMessages")
public class TimetableChatMessageController extends
        AuthBaseRESTController<TimetableChatMessageFacade, AddTimetableChatMessageDTO, TimetableChatMessageDTO> {

    private final TimetableFacade timetableFacade;
    private final OfferFacade offerFacade;

    @Inject
    public TimetableChatMessageController(UserFacade userFacade, TimetableFacade timetables, OfferFacade offers, TimetableChatMessageFacade chatMessages) {
        super(userFacade, chatMessages, false, false, true, false);
        timetableFacade = timetables;
        offerFacade = offers;
    }

    @GetMapping("allMessagesOfUser")
    ResponseEntity<List<TimetableChatMessageDTO>> getMessagesByUserId(@RequestHeader(value = "token") String token, @RequestParam Long userId) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized(null);
        if (!ownerOnlyPermission(auth, userId)) return forbidden(null);

        return ok(facade.getAllMessagesOfUser(userId));
    }

    @GetMapping("allMessagesOfTimetableEntry")
    ResponseEntity<List<TimetableChatMessageDTO>> getAllMessagesOfTimetableEntry(@RequestHeader(value = "token") String token, @RequestParam Long entryId) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized(null);
        if (!relatedOnlyPermission(auth, entryId)) return forbidden(null);

        return ok(facade.getAllMessagesOfTimetableEntry(entryId));
    }

    @PostMapping("changeMessage")
    ResponseEntity<Void> changeMessage(@RequestHeader(value = "token") String token, @RequestBody ChangeTextTimetableChatMessageDTO dto) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();
        if (!ownerOnlyPermission(auth, facade.getById(dto.getId()).getUserId())) return forbidden(null);

        facade.changeText(dto);
        return ok().build();
    }

    @PostMapping("deleteAllMessages")
    ResponseEntity<Void> deleteMessagesByUserId(@RequestHeader(value = "token") String token, @RequestParam long userId) {
        AuthState auth = isAuthenticated(token);
        if (!auth.authenticated()) return unauthorized();
        if (!auth.admin()) return forbidden();

        facade.deleteAllMessagesOfUser(userId);
        return ok().build();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean ownerOnlyPermission(AuthState principal, long userId) {
        return principal.principalId() == userId || principal.admin();
    }

    private boolean relatedOnlyPermission(AuthState principal, long entryId) {
        TimetableChatMessageDTO dto = facade.getById(entryId);
        TimetableEntryDTO entryDto = timetableFacade.getEntryById(dto.getTimetableEntryId());
        boolean owner = entryDto.getOffer().getOwner().getId() == principal.principalId();
        List<OfferDTO> subscribed = offerFacade.getAllSubscribedBy(userFacade.getById(principal.principalId()));
        boolean subscriber = subscribed.stream().anyMatch(f -> entryDto.getOffer() == f);
        return owner || subscriber || principal.admin();
    }

    @Override
    protected boolean isOwner(Long principalId, Long resourceId) {
        return facade.getById(resourceId).getUserId().equals(principalId);
    }

    @Override
    protected boolean allowedToRegister(AuthState state, AddTimetableChatMessageDTO addTimetableChatMessageDTO) {
        return relatedOnlyPermission(state, addTimetableChatMessageDTO.getTimetableEntryId());
    }
}
