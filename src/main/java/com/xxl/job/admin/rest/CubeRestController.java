package com.xxl.job.admin.rest;

import com.xxl.job.admin.rest.vm.BuildCubeJobsRequestVM;
import com.xxl.job.admin.rest.vm.CreatCubeJobsRequestVM;
import com.xxl.job.admin.rest.vm.DimensionTableAttribute;
import com.xxl.job.admin.rest.vm.SourceTableAttribute;
import com.xxl.job.admin.service.CubeService;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wfeng on 2018/6/6.
 */
@RestController
@RequestMapping("/api/cube")
@Api(description = "任务管理接口")
public class CubeRestController {

    private static final Logger logger = LoggerFactory.getLogger(CubeRestController.class);

    @Resource
    private XxlJobService xxlJobService;

    @Resource
    private CubeService cubeService;

    @ApiOperation(value = "创建立方任务", notes = "")
    @PostMapping("/creatCubeJobs")
    public ReturnT<String> creatCubeJobs(@RequestBody CreatCubeJobsRequestVM requestVM) {
        logger.info("创建数据立方接口");

        //验证输入项
        if (requestVM.getCudeId() == 0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "请设置cubeId");
        }
        if (StringUtils.isBlank(requestVM.getCubeName())) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "请设置cube名");
        }
        if (StringUtils.isBlank(requestVM.getTriggerModel()) ||
                (!CreatCubeJobsRequestVM.EVERY_DAY.equals(requestVM.getTriggerModel()) &&
                        !CreatCubeJobsRequestVM.MANUAL.equals(requestVM.getTriggerModel()))) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "请设置触发模式:EVERY_DAY/MANUAL");
        }
        if (requestVM.getSourceTableList().isEmpty()) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "请设置源数据表项");
        }
        int primaryNum = 0;
        for (SourceTableAttribute sourceTable : requestVM.getSourceTableList()) {
            if (sourceTable.isPrimaryTable()) {
                primaryNum += 1;
            }
            if (sourceTable.isEmpty()) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "源数据表无效");
            }
        }
        if (primaryNum != 1) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "主数据表必须要指定且只能指定一个");
        }
        if (requestVM.getDimensionTableList().isEmpty()) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "请设置维度表项");
        }
        for (DimensionTableAttribute dimTable : requestVM.getDimensionTableList()) {
            if (dimTable.isEmpty()) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "维度表无效");
            }
        }

        return cubeService.createCube(requestVM);
    }

    @ApiOperation(value = "构建立方任务", notes = "")
    @PostMapping("/buildCubeJobs")
    public ReturnT<String> buildCubeJobs(@RequestBody BuildCubeJobsRequestVM requestVM) {
        logger.info("构建数据立方接口");

        return ReturnT.SUCCESS;
    }

    @ApiOperation(value = "清空立方任务", notes = "")
    @PostMapping("/clearCubeJobs")
    public ReturnT<String> clearCubeJobs(@RequestBody BuildCubeJobsRequestVM requestVM) {
        logger.info("清空数据立方接口");

        return ReturnT.SUCCESS;
    }

    @ApiOperation(value = "刷新立方任务", notes = "")
    @PostMapping("/refreshCubeJobs")
    public ReturnT<String> refreshCubeJobs(@RequestBody BuildCubeJobsRequestVM requestVM) {
        logger.info("刷新数据立方接口");

        return ReturnT.SUCCESS;
    }

    @ApiOperation(value = "销毁立方任务", notes = "")
    @PostMapping("/destroyCubeJobs")
    public ReturnT<String> destroyCubeJobs(@RequestBody BuildCubeJobsRequestVM requestVM) {
        logger.info("销毁数据立方接口");

        return ReturnT.SUCCESS;
    }

//    @ApiOperation(value = "执行任务接口", notes = "")
//    @GetMapping("/trigger/{id}")
//    public ReturnT<String> triggerJob(@PathVariable int id) {
//        return xxlJobService.triggerJob(id);
//    }
}