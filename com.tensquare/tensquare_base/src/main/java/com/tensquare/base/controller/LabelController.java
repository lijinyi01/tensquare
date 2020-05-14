package com.tensquare.base.controller;
import com.tensquare.base.entities.Label;
import com.tensquare.base.entities.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 标签控制层
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {
    @Autowired
    private LabelService labelService;
    /**
     * 查询全部列表
     * @return
     */
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        String header = request.getHeader("Authorization");
        System.out.println(header);
        System.out.println("haha");

        return new Result(true, StatusCode.OK,"查询成功" ,labelService.findAll() );
    }
    /**
     * 根据ID查询标签
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成 功",labelService.findById(id));
    }
/**
 * 增加标签
 * @param label
 * @return
 */
@RequestMapping(method = RequestMethod.POST)
public Result save( @RequestBody Label label){
   labelService.save(label);
    return new Result(true,StatusCode.OK,"增加成功");
}
    /**
     * 修改标签
     * @param label
     * @return
     */
    @RequestMapping(value="/{id}" ,method = RequestMethod.PUT)
    public Result update(@RequestBody Label label,@PathVariable String   id){
        label.setId(id);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"更新成功");
    }
    /**
     * 删除标签
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}" ,method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
     labelService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }



    /**
     * 根据条件查询
     * @paramsearchMap
     * @return
     */

    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Label label){
         List<Label>  list= labelService.findSearch(label);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }


    @RequestMapping(value="/search/{page}/{size}",method = RequestMethod.POST)
    public Result pageQuery( @RequestBody Label label  ,@PathVariable int page,@PathVariable int size){
        Page<Label> pageData = labelService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Label>(pageData.getTotalElements(),pageData.getContent()));
    }

}
