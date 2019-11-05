package com.ws.service.impl;

import com.ws.dao.TagsRepository;
import com.ws.exception.NotFoundException;
import com.ws.pojo.Tag;
import com.ws.service.TagService;
import com.ws.util.StringToList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Syen
 * @date 2019/10/31 0031-下午 14:01
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagsRepository tagsRepository;

    StringToList stringToList = new StringToList();

    @Transactional//放进事务中
    @Override
    public Tag saveTag(Tag tag) {
        return tagsRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagsRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagsRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagsRepository.findById(id).orElse(null);
        if(t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,t);
        return tagsRepository.save(t);
    }

    @Override
    public List<Tag> ListTag() {
        return tagsRepository.findAll();
    }

    @Override
    public Tag getTagByName(String name) {
        return tagsRepository.findByName(name);
    }

    @Override
    public List<Tag> ListTag(String ids) {

        return tagsRepository.findAllById(stringToList.convertToList(ids));
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagsRepository.deleteById(id);

    }
}
