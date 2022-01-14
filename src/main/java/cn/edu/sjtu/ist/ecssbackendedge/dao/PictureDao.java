package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning.Picture;
import java.util.*;

/**
 * @author dyanjun
 * @date 2022/1/14 8:37
 */
public interface PictureDao {
    Picture insertPicture(Picture picture);

    List<Picture> getPictureByMLId(String id);
}
