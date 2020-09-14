package com.sheildog.csleevebackend.api.v1;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.sheildog.csleevebackend.bo.PageCounter;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.model.Spu;
import com.sheildog.csleevebackend.service.SpuService;
import com.sheildog.csleevebackend.service.SpuServiceImpl;
import com.sheildog.csleevebackend.util.CommonUtil;
import com.sheildog.csleevebackend.vo.Paging;
import com.sheildog.csleevebackend.vo.PagingDozer;
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
    public Spu getDetail(@PathVariable @Positive(message = "{id.positive}") Long id) {
        Spu spu = spuService.getSpu(id);
        if (spu == null) {
            throw new NotFoundException(30003);
        }
        return spu;
    }

    @GetMapping("/id/{id}/simplify")
    public SpuSimplifyVO getSimplifySpu(@PathVariable @Positive(message = "{id.positive}") Long id) {
        Spu spu = spuService.getSpu(id);
        if (spu == null) {
            throw new NotFoundException(30003);
        }
        SpuSimplifyVO vo = new SpuSimplifyVO();
        BeanUtils.copyProperties(spu, vo);
        return vo;
    }

    @GetMapping("/latest")
    public PagingDozer<Spu, SpuSimplifyVO> getLatestSpuList(
            @RequestParam(defaultValue = "0") Integer start,
            @RequestParam(defaultValue = "10") Integer count
    ) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = spuService.getLatestPagingSpu(pageCounter.getPage(), pageCounter.getCount());
        if (page.getTotalElements() == 0) {
            throw new NotFoundException(30003);
        }
        PagingDozer<Spu, SpuSimplifyVO> paging = new PagingDozer<>(page, SpuSimplifyVO.class);
        return paging;
    }

    @GetMapping("/by/category/{id}")
    public PagingDozer<Spu, SpuSimplifyVO> getByCategoryId(
            @PathVariable(name = "id") @Positive(message = "{id.positive}") Long id,
            @RequestParam(name = "is_root", defaultValue = "false") Boolean isRoot,
            @RequestParam(defaultValue = "0", name = "start") Integer start,
            @RequestParam(defaultValue = "10", name = "count") Integer count
    ) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page = this.spuService.getByCategory(id, isRoot, pageCounter.getPage(), pageCounter.getCount());
        if (page.getTotalElements() == 0) {
            throw new NotFoundException(30003);
        }
        return new PagingDozer<>(page, SpuSimplifyVO.class);
    }
}
