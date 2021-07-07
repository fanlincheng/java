package com.example.mall.Service;

import com.example.mall.Form.ShippingAddForm;
import com.example.mall.MallApplicationTests;
import com.example.mall.pojo.Shipping;
import com.example.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class ShippingServiceTest extends MallApplicationTests {
    @Autowired
    private ShippingService shippingService;

    @Test
    public void getAllShipping() {
        ResponseVo<PageInfo> allShipping = shippingService.getAllShipping(1,1,5);
        log.info("allShipping={}",allShipping);

    }

    @Before
    public void addShipping() {
        ShippingAddForm shippingAddForm=new ShippingAddForm("小小","135","1881005231","北京",
                "北京","海淀区","学院路30号北京科技大学","110000");
        ResponseVo<Map<String, Integer>> mapResponseVo = shippingService.addShipping(1, shippingAddForm);
        log.info("新增：{}",mapResponseVo);
    }

    @Test
    public void deleteShipping() {
        ResponseVo responseVo = shippingService.deleteShipping(1, 9);
        log.info("删除后:{}",responseVo);

    }

    @Test
    public void updateShipping() {
        ShippingAddForm shippingAddForm=new ShippingAddForm("小小","135","1881005231","北京",
                "北京","海淀区","学院路30号北京科技大学","110000");
        Shipping shipping=new Shipping();
        ResponseVo responseVo = shippingService.updateShipping(1, 7,shippingAddForm);
        log.info("更新后{}",responseVo);

    }
}