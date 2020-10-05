package com.zhou.shoehome.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.zhou.shoehome.bean.PmsSkuAttrValue;
import com.zhou.shoehome.bean.PmsSkuImage;
import com.zhou.shoehome.bean.PmsSkuInfo;
import com.zhou.shoehome.bean.PmsSkuSaleAttrValue;
import com.zhou.shoehome.manage.mapper.PmsSkuAttrValueMapper;
import com.zhou.shoehome.manage.mapper.PmsSkuImageMapper;
import com.zhou.shoehome.manage.mapper.PmsSkuInfoMapper;
import com.zhou.shoehome.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.zhou.shoehome.service.ISkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhouzh6
 * @date 2020-10-05
 */
@Service
public class SkuServiceImpl implements ISkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Transactional
    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        // 添加pmsSkuInfo
        pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        // 添加平台属性
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

        // 添加销售属性
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        // 添加图片
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }
    }
}
