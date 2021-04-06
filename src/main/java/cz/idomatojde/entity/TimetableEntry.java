package cz.idomatojde.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class TimetableEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Timetable timetable;

    @ManyToOne
    private Offer offer;

    private LocalTime entryStart;

    private Duration length;

    private String description;

    @OneToMany(mappedBy = "timetableEntry", targetEntity = TimetableChatMessage.class)
    private List<TimetableChatMessage> messages = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public LocalTime getEntryStart() {
        return entryStart;
    }

    public void setEntryStart(LocalTime entryStart) {
        this.entryStart = entryStart;
    }

    public Duration getLength() {
        return length;
    }

    public void setLength(Duration length) {
        this.length = length;
    }

    public List<TimetableChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<TimetableChatMessage> messages) {
        this.messages = messages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimetableEntry)) return false;
        TimetableEntry that = (TimetableEntry) o;
        return Objects.equals(getTimetable(), that.getTimetable()) &&
                Objects.equals(getOffer(), that.getOffer()) &&
                Objects.equals(getEntryStart(), that.getEntryStart()) &&
                Objects.equals(getLength(), that.getLength()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getMessages(), that.getMessages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimetable(), getOffer(), getEntryStart(), getLength(), getDescription(), getMessages());
    }
}
