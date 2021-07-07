package com.example.mall.Service.Impl;

import com.example.mall.Form.ShippingAddForm;
import com.example.mall.Service.ShippingService;
import com.example.mall.dao.ShippingMapper;
import com.example.mall.pojo.Shipping;
import com.example.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.mall.enums.ResponseEnum.ERROR;

@Service
@Slf4j
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<PageInfo> getAllShipping(Integer uid, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Shipping> shippings = shippingMapper.selectAllShipping(uid);
         PageInfo pageInfo=new PageInfo(shippings);
         pageInfo.setList(shippings);
         return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<Map<String, Integer>> addShipping(Integer uid, ShippingAddForm shippingAddForm) {
        Shipping shipping=new Shipping();
        BeanUtils.copyProperties(shippingAddForm,shipping);
        shipping.setUserId(uid);
        int rowNum = shippingMapper.insertSelective(shipping);
        if(rowNum<=0){
            return ResponseVo.error(ERROR);
        }
        Map<String,Integer> resMap=new HashMap<>();
        resMap.put("ShippingId",shipping.getId());
        return ResponseVo.success(resMap);
    }

    @Override
    public ResponseVo deleteShipping(Integer uid, Integer shippingId) {
        int rowNum = shippingMapper.deleteByUidAndPrimaryKey(uid,shippingId);
        if(rowNum<=0){
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.success();

    }

    @Override
    public ResponseVo updateShipping(Integer uid,Integer shippingId, ShippingAddForm shippingAddForm) {
        Shipping shipping=new Shipping();
        BeanUtils.copyProperties(shippingAddForm,shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);
        int rowNum = shippingMapper.updateByPrimaryKeySelective(shipping);
        if(rowNum<=0){
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.success();
    }
}
