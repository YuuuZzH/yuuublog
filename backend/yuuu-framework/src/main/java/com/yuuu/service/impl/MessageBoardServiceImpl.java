package com.yuuu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuuu.domain.ResponseResult;
import com.yuuu.domain.entity.LoginUser;
import com.yuuu.domain.entity.MessageBoard;
import com.yuuu.domain.entity.Sensitive;
import com.yuuu.domain.entity.User;
import com.yuuu.enums.AppHttpCodeEnum;
import com.yuuu.mapper.MessageBoardMapper;
import com.yuuu.service.MessageBoardService;
import com.yuuu.service.SensitiveService;
import com.yuuu.service.UserService;
import com.yuuu.utils.PathUtils;
import com.yuuu.utils.SecurityUtils;
import com.yuuu.utils.SensitiveWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yuuu.utils.VisitorUtil.generateGuestUsername;


/**
 * (MessageBoard)表服务实现类
 *
 * @author makejava
 * @since 2024-01-26 14:58:19
 */
@Service("messageBoardService")
public class MessageBoardServiceImpl extends ServiceImpl<MessageBoardMapper, MessageBoard> implements MessageBoardService {
    @Autowired
    private AvatarService avatarService;

    @Autowired
    private UserService userService;

    @Autowired
    private SensitiveService sensitiveService;

    @Override
    public ResponseResult saveMessage(String message) {
        if (StrUtil.isBlank(message)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_NULL);
        }
        List<String> sensitives = sensitiveService.list().stream().map(Sensitive::getSensitives).collect(Collectors.toList());
        SensitiveWordUtil.initMap(sensitives);
        String filteredContent = SensitiveWordUtil.filterSensitiveWords(message);
        Long userId = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LoginUser){
             userId = SecurityUtils.getUserId();
        }
        MessageBoard messageBoard = new MessageBoard();
        if (userId == null){
            messageBoard.setUserName(generateGuestUsername());
            messageBoard.setMessage(filteredContent);
            messageBoard.setAvatar(avatarService.getRandomAvatar());
        }else{
            User user = userService.getById(userId);
            String avatar = "";
            if (StrUtil.isBlank(user.getAvatar())){
                avatar = avatarService.getRandomAvatar();
            }else{
                avatar = user.getAvatar();
            }
            messageBoard.setAvatar(avatar);
            messageBoard.setUserName(user.getUserName());
            messageBoard.setMessage(filteredContent);
        }
        save(messageBoard);
        return ResponseResult.okResult(messageBoard);
    }


}
