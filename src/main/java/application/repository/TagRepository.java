package application.repository;

import application.entity.Tag;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {
    /**
     * 通过tID返回标签
     *
     * @param tID
     * @return
     */
    Tag findByTId(int tID);

    /**
     * 删除标签
     *
     * @param tag
     */
    @Override
    void delete(Tag tag);

    List<Tag> findAll();
}