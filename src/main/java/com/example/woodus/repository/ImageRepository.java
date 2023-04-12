package com.example.woodus.repository;
import com.example.woodus.model.Image;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ImageRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ImageRepository(DataSource dataSource){
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long addImage(Image image){
        String sql = "INSERT INTO image(name, type, pic_byte) VALUES (:name, :type, :picByte)";
        SqlParameterSource parameter = new MapSqlParameterSource("name", image.getName())
                .addValue("type", image.getType())
                .addValue("picByte", image.getPic_byte());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, parameter, keyHolder);
        return keyHolder.getKey().longValue();
    }
}