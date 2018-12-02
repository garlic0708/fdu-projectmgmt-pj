package application.repository;

import application.entity.EventTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class EventTagRepositoryTest {
    @Autowired
    private EventTagRepository eventTagRepository;

    @Test
    @Rollback
    public void findByEtId() {
        EventTag eventTag = new EventTag();
        eventTag.seteId(15);
        eventTag.settId(2);
        eventTagRepository.save(eventTag);
        int etId = eventTag.getEtId();
        EventTag test = eventTagRepository.findByEtId(etId);
        assertEquals(test.geteId(), new Integer(15));
        assertEquals(test.gettId(), new Integer(2));
    }

    @Test
    @Rollback
    public void findByEId() {
        EventTag eventTag1 = new EventTag();
        eventTag1.seteId(15);
        eventTag1.settId(2);
        eventTagRepository.save(eventTag1);
        EventTag eventTag = new EventTag();
        eventTag.seteId(8);
        eventTag.settId(7);
        eventTagRepository.save(eventTag);
        EventTag eventTag2 = new EventTag();
        eventTag2.seteId(15);
        eventTag2.settId(5);
        eventTagRepository.save(eventTag2);
        List<EventTag> list = eventTagRepository.findByEId(15);
        EventTag test1 = list.get(0);
        EventTag test2 = list.get(1);
        assertEquals(test1.gettId(), new Integer(2));
        assertEquals(test2.gettId(), new Integer(5));
    }

    @Test
    @Rollback
    public void findByTId() {
        EventTag eventTag1 = new EventTag();
        eventTag1.seteId(5);
        eventTag1.settId(3);
        eventTagRepository.save(eventTag1);
        EventTag eventTag = new EventTag();
        eventTag.seteId(8);
        eventTag.settId(3);
        eventTagRepository.save(eventTag);
        EventTag eventTag2 = new EventTag();
        eventTag2.seteId(15);
        eventTag2.settId(5);
        eventTagRepository.save(eventTag2);
        List<EventTag> list = eventTagRepository.findByTId(3);
        EventTag test1 = list.get(0);
        EventTag test2 = list.get(1);
        assertEquals(test1.geteId(), new Integer(5));
        assertEquals(test2.geteId(), new Integer(8));
    }

    @Test
    @Rollback
    public void deleteByEtId() {
        EventTag eventTag = new EventTag();
        eventTag.seteId(8);
        eventTag.settId(3);
        eventTagRepository.save(eventTag);
        int etId = eventTag.getEtId();
        eventTagRepository.deleteByEtId(etId);
        assertEquals(eventTagRepository.findByEtId(etId), null);
    }

    @Test
    @Rollback
    public void deleteByEId() {
        EventTag eventTag1 = new EventTag();
        eventTag1.seteId(15);
        eventTag1.settId(3);
        eventTagRepository.save(eventTag1);
        EventTag eventTag = new EventTag();
        eventTag.seteId(8);
        eventTag.settId(7);
        eventTagRepository.save(eventTag);
        EventTag eventTag2 = new EventTag();
        eventTag2.seteId(15);
        eventTag2.settId(5);
        eventTagRepository.save(eventTag2);
        eventTagRepository.deleteByEId(15);
        assertEquals(eventTagRepository.findByEId(15).size(), 0);
        assertNotEquals(eventTagRepository.findByEId(8).size(), 0);
    }

    @Test
    @Rollback
    public void deleteByTId() {
        EventTag eventTag1 = new EventTag();
        eventTag1.seteId(5);
        eventTag1.settId(3);
        eventTagRepository.save(eventTag1);
        EventTag eventTag = new EventTag();
        eventTag.seteId(8);
        eventTag.settId(7);
        eventTagRepository.save(eventTag);
        EventTag eventTag2 = new EventTag();
        eventTag2.seteId(15);
        eventTag2.settId(7);
        eventTagRepository.save(eventTag2);
        eventTagRepository.deleteByTId(7);
        assertEquals(eventTagRepository.findByTId(7).size(), 0);
        assertNotEquals(eventTagRepository.findByTId(3).size(), 0);
    }

    @Test
    @Rollback
    public void deleteEventTag() {
        EventTag eventTag = new EventTag();
        eventTag.seteId(10);
        eventTag.settId(3);
        eventTagRepository.save(eventTag);
        int etId = eventTag.getEtId();
        eventTagRepository.delete(eventTag);
        assertEquals(eventTagRepository.findByEtId(etId), null);
    }

    @Test
    @Rollback
    public void updateEventTag(){
        EventTag eventTag = new EventTag();
        eventTag.seteId(10);
        eventTag.settId(3);
        eventTagRepository.save(eventTag);
        int id = eventTag.getEtId();
        EventTag event1 = eventTagRepository.findByEtId(id);
        eventTag.settId(5);
        eventTagRepository.save(event1);
        EventTag test = eventTagRepository.findByEtId(id);
        assertEquals(test.gettId(), new Integer(5));
        assertEquals(test.geteId(), new Integer(10));
    }
}
