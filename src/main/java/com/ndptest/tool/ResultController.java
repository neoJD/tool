package com.ndptest.tool;

import com.ndptest.tool.apiMethod.GET.threadPoolTest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import static com.ndptest.tool.apiMethod.GET.UserService.*;
import static com.ndptest.tool.apiMethod.GET.threadPoolTest.*;

import com.ndptest.tool.apiMethod.GET.threadPoolTest.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

//import static com.ndptest.tool.apiMethod.GET.threadPoolTest.threadPool;

@RequiredArgsConstructor
@Controller
public class ResultController {
    String testSubject;

    private final ResultService resultService;
    @Autowired
    private final ResultRepository resultRepository;


    @RequestMapping(value="/loading", method={RequestMethod.GET,RequestMethod.POST})
    public String loading(@ModelAttribute("selectDto") SelectDto selectDto, Model model
    ) throws Exception {
        model.addAttribute("subject", selectDto.getSubject());
        testSubject = String.valueOf(selectDto.getSubject());
        threadPool(testSubject);
        System.out.println("끝");
        return "test_loading";
    }

    @RequestMapping(value="/result", method={RequestMethod.GET, RequestMethod.POST})
    public String result(Model model)throws Exception{
        ArrayList<Result> resultList = new ArrayList<>();
        IntStream.rangeClosed(0, idList.size()-1).forEach(i -> {
            Result result = Result.builder()
                    .number(i+1)
                    .username(idList.get(i))
                    .responseTime(responseList.get(i))
                    .build();
            resultList.add(result);
        });
        resultRepository.saveAll(resultList);
        List<Result> testResultList = this.resultService.getList();
        model.addAttribute("subject", testSubject);
        model.addAttribute("testResultList", testResultList);
        return "test_result";
    }

    @RequestMapping(value="/returnToMain", method={RequestMethod.POST})
    public String returnToMain()throws Exception{
        resultService.truncateResultTable();
        resetResultList();
        return "redirect:/main";
    }

}
