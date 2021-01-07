package com.merlin.merlinsblog.web.admin;

import com.merlin.merlinsblog.po.Type;
import com.merlin.merlinsblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/type")
    public String type(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){

        model.addAttribute("page",typeService.listType(pageable));
//        typeService.listType(pageable);
        return "admin/typeManager";
    }

    @GetMapping("/type/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/typeInput";
    }

    @GetMapping("/type/{id}/input")
    //@PathVariable 注解接受地址中的参数
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/typeInput";
    }

// post get 方法不同，同名路径可以区分，但为了避免误区，所以不重复命名
    @PostMapping("/typeinput")
    public String save(@Valid Type type, BindingResult result,RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
//            添加自定义验证结果，和Type类中的验证属性名字保持一致
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/typeInput";
        }
        Type t = typeService.saveType(type);
        if( t == null){
            attributes.addFlashAttribute("message","新增失败");
        }else{

            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/type";
    }

    @PostMapping("/typeinput/{id}")
//@Valid Type type, BindingResult result 参数列表里 这两个要连在一起写，不然验证不起作用
//    @PathVariable 获取路径变量
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
//            添加自定义验证结果，和Type类中的验证属性名字保持一致
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if(result.hasErrors()){
            return "admin/typeInput";
        }
        Type t = typeService.updateType(id,type);
        if( t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else{

            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/type";
    }

    @GetMapping("/type/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/type";
    }


}
