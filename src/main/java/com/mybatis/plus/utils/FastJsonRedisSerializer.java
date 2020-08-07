package com.mybatis.plus.utils;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Lenovo redis序列化工具
 * @param <T>
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {


	@Override
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		return JSON.toJSONBytes(t, SerializerFeature.WriteClassName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		String str = new String(bytes);
		ParserConfig parserConfig = new ParserConfig();
		parserConfig.setAutoTypeSupport(true);
		return (T) JSON.parse(str, parserConfig);
	}

}
