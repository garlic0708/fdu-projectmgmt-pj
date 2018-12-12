package application.service.impl;

import application.entity.Tag;
import application.repository.TagRepository;
import application.repository.UserRepository;
import application.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getTagList() {
        return tagRepository.findAll();
    }

    @Override
    public void addTag(String tagName) {
        Tag tag = new Tag();
        tag.setTagname(tagName);
        tagRepository.save(tag);
    }
}
