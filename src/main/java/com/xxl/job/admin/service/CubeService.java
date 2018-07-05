package com.xxl.job.admin.service;


import com.xxl.job.admin.core.exception.CubeServiceException;
import com.xxl.job.admin.rest.vm.CreatCubeJobsRequestVM;
import com.xxl.job.core.biz.model.ReturnT;

/**
 * 数据立方相关服务
 */
public interface CubeService {

    /**
     * 创建立方任务
     *
     * @param request
     * @return
     * @throws CubeServiceException
     */
    ReturnT<String> createCube(CreatCubeJobsRequestVM request);

}
