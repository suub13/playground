package com.encore.playground.domain.feed.control;

import com.encore.playground.domain.feed.dto.FeedDto;
import com.encore.playground.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 피드 글을 반환하는 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    @PostMapping(value = "/modify")
    public String modify(int feedNo, String article, Model model) {
        List<FeedDto> feedAfterModify = feedService.modify(feedNo, article);
        model.addAttribute("feeds", feedAfterModify);
        return "feed/feed_main";
    }

    @GetMapping(value = "/delete")
    public String delete(int feedNo, Model model) {
        List<FeedDto> feedAfterDel = feedService.delete(feedNo);
        model.addAttribute("feeds", feedAfterDel);
        return "feed/feed_main";
    }

    @GetMapping(value = {"", "/"})
    public String feedMain(Model model) {
        model.addAttribute("feeds", feedService.feedPage());
        return "feed/feed_main";
    }
}
