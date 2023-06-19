package com.yuuu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.service.ArticleTagService;
import com.yuuu.domain.entity.ArticleTag;
import com.yuuu.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-16 21:24:24
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

