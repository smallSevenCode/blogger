package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.common.PageAccept;
import org.example.common.PageResult;
import org.example.dto.PathQuery;
import org.example.entity.Path;
import org.example.vo.PathVo;

/**
 * <p>
 * 接口表 服务类
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-19
 */
public interface PathService extends IService<Path> {


    /**
     * 分页
     *
     * @param pageAccept
     * @return
     */
    PageResult<PathVo> listPage(PageAccept<PathQuery> pageAccept);

    /**
     * 保存
     *
     * @param url   http://192.168.1.2:29527/api/v2/api-docs?group=测试分组
     * @param token
     */
    void save(String url, String token);
}
