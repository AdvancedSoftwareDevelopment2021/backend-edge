package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.PictureDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning.Picture;
import cn.edu.sjtu.ist.ecssbackendedge.repository.PictureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dyanjun
 * @date 2022/1/14 8:38
 */
@Slf4j
@Component
public class PictureDaoImpl implements PictureDao {
    @Autowired
    PictureRepository pictureRepository;
    @Override
    public Picture insertPicture(Picture picture) {
        pictureRepository.save(picture);
        return picture;
    }

    @Override
    public List<Picture> getPictureByMLId(String id) {
        return pictureRepository.findPicturesByMlModalId(id);
    }
}
