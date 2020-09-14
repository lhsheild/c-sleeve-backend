package com.sheildog.csleevebackend.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a7818
 */
//@Getter
//@Setter
@NoArgsConstructor
public class PagingDozer<T, K> extends Paging {
    @SuppressWarnings("unchecked")
    public PagingDozer(Page<T> pageT, Class<K> classK){
        this.initPageParameters(pageT);

        List<T> tList = pageT.getContent();

        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<K> voList = new ArrayList<>();
        tList.forEach(s -> {
            K vo = mapper.map(s, classK);
            voList.add(vo);
        });
        this.setItems(voList);
    }
}
