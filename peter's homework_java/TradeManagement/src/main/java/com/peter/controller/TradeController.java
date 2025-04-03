package com.peter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.peter.common.AjaxResult;
import com.peter.common.PageDomain;
import com.peter.common.TableDataInfo;
import com.peter.common.TableSupport;
import com.peter.domain.TradeInfo;
import com.peter.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;


    @GetMapping("/list")
    public TableDataInfo list(TradeInfo tradeInfo) {
        List<TradeInfo> list = tradeService.selectTradeInfoList(tradeInfo);
        return getDataTable(list);
    }

    /**
     * 获取详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(tradeService.selectTradeInfoById(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public AjaxResult add(@RequestBody TradeInfo tradeInfo) {
        return tradeService.insertTradeInfo(tradeInfo) > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 修改加班统计
     */
    @PutMapping
    public AjaxResult edit(@RequestBody TradeInfo tradeInfo) {
        return tradeService.updateTradeInfo(tradeInfo) > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 删除加班统计
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return tradeService.deleteTradeInfoByIds(ids) > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @GetMapping("/listAll")
    public AjaxResult getAllTradeInfo() {
        List<TradeInfo> tradeInfoList = tradeService.getAllTradeInfo();
        return AjaxResult.success(tradeInfoList);
    }


    protected TableDataInfo getDataTable(List<?> list) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain != null ? pageDomain.getPageNum() : 1;
        Integer pageSize = pageDomain != null ? pageDomain.getPageSize() : 10;
        if (list == null) {
            list = Collections.emptyList();
        }
        PageInfo<?> pageInfo = paginateList(list, pageNum, pageSize);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(200);
        rspData.setMsg("查询成功");
        rspData.setRows(pageInfo.getList());
        rspData.setTotal(list.size());
        return rspData;
    }


    public static <T> PageInfo<T> paginateList(List<T> list, int pageNum, int pageSize) {
        if (list == null) {
            list = new ArrayList<>();
        }
        // 计算起始索引和结束索引
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        if (startIndex > list.size()) {
            startIndex = 0;
            endIndex = 0;
        }
        // 截取当前页的数据
        List<T> currentPageData = list.subList(startIndex, endIndex);

        // 创建 Page 对象
        Page<T> page = new Page<>(pageNum, pageSize);
        page.setRecords(currentPageData);
        page.setTotal(list.size());

        // 创建 PageInfo 对象
        return new PageInfo<>(page.getRecords(), (int) page.getTotal());
    }


}
