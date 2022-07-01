package com.djh.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.djh.common.lang.Result;
import com.djh.entity.Blog;
import com.djh.service.BlogService;
import com.djh.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author djh
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageIndex) {
        Page page = new Page(pageIndex, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.result(pageData);
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "暂无此博客");
        return Result.result(blog);
    }


    @RequiresAuthentication
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Long blogId = blog.getId();
        Blog data;
        if (blogId != null) {
            data = blogService.getById(blogId);
            Assert.isTrue(data.getUserId().equals(ShiroUtil.getProfile().getId()), "没有权限");
        } else {
            Blog temp = blogService.list(new QueryWrapper<Blog>().eq("title", blog.getTitle())).get(0);
            Assert.isNull(temp , "该标题已存在");
            data = new Blog();
            data.setUserId(ShiroUtil.getProfile().getId());
            data.setCreated(LocalDateTime.now());
            data.setStatus(0);
        }
        BeanUtil.copyProperties(blog, data, "id", "userId", "created", "status");
        blogService.saveOrUpdate(data);
        return Result.result(null);
    }


}
