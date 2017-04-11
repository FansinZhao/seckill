package com.fansin.seckill.web;

import com.fansin.seckill.dto.SeckillExecution;
import com.fansin.seckill.dto.SeckillExposer;
import com.fansin.seckill.dto.SeckillResult;
import com.fansin.seckill.enums.SeckillState;
import com.fansin.seckill.exceptions.RepeatSeckillException;
import com.fansin.seckill.exceptions.SeckillCloseException;
import com.fansin.seckill.mybatis.entity.Seckill;
import com.fansin.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * [接口模式]
 * restfull接口:/模块/资源/{id}/功能
 * <p>
 * 1 查询所有秒杀产品 /seckill/list
 * 2 查询单个秒杀产品 /seckill/{seckillId}/detail
 * 3 查询秒杀接口 /seckill/{seckillId}/exposer
 * 4 执行秒杀接口 /seckill/{seckillId}/{md5}/execution
 * <p>
 * Created by zhaofeng on 17-4-10.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService service;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> list = service.getSeckillList();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = service.getSeckillById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public SeckillResult<SeckillExposer> exposeUrl(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<SeckillExposer> result;
        try {
            SeckillExposer exposer = service.exportSeckillUrl(seckillId);
            if (exposer == null) {
                return new SeckillResult(false, new SeckillExecution(seckillId, SeckillState.END));
            }
            result = new SeckillResult(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> executeSeckill(@PathVariable("seckillId") Long seckillId, @PathVariable("md5") String md5, @CookieValue(value = "killPhone", required = false) Long phone) {

        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        try {
            SeckillExecution execution = service.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatSeckillException e) {
            return new SeckillResult<SeckillExecution>(true, new SeckillExecution(seckillId, SeckillState.REPEAT_KILL));
        } catch (SeckillCloseException e) {
            return new SeckillResult<SeckillExecution>(true, new SeckillExecution(seckillId, SeckillState.END));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SeckillResult<SeckillExecution>(true, new SeckillExecution(seckillId, SeckillState.INNER_ERROR));
        }
    }


    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}
