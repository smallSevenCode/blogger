package org.example.service.impl;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.PageAccept;
import org.example.common.PageResult;
import org.example.dto.PathQuery;
import org.example.entity.Path;
import org.example.mapper.PathMapper;
import org.example.service.PathService;
import org.example.vo.PathVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 接口表 服务实现类
 * </p>
 *
 * @author 张鹏
 * @since 2022-06-19
 */
@Service
public class PathServiceImpl extends ServiceImpl<PathMapper, Path> implements PathService {


    /**
     * 分页
     *
     * @param pageAccept
     * @return
     */
    @Override
    public PageResult<PathVo> listPage(PageAccept<PathQuery> pageAccept) {
        PathQuery params = pageAccept.getParams();
        IPage<Path> buildPage = pageAccept.buildPage(Path.class);
        Wrapper<Path> wrapper = Wrappers.<Path>lambdaQuery()
                .like(StrUtil.isNotBlank(params.getUrl()), Path::getUrl, params.getUrl())
                .like(StrUtil.isNotBlank(params.getTags()), Path::getTags, params.getTags())
                .like(StrUtil.isNotBlank(params.getName()), Path::getName, params.getName())
                .eq(StrUtil.isNotBlank(params.getMode()), Path::getMode, params.getMode());
        IPage<Path> page = page(buildPage, wrapper);
        return pageAccept.buildPage(page, PathVo.class);
    }

    /**
     * 保存
     *
     * @param url   http://192.168.1.2:29527/api/v2/api-docs?group=测试分组
     * @param token
     */
    @Override
    public void save(String url, String token) {
        String result = HttpRequest.get(url)
                .header("Cookie", token)
                .execute()
                .body();
        JSONObject swaggerObject = JSONUtil.parseObj(result);
        Object pathsJson = swaggerObject.get("paths");
        JSONObject parseObj = JSONUtil.parseObj(pathsJson);

        List<Path> pathList = new ArrayList<>();
        parseObj.forEach((pathUrl, modeJson) -> {
            Path path = new Path();
            JSONObject modeObject = JSONUtil.parseObj(modeJson);
            path.setId(HashUtil.mixHash(pathUrl));
            path.setUrl(pathUrl);
            modeObject.forEach((mode, summaryJson) -> {
                path.setMode(mode);
                JSONObject summaryObject = JSONUtil.parseObj(summaryJson);
                Object summary = summaryObject.get("summary");
                path.setName(summary.toString());
                Object tagsJson = summaryObject.get("tags");
                JSONArray tagsList = JSONUtil.parseArray(tagsJson);
                Object tags = tagsList.get(0);
                path.setTags(tags.toString());
            });
            pathList.add(path);
        });
        remove(Wrappers.lambdaQuery());
        saveBatch(pathList);
    }
}
