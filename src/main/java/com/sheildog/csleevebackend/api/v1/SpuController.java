package com.sheildog.csleevebackend.api.v1;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.model.Spu;
import com.sheildog.csleevebackend.service.SpuService;
import com.sheildog.csleevebackend.service.SpuServiceImpl;
import com.sheildog.csleevebackend.vo.SpuSimplifyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

/**
 * @author a7818
 */
@RestController
@RequestMapping("/spu")
@Validated
public class SpuController {
    @Autowired
    private SpuService spuService;

    @GetMapping("/id/{id}/detail")
    public Spu getDetail(@PathVariable @Positive Long id){
        Spu spu = spuService.getSpu(id);
        if (spu==null){
            throw new NotFoundException(30003);
        }
        return spu;
    }

    @GetMapping("/id/{id}/simplify")
    public SpuSimplifyVO getSimplifySpu(@PathVariable @Positive Long id){
        Spu spu = spuService.getSpu(id);
        if (spu==null){
            throw new NotFoundException(30003);
        }
        SpuSimplifyVO vo = new SpuSimplifyVO();
        BeanUtils.copyProperties(spu, vo);
        return vo;
    }

    @GetMapping("/latest")
    public List<SpuSimplifyVO> getLatestSpuList(
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count
    ){
        Page<Spu> list = spuService.getLatestPagingSpu(start, count);
        if (list==null){
            throw new NotFoundException(30003);
        }
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<SpuSimplifyVO> vos = new ArrayList<>();
        list.forEach(s->{
            SpuSimplifyVO vo = mapper.map(s, SpuSimplifyVO.class);
            vos.add(vo);
        });
        return vos;
    }
}
