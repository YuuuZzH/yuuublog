package com.yuuu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.domain.entity.User;
import com.yuuu.service.CommentService;
import com.yuuu.constants.SystemConstants;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.Comment;
import com.yuuu.domain.vo.CommentVo;
import com.yuuu.domain.vo.PageVo;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.exception.SystemException;
import com.yuuu.mapper.CommentMapper;
import com.yuuu.service.UserService;
import com.yuuu.utils.BeanCopyUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-03-04 17:15:52
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId,SystemConstants.COMMENT_ROOT_ID);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);
        //分页查询
        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentList(page.getRecords());

        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_IS_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }



    /**
     * @description:根据根评论的id查询子评论集合，并返回
     * @author: Yuuu。
     * @date: 2023-03-05 14:43
     * @param: [id]
     * @return: java.util.List<com.yuuu.domain.vo.CommentVo>
     **/
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        List<CommentVo> commentVos = toCommentList(list);
        return commentVos;
    }


    /**
     * 封装函数，将评论人和被评论人的昵称赋值给根评论
     * @param list
     * @return
     */
    private List<CommentVo> toCommentList(List<Comment> list) {
        //先通过工具类给CommentVo中公共属性复制
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        return commentVoList.stream()
                //使用peek方法对列表中每个元素重新复制
                .peek(commentVo -> {
                    LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
                    //只返回需要的字段，降低数据库传输压力
                    queryWrapper1.select(User::getNickName,User::getAvatar)
                            .eq(User::getId,commentVo.getCreateBy());
                    User commentUser = userService.getOne(queryWrapper1);
                    commentVo.setUsername(commentUser==null?SystemConstants.CLOSED_USERNAME:commentUser.getNickName());
                    commentVo.setAvatar(commentUser==null?"":commentUser.getAvatar());
                    //如果是根评论则不需要进行查询
                    if (commentVo.getRootId()!=-1) {
                        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
                        queryWrapper2.select(User::getUserName,User::getNickName)
                                .eq(User::getId,commentVo.getToCommentUserId());
                        User toCommentUser = userService.getOne(queryWrapper2);
                        if (toCommentUser==null){
                            commentVo.setToCommentUserName(SystemConstants.CLOSED_USERNAME);
                        } else if (Strings.hasText(toCommentUser.getNickName()))
                            commentVo.setToCommentUserName(toCommentUser.getNickName());
                        else
                            commentVo.setToCommentUserName(toCommentUser.getUserName());
                    }
                })
                //转换为List
                .collect(Collectors.toList());
    }
}

