package com.yuuu.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * Redis使用FastJson序列化
 *
 * @author sg
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    static {
        // 关闭全局 autoType 支持：Fastjson 反序列化 RCE 的经典高危开关
        ParserConfig.getGlobalInstance().setAutoTypeSupport(false);
        // 改为白名单：只放行本项目类型与 JDK 基础容器/类型，缓存仍能还原为具体 VO
        ParserConfig.getGlobalInstance().addAccept("com.yuuu.");
        ParserConfig.getGlobalInstance().addAccept("java.util.");
        ParserConfig.getGlobalInstance().addAccept("java.lang.");
        ParserConfig.getGlobalInstance().addAccept("java.time.");
        ParserConfig.getGlobalInstance().addAccept("java.math.");
    }

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return JSON.parseObject(str, clazz);
    }


    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}