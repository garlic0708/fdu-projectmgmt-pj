package application.repository;

import application.entity.JoinEvent;
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
public class JoinEventRepositoryTest {
    @Autowired
    private JoinEventRepository joinEventRepository;

    @Test
    @Rollback
    public void findByJeId() {
        JoinEvent joinEvent = new JoinEvent();
        joinEvent.seteId(15);
        joinEvent.setuId(2);
        joinEventRepository.save(joinEvent);
        int jeId = joinEvent.getJeId();
        JoinEvent test = joinEventRepository.findByJeId(jeId);
        assertEquals(test.geteId(), new Integer(15));
        assertEquals(test.getuId(), new Integer(2));
    }

    @Test
    @Rollback
    public void findByUId() {
        JoinEvent joinEvent1 = new JoinEvent();
        joinEvent1.seteId(5);
        joinEvent1.setuId(2);
        joinEventRepository.save(joinEvent1);
        JoinEvent eventTag = new JoinEvent();
        eventTag.seteId(8);
        eventTag.setuId(7);
        joinEventRepository.save(eventTag);
        JoinEvent eventTag2 = new JoinEvent();
        eventTag2.seteId(15);
        eventTag2.setuId(7);
        joinEventRepository.save(eventTag2);
        List<JoinEvent> list = joinEventRepository.findByUId(7);
        JoinEvent test1 = list.get(0);
        JoinEvent test2 = list.get(1);
        assertEquals(test1.geteId(), new Integer(8));
        assertEquals(test2.geteId(), new Integer(15));
    }

    @Test
    @Rollback
    public void findByEId() {
        JoinEvent joinEvent1 = new JoinEvent();
        joinEvent1.seteId(15);
        joinEvent1.setuId(2);
        joinEventRepository.save(joinEvent1);
        JoinEvent joinEvent = new JoinEvent();
        joinEvent.seteId(8);
        joinEvent.setuId(7);
        joinEventRepository.save(joinEvent);
        JoinEvent joinEvent2 = new JoinEvent();
        joinEvent2.seteId(15);
        joinEvent2.setuId(3);
        joinEventRepository.save(joinEvent2);
        List<JoinEvent> list = joinEventRepository.findByEId(15);
        JoinEvent test1 = list.get(0);
        JoinEvent test2 = list.get(1);
        assertEquals(test1.getuId(), new Integer(2));
        assertEquals(test2.getuId(), new Integer(3));
    }

    @Test
    @Rollback
    public void findByJeState() {
        JoinEvent joinEvent1 = new JoinEvent();
        joinEvent1.seteId(15);
        joinEvent1.setuId(2);
        joinEvent1.setJeState("absent");
        joinEventRepository.save(joinEvent1);
        JoinEvent joinEvent = new JoinEvent();
        joinEvent.seteId(8);
        joinEvent.setuId(7);
        joinEvent.setJeState("sign");
        joinEventRepository.save(joinEvent);
        JoinEvent joinEvent2 = new JoinEvent();
        joinEvent2.seteId(15);
        joinEvent2.setuId(3);
        joinEvent2.setJeState("absent");
        joinEventRepository.save(joinEvent2);
        List<JoinEvent> list = joinEventRepository.findByJeState("absent");
        JoinEvent test1 = list.get(0);
        JoinEvent test2 = list.get(1);
        assertEquals(test1.getuId(), new Integer(2));
        assertEquals(test2.getuId(), new Integer(3));
    }

    @Test
    @Rollback
    public void deleteByJeId() {
        JoinEvent eventTag = new JoinEvent();
        eventTag.seteId(8);
        eventTag.setuId(3);
        joinEventRepository.save(eventTag);
        int jeId = eventTag.getJeId();
        joinEventRepository.deleteByJeId(jeId);
        assertEquals(joinEventRepository.findByJeId(jeId), null);
    }

    @Test
    @Rollback
    public void deleteByUId() {
        JoinEvent eventTag1 = new JoinEvent();
        eventTag1.seteId(15);
        eventTag1.setuId(3);
        joinEventRepository.save(eventTag1);
        JoinEvent eventTag = new JoinEvent();
        eventTag.seteId(8);
        eventTag.setuId(7);
        joinEventRepository.save(eventTag);
        JoinEvent eventTag2 = new JoinEvent();
        eventTag2.seteId(15);
        eventTag2.setuId(5);
        joinEventRepository.save(eventTag2);
        joinEventRepository.deleteByEId(15);
        assertEquals(joinEventRepository.findByEId(15).size(), 0);
        assertNotEquals(joinEventRepository.findByEId(8).size(), 0);
    }

    @Test
    @Rollback
    public void deleteByEId() {
        JoinEvent eventTag1 = new JoinEvent();
        eventTag1.seteId(5);
        eventTag1.setuId(3);
        joinEventRepository.save(eventTag1);
        JoinEvent eventTag = new JoinEvent();
        eventTag.seteId(8);
        eventTag.setuId(7);
        joinEventRepository.save(eventTag);
        JoinEvent eventTag2 = new JoinEvent();
        eventTag2.seteId(15);
        eventTag2.setuId(7);
        joinEventRepository.save(eventTag2);
        joinEventRepository.deleteByUId(7);
        assertEquals(joinEventRepository.findByUId(7).size(), 0);
        assertNotEquals(joinEventRepository.findByUId(3).size(), 0);
    }

    @Test
    @Rollback
    public void deleteJoinEvent() {
        JoinEvent eventTag = new JoinEvent();
        eventTag.seteId(10);
        eventTag.setuId(3);
        joinEventRepository.save(eventTag);
        int etId = eventTag.getJeId();
        joinEventRepository.delete(eventTag);
        assertEquals(joinEventRepository.findByJeId(etId), null);
    }

    @Test
    @Rollback
    public void updateJoinEvent() {
        JoinEvent eventTag = new JoinEvent();
        eventTag.seteId(10);
        eventTag.setuId(3);
        joinEventRepository.save(eventTag);
        int id = eventTag.getJeId();
        JoinEvent event1 = joinEventRepository.findByJeId(id);
        eventTag.setuId(5);
        joinEventRepository.save(event1);
        JoinEvent test = joinEventRepository.findByJeId(id);
        assertEquals(test.getuId(), new Integer(5));
        assertEquals(test.geteId(), new Integer(10));
    }

}
