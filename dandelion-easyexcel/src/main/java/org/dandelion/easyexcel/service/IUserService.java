package org.dandelion.easyexcel.service;

import org.dandelion.easyexcel.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx6x
 * @since 2023/08/09
 */
public interface IUserService extends IService<User> {


    void export() throws IOException;

    void importData(List<Map<Integer, String>> dataList);
}
