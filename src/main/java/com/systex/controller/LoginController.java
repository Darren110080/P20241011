package com.systex.controller;

import com.systex.model.Member;
import com.systex.model.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("member", new Member()); // 傳遞 Member 實體給註冊頁面
        return "register"; // 返回 register.jsp 頁面
    }

    @PostMapping("/register")
    public String registerMember(Member member, Model model) {
        // 檢查使用者名稱是否已存在
        Member existingMember = memberService.findMemberByUsername(member.getUsername());
        if (existingMember != null) {
            // 如果使用者名稱已存在，向模型中添加錯誤消息
            model.addAttribute("error", "帳號重複，請輸入其他帳號。");
            return "register"; // 返回到註冊頁面
        }

        // 儲存註冊的新會員
        memberService.saveMember(member);
        // 註冊成功，重定向到登入頁面並顯示成功消息
        model.addAttribute("successMessage", "註冊成功，請登入"); // 添加成功消息
        return "redirect:/login"; // 註冊後重定向到登入頁面
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login"; // 返回 login.jsp 頁面
    }

    @PostMapping("/login")
    public String loginMember(Member member, Model model, HttpServletRequest request) {
        Member existingMember = memberService.findMemberByUsername(member.getUsername());
        
        if (existingMember != null && existingMember.getPassword().equals(member.getPassword())) {
            // 登入成功，將使用者資訊存入 session
            HttpSession session = request.getSession();
            session.setAttribute("user", existingMember); // 將使用者資訊存入 session
            return "redirect:/index"; // 登入後重定向到 index.jsp 頁面
        } else {
            // 登入失敗，返回到登入頁面並顯示錯誤消息
            model.addAttribute("error", "使用者名稱或密碼錯誤");
            return "login"; // 返回 login.jsp 頁面
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 使 session 無效
        return "redirect:/login"; // 登出後重定向到登入頁面
    }

    @GetMapping("/index")
    public String showIndex(Model model) {
        return "index"; // 返回 index.jsp 頁面
    }
}
