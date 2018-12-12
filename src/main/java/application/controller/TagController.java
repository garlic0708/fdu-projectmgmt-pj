package application.controller;

import application.entity.forms.ResultMessage;
import application.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Creator: DreamBoy
 * Date: 2018/11/25.
 */
@Controller
public class TagController {
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(value = "${api.tag.add}/{tagName}", method = RequestMethod.POST)
    public ResponseEntity<?> addTag(@PathVariable("tagName") String tagName) {
        tagService.addTag(tagName);
        return ResponseEntity.ok().body(new ResultMessage("Add tag success"));
    }

    @RequestMapping(value = "${api.tag.list}", method = RequestMethod.GET)
    public ResponseEntity<?> getTagList() {
        return ResponseEntity.ok().body(tagService.getTagList());
    }
}
