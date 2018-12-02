package application.service;

import application.SpringBootWebApplication;
import application.entity.Tag;
import application.repository.TagRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {SpringBootWebApplication.class})
public class TagServiceTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagRepository tagRepository;

    private int id;
    private Tag tag;

    @Before
    public void add() {
        tag = new Tag();
        tag.setTagname("testTag");
        tag = tagRepository.save(tag);
        id = tag.gettId();
    }

    @Test
    @Rollback
    public void getTagList() throws Exception {
        List<Tag> tags = tagService.getTagList();
        assertEquals(tags.get(0).getTagname(), "testTag");
    }

    @After
    public void delete() {
        tagRepository.delete(tag);
    }
}